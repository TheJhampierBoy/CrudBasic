document.addEventListener("DOMContentLoaded", function () {
    const orderForm = document.getElementById("orderForm");
    const saveOrderButton = document.getElementById("saveOrderButton");
    const searchButton = document.getElementById("searchButton");
    const searchFilter = document.getElementById("searchFilter");
    const orderTableBody = document.getElementById("orderTableBody");
    const orderModal = new bootstrap.Modal(document.getElementById("orderModal"));
    const apiUrl = "http://localhost:8080/api/v1/orderDetail/";

    let editingOrderId = null;

    // Obtener y mostrar órdenes
    function fetchOrders(filter = "") {
        const url = filter ? `${apiUrl}filter?search=${encodeURIComponent(filter)}` : apiUrl;

        fetch(url)
            .then(response => response.json())
            .then(orders => {
                orderTableBody.innerHTML = ""; // Limpiar la tabla
                if (orders.length === 0) {
                    orderTableBody.innerHTML = `<tr><td colspan="6" class="text-center">No se encontraron resultados.</td></tr>`;
                } else {
                    orders.forEach(addOrderRow); // Agregar cada orden como una fila
                }
            })
            .catch(error => console.error("Error al obtener las órdenes:", error));
    }

    // Agregar una fila a la tabla
    function addOrderRow(order) {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${order.orderDetailID || "N/A"}</td>
            <td>${order.price}</td>
            <td>${order.quantity}</td>
            <td>${order.drugId || "Sin medicamento"}</td>
            <td>${order.orderID || "Sin pedido"}</td>
            <td>
                <button class="btn btn-warning btn-sm edit-button" data-id="${order.orderDetailID}"><i class="fas fa-edit"></i></button>
                <button class="btn btn-danger btn-sm delete-button" data-id="${order.orderDetailID}"><i class="fas fa-trash"></i></button>
            </td>
        `;
        orderTableBody.appendChild(row);

        // Asignar eventos a los botones de edición y eliminación
        row.querySelector(".edit-button").addEventListener("click", () => editOrder(order.orderDetailID));
        row.querySelector(".delete-button").addEventListener("click", () => deleteOrder(order.orderDetailID));
    }

    // Guardar o actualizar una orden
    saveOrderButton.addEventListener("click", () => {
        const orderData = {
            orderID: parseInt(document.getElementById("orderId").value, 10),
            drugId: parseInt(document.getElementById("drugId").value, 10),
            quantity: parseInt(document.getElementById("quantity").value, 10),
            price: parseFloat(document.getElementById("price").value)
        };

        const method = editingOrderId ? "PUT" : "POST";
        const url = editingOrderId ? `${apiUrl}${editingOrderId}` : apiUrl;

        fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(orderData)
        })
            .then(response => {
                if (!response.ok) throw new Error("Error al guardar la orden detalle");
                return response.json();
            })
            .then(order => {
                if (!editingOrderId) {
                    // Si es una nueva orden, agregarla directamente a la tabla
                    addOrderRow(order);
                } else {
                    // Si es una edición, recargar toda la lista de órdenes
                    fetchOrders();
                }

                // Limpiar el formulario y cerrar el modal
                orderForm.reset();
                editingOrderId = null;
                orderModal.hide();
            })
            .catch(error => console.error("Error al guardar la orden detalle:", error));
    });

    // Editar una orden
    function editOrder(id) {
        fetch(`${apiUrl}${id}`)
            .then(response => response.json())
            .then(order => {
                // Llenar el formulario con los datos de la orden
                document.getElementById("quantity").value = order.quantity;
                document.getElementById("price").value = order.price;
                document.getElementById("drugId").value = order.drugId || "";
                document.getElementById("orderId").value = order.orderID || "";

                editingOrderId = id; // Establecer el ID de la orden que se está editando
                orderModal.show(); // Mostrar el modal
            })
            .catch(error => console.error("Error al editar la orden detalle:", error));
    }

    // Eliminar una orden
    function deleteOrder(id) {
        if (!id) {
            console.error("ID no válido para eliminar la orden detalle.");
            return;
        }

        if (confirm("¿Estás seguro de que deseas eliminar esta orden detalle?")) {
            fetch(`${apiUrl}${id}`, { method: "DELETE" })
                .then(response => {
                    if (!response.ok) throw new Error("Error al eliminar la orden detalle");
                    // Eliminar la fila de la tabla directamente
                    document.querySelector(`button[data-id="${id}"]`).closest("tr").remove();
                })
                .catch(error => console.error("Error al eliminar la orden detalle:", error));
        }
    }

    // Buscar órdenes
    searchButton.addEventListener("click", () => {
        const term = searchFilter.value.trim();
        fetchOrders(term); // Llamar a fetchOrders con el término de búsqueda
    });

    // Limpiar el formulario cuando se cierra el modal
    document.getElementById("orderModal").addEventListener("hidden.bs.modal", () => {
        orderForm.reset();
        editingOrderId = null; // Restablecer el ID de edición
    });

    // Cargar órdenes al inicio
    fetchOrders();
});