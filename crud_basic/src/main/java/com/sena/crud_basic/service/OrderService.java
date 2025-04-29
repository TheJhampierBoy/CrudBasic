package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.OrderDTO;
import com.sena.crud_basic.model.Order;
import com.sena.crud_basic.model.Customer;
import com.sena.crud_basic.model.Employee;
import com.sena.crud_basic.repository.OrderRepository;
import com.sena.crud_basic.repository.CustomerRepository;
import com.sena.crud_basic.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public String save(OrderDTO orderDTO) {
        validateOrderDTO(orderDTO);
        
        Order order = convertToModel(orderDTO);
        orderRepository.save(order);
        return "Pedido registrado exitosamente";
    }

    public List<OrderDTO> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<OrderDTO> findById(int id) {
        return orderRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Transactional
    public String deleteOrder(int id) {
        if (!orderRepository.existsById(id)) {
            return "El pedido con ID " + id + " no existe";
        }
        orderRepository.deleteById(id);
        return "Pedido eliminado correctamente";
    }

    @Transactional
    public String updateOrder(int id, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El pedido con ID " + id + " no existe"));

        validateOrderDTO(orderDTO);
        
        existingOrder.setProduct(orderDTO.getProduct());
        existingOrder.setQuantity(orderDTO.getQuantity());
        existingOrder.setPrice(orderDTO.getPrice());
        existingOrder.setTotalAmount(orderDTO.getQuantity() * orderDTO.getPrice());
        existingOrder.setStatus(orderDTO.getStatus());
        
        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + orderDTO.getCustomerId()));
        existingOrder.setCustomer(customer);
        
        Employee employee = employeeRepository.findById(orderDTO.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con ID: " + orderDTO.getEmployeeId()));
        existingOrder.setEmployee(employee);
        
        orderRepository.save(existingOrder);
        return "Pedido actualizado correctamente";
    }

    public List<OrderDTO> filterOrders(String searchTerm) {
        return orderRepository.filterOrders(searchTerm == null ? "" : searchTerm)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> advancedFilterOrders(LocalDateTime startDate, LocalDateTime endDate,
                                             Integer minAmount, Integer maxAmount, String status) {
        return orderRepository.advancedFilterOrders(
                startDate,
                endDate,
                minAmount,
                maxAmount,
                status != null ? status.toLowerCase() : null)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private void validateOrderDTO(OrderDTO orderDTO) {
        if (orderDTO.getProduct() == null || orderDTO.getProduct().trim().isEmpty()) {
            throw new IllegalArgumentException("El producto no puede estar vacío");
        }
        if (orderDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        if (orderDTO.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero");
        }
        if (!customerRepository.existsById(orderDTO.getCustomerId())) {
            throw new IllegalArgumentException("El cliente especificado no existe");
        }
        if (!employeeRepository.existsById(orderDTO.getEmployeeId())) {
            throw new IllegalArgumentException("El empleado especificado no existe");
        }
    }

    private Order convertToModel(OrderDTO orderDTO) {
        Order order = new Order();
        order.setProduct(orderDTO.getProduct());
        order.setQuantity(orderDTO.getQuantity());
        order.setPrice(orderDTO.getPrice());
        order.setTotalAmount(orderDTO.getQuantity() * orderDTO.getPrice());
        order.setStatus(orderDTO.getStatus() != null ? orderDTO.getStatus() : "Pendiente");
        order.setOrderDate(LocalDateTime.now());
    
        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + orderDTO.getCustomerId()));
        order.setCustomer(customer);
    
        Employee employee = employeeRepository.findById(orderDTO.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con ID: " + orderDTO.getEmployeeId()));
        order.setEmployee(employee);
    
        return order;
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderID());
        dto.setProduct(order.getProduct());
        dto.setQuantity(order.getQuantity());
        dto.setPrice(order.getPrice());
        dto.setStatus(order.getStatus());
        dto.setCustomerId(order.getCustomer().getCustomerID());
        dto.setEmployeeId(order.getEmployee().getEmployeeID());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setOrderDate(order.getOrderDate());
        return dto;
    }
}