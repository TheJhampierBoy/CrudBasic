document.addEventListener("DOMContentLoaded", function () {
    const saveInventoryButton = document.getElementById("saveInventoryButton");
    const productFilter = document.getElementById("productFilter");
    const inventoryTableBody = document.querySelector(".inventario-table tbody");
    const apiUrl = "http://localhost:8080/api/v1/inventory/";

    // Agregar o actualizar inventario
    saveInventoryButton.addEventListener("click", async function (event) {
        event.preventDefault();

        const productName = document.getElementById("productName").value.trim();
        const drugID = document.getElementById("drugID").value.trim();
        const stockReceived = document.getElementById("stockReceived").value.trim();
        const stockRemaining = document.getElementById("stockRemaining").value.trim();

        // Validar que los campos no estén vacíos
        if (!productName || !drugID || !stockReceived || !stockRemaining) {
            alert("Por favor, completa todos los campos correctamente.");
            return;
        }

        // Crear el objeto del inventario
        const inventoryData = {
            productName,
            stockReceived: parseInt(stockReceived, 10),
            stockRemaining: parseInt(stockRemaining, 10),
            drugID: parseInt(drugID, 10)
        };

        const id = saveInventoryButton.dataset.id;
        const url = id ? `${apiUrl}${id}` : apiUrl;
        const method = id ? "PUT" : "POST";

        try {
            const response = await fetch(url, {
                method,
                body: JSON.stringify(inventoryData),
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || "Error al guardar el inventario");
            }

            // Limpiar los campos del formulario y restablecer el botón
            document.getElementById("inventoryForm").reset();
            delete saveInventoryButton.dataset.id;

            // Cerrar el modal
            bootstrap.Modal.getInstance(document.getElementById("inventoryModal")).hide();

            // Actualizar la tabla automáticamente
            actualizarTablaInventario();
        } catch (error) {
            console.error("❌ Error al guardar el inventario:", error);
            alert("Error al guardar el inventario: " + error.message);
        }
    });

    // Buscar inventario
    document.getElementById("searchButton").addEventListener("click", function () {
        actualizarTablaInventario(productFilter.value.trim());
    });

    // Función para actualizar la tabla
    async function actualizarTablaInventario(filter = "") {
        const url = filter ? `${apiUrl}filter?productName=${encodeURIComponent(filter)}` : apiUrl;

        try {
            const response = await fetch(url);
            if (!response.ok) throw new Error("Error al obtener datos del inventario");

            const data = await response.json();
            inventoryTableBody.innerHTML = "";

            if (data.length === 0) {
                inventoryTableBody.innerHTML = `<tr><td colspan="6" class="text-center">No se encontraron resultados.</td></tr>`;
                return;
            }

            data.forEach(item => {
                const row = `
                    <tr>
                        <td>${item.inventoryID}</td>
                        <td>${item.productName}</td>
                        <td>${item.drugID}</td>
                        <td>${item.stockReceived}</td>
                        <td>${item.stockRemaining}</td>
                        <td>
                            <button class="btn btn-warning btn-sm edit-button" data-id="${item.inventoryID}"><i class="fas fa-edit"></i></button>
                            <button class="btn btn-danger btn-sm delete-button" data-id="${item.inventoryID}"><i class="fas fa-trash-alt"></i></button>
                        </td>
                    </tr>
                `;
                inventoryTableBody.innerHTML += row;
            });

            agregarEventosBotones();
        } catch (error) {
            console.error("Error al actualizar tabla:", error);
        }
    }

    // Agregar eventos a los botones
    function agregarEventosBotones() {
        document.querySelectorAll(".edit-button").forEach(button => {
            button.addEventListener("click", function () {
                const id = this.getAttribute("data-id");
                if (id) {
                    editarInventario(id);
                } else {
                    console.error("❌ Error: ID no encontrado en el botón de edición.");
                }
            });
        });

        document.querySelectorAll(".delete-button").forEach(button => {
            button.addEventListener("click", function () {
                const id = this.getAttribute("data-id");
                if (id) {
                    eliminarInventario(id);
                } else {
                    console.error("❌ Error: ID no encontrado en el botón de eliminación.");
                }
            });
        });
    }

    // Editar inventario
    async function editarInventario(id) {
        try {
            const response = await fetch(`${apiUrl}${id}`);
            if (!response.ok) throw new Error("Error al obtener inventario");

            const item = await response.json();
            document.getElementById("productName").value = item.productName;
            document.getElementById("drugID").value = item.drugID;
            document.getElementById("stockReceived").value = item.stockReceived;
            document.getElementById("stockRemaining").value = item.stockRemaining;

            saveInventoryButton.setAttribute("data-id", id);
            bootstrap.Modal.getInstance(document.getElementById("inventoryModal")).show();
        } catch (error) {
            console.error("Error al editar:", error);
            alert("Error al editar el inventario.");
        }
    }

    // Eliminar inventario
    async function eliminarInventario(id) {
        if (!confirm("¿Seguro que deseas eliminar este producto?")) return;

        try {
            const response = await fetch(`${apiUrl}${id}`, { method: "DELETE" });
            if (!response.ok) throw new Error("Error al eliminar inventario");

            actualizarTablaInventario();
        } catch (error) {
            console.error("Error al eliminar:", error);
            alert("Error al eliminar el inventario.");
        }
    }

    // Cargar tabla al inicio
    actualizarTablaInventario();
});