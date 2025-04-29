document.addEventListener("DOMContentLoaded", function () {
    const botonEnvioProveedor = document.getElementById("botonEnvioProveedor");

    if (botonEnvioProveedor) {
        botonEnvioProveedor.addEventListener("click", async function (event) {
            event.preventDefault();

            // Obtener los valores del formulario
            const supplierName = document.getElementById("supplierName").value;
            const supplierContact = document.getElementById("supplierContact").value;
            const supplierAddress = document.getElementById("supplierAddress").value;
            const supplierStatus = document.getElementById("supplierStatus").checked; // Checkbox para el estado

            // Validar que los campos no estén vacíos
            if (!supplierName || !supplierContact || !supplierAddress) {
                alert("Por favor, completa todos los campos obligatorios.");
                return;
            }

            // Crear el objeto proveedor
            const proveedorData = {
                name: supplierName,
                contact: supplierContact,
                address: supplierAddress,
                status: supplierStatus
            };

            // Verificar si es una edición o una creación
            const id = botonEnvioProveedor.dataset.id;
            const url = id
                ? `http://localhost:8080/api/v1/supplier/${id}` // Edición
                : "http://localhost:8080/api/v1/supplier/"; // Creación
            const method = id ? "PUT" : "POST";

            try {
                // Enviar la solicitud al backend
                const response = await fetch(url, {
                    method: method,
                    body: JSON.stringify(proveedorData),
                    headers: {
                        "Content-Type": "application/json"
                    }
                });

                if (!response.ok) {
                    const errorData = await response.json();
                    throw new Error(errorData.message || "Error al guardar el proveedor");
                }

                // Limpiar los campos del formulario y el estado del botón
                document.getElementById("proveedorForm").reset();
                botonEnvioProveedor.textContent = "Guardar";
                delete botonEnvioProveedor.dataset.id;

                // Cerrar el modal
                const modal = bootstrap.Modal.getInstance(document.getElementById('exampleModal'));
                if (modal) {
                    modal.hide();
                }

                // Actualizar la tabla automáticamente
                actualizarTablaProveedores();
            } catch (error) {
                console.error("❌ Error al guardar el proveedor:", error);
                alert("Error al guardar el proveedor: " + error.message);
            }
        });
    }

    // Configurar el botón de búsqueda
    const botonBuscar = document.querySelector(".filtrar-proveedores button");
    if (botonBuscar) {
        botonBuscar.addEventListener("click", function () {
            actualizarTablaProveedores();
        });
    }

    // Cargar la tabla al iniciar
    actualizarTablaProveedores();
});

function obtenerProveedores() {
    return new Promise(async (resolve, reject) => {
        try {
            const filtroInput = document.getElementById("supplierFilter");
            const filtro = filtroInput ? filtroInput.value : ""; // Validar si el filtro existe

            let url = 'http://localhost:8080/api/v1/supplier/filter';

            // Si hay un filtro, agregarlo a la URL
            if (filtro.trim() !== "") {
                url += `?name=${encodeURIComponent(filtro)}`;
            }

            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    "Accept": "application/json"
                }
            });

            if (!response.ok) {
                throw new Error("Error al obtener los proveedores");
            }

            const data = await response.json();
            resolve(data);
        } catch (error) {
            console.error("❌ Error al obtener los proveedores:", error);
            reject(error);
        }
    });
}

function actualizarTablaProveedores() {
    obtenerProveedores()
        .then(proveedores => {
            const tbody = document.querySelector(".proveedores-table tbody");
            if (!tbody) {
                console.error("❌ No se encontró la tabla de proveedores en el DOM.");
                return;
            }
            tbody.innerHTML = "";

            if (proveedores.length === 0) {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td colspan="6" class="text-center">
                        No se encontraron proveedores con ese filtro.
                    </td>
                `;
                tbody.appendChild(tr);
                return;
            }

            proveedores.forEach(proveedor => {
                const tr = document.createElement("tr");
                const proveedorId = proveedor.id || "N/A";

                tr.innerHTML = `
                    <td>${proveedorId}</td>
                    <td>${proveedor.name || "Sin nombre"}</td>
                    <td>${proveedor.contact || "Sin contacto"}</td>
                    <td>${proveedor.address || "Sin dirección"}</td>
                    <td>${proveedor.status ? "Activo" : "Inactivo"}</td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-edit btn btn-warning btn-sm" data-id="${proveedorId}">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn-delete btn btn-danger btn-sm" data-id="${proveedorId}">
                                <i class="fas fa-trash-alt"></i>
                            </button>
                        </div>
                    </td>
                `;
                tbody.appendChild(tr);
            });

            agregarEventosBotonesProveedores();
        })
        .catch(error => {
            console.error("❌ Error al actualizar la tabla de proveedores:", error);
        });
}

function agregarEventosBotonesProveedores() {
    document.querySelectorAll(".btn-delete").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            eliminarProveedor(id);
        });
    });

    document.querySelectorAll(".btn-edit").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            editarProveedor(id);
        });
    });
}

function eliminarProveedor(id) {
    if (!id || isNaN(parseInt(id))) {
        alert("ID de proveedor no válido.");
        return;
    }

    if (confirm("¿Eliminar este proveedor?")) {
        fetch(`http://localhost:8080/api/v1/supplier/${id}`, {
            method: "DELETE",
            headers: {
                "Accept": "text/plain",
                "Content-Type": "application/json"
            }
        })
        .then(async response => {
            const text = await response.text();

            if (!response.ok) {
                throw new Error(text || "Error al eliminar");
            }

            alert(text);
            actualizarTablaProveedores();
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Error al eliminar el proveedor: " + error.message);
        });
    }
}

function editarProveedor(id) {
    fetch(`http://localhost:8080/api/v1/supplier/${id}`, {
        method: "GET",
        headers: {
            "Accept": "application/json"
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error al obtener los datos del proveedor");
        }
        return response.json();
    })
    .then(proveedor => {
        document.getElementById("supplierName").value = proveedor.name;
        document.getElementById("supplierContact").value = proveedor.contact;
        document.getElementById("supplierAddress").value = proveedor.address;
        document.getElementById("supplierStatus").checked = proveedor.status;

        const botonEnvioProveedor = document.getElementById("botonEnvioProveedor");
        botonEnvioProveedor.textContent = "Actualizar";
        botonEnvioProveedor.dataset.id = id;

        // Usar el ID correcto del modal
        const modal = new bootstrap.Modal(document.getElementById('exampleModal'));
        modal.show();
    })
    .catch(error => {
        console.error("Error al cargar los datos del proveedor:", error);
        alert("Error al cargar los datos del proveedor: " + error.message);
    });
}