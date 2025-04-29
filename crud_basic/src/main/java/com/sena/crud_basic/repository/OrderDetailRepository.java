package com.sena.crud_basic.repository;

import com.sena.crud_basic.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    @Query("SELECT od FROM OrderDetail od WHERE " +
           "od.order.orderID = :orderId OR " +
           "od.drug.drugID = :drugId OR " +
           "od.quantity = :quantity OR " +
           "od.price = :price")
    List<OrderDetail> filterOrderDetails(
            @Param("orderId") Integer orderId,
            @Param("drugId") Integer drugId,
            @Param("quantity") Integer quantity,
            @Param("price") Double price);
}
