document.addEventListener("DOMContentLoaded", function () {
    const botonEnvioCliente = document.getElementById("botonEnvioCliente");

    if (botonEnvioCliente) {
        botonEnvioCliente.addEventListener("click", async function (event) {
            event.preventDefault();

            // Obtener los valores del formulario
            const nombre = document.getElementById("nombreCliente").value;
            const email = document.getElementById("emailCliente").value;
            const telefono = document.getElementById("telefonoCliente").value;

            // Validar que los campos no estén vacíos
            if (!nombre || !email || !telefono) {
                alert("Por favor, completa todos los campos.");
                return;
            }

            try {
                // Crear el objeto cliente
                const clienteData = {
                    name: nombre,
                    email: email,
                    phone: telefono
                };

                // Verificar si es una edición o una creación
                const id = botonEnvioCliente.dataset.id;
                const url = id 
                    ? `http://localhost:8080/api/v1/customer/${id}` // Edición
                    : "http://localhost:8080/api/v1/customer/"; // Creación
                const method = id ? "PUT" : "POST";

                // Enviar la solicitud al backend
                const response = await fetch(url, {
                    method: method,
                    body: JSON.stringify(clienteData),
                    headers: {
                        "Content-Type": "application/json"
                    }
                });

                if (!response.ok) {
                    const errorData = await response.json();
                    throw new Error(errorData.message || "Error al guardar el cliente");
                }

                // Limpiar los campos del formulario y el estado del botón
                document.getElementById("clienteForm").reset();
                botonEnvioCliente.textContent = "Enviar";
                delete botonEnvioCliente.dataset.id;

                // Cerrar el modal
                const modal = bootstrap.Modal.getInstance(document.getElementById('exampleModal'));
                if (modal) {
                    modal.hide();
                }

                // Actualizar la tabla automáticamente
                actualizarTablaClientes();

            } catch (error) {
                console.error("❌ Error al guardar el cliente:", error);
                alert("Error al guardar el cliente: " + error.message);
            }
        });
    }

    // Configurar el botón de búsqueda
    const botonBuscar = document.querySelector(".filtrar-clientes button");
    if (botonBuscar) {
        botonBuscar.addEventListener("click", function () {
            actualizarTablaClientes();
        });
    }

    // Cargar la tabla al iniciar
    actualizarTablaClientes();
});

// Función para obtener clientes desde el servidor
function obtenerClientes() {
    return new Promise(async (resolve, reject) => {
        try {
            let url = 'http://localhost:8080/api/v1/customer/filter';
            let filtro = document.getElementById("nameFilter").value;

            // Si hay un filtro, agregarlo a la URL
            if (filtro !== '') {
                url += `?searchTerm=${encodeURIComponent(filtro)}`;
            }

            let response = await fetch(url, {
                method: 'GET',
                headers: {
                    "Accept": "application/json"
                }
            });

            if (!response.ok) {
                throw new Error("Error al obtener los clientes");
            }

            const data = await response.json();
            resolve(data);
        } catch (error) {
            console.error("❌ Error al obtener los clientes:", error);
            reject(error);
        }
    });
}

// Función para actualizar la tabla de clientes
function actualizarTablaClientes() {
    obtenerClientes()
        .then(clientes => {
            const tbody = document.querySelector(".clientes-table tbody");
            tbody.innerHTML = "";

            if (clientes.length === 0) {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td colspan="4" class="text-center">
                        No se encontraron clientes con ese filtro
                    </td>
                `;
                tbody.appendChild(tr);
                return;
            }

            clientes.forEach(cliente => {
                const tr = document.createElement("tr");
                const clienteId = cliente.customerID !== undefined && cliente.customerID !== null ? cliente.customerID : "N/A";

                tr.innerHTML = `
                    <td>${clienteId}</td>
                    <td>${cliente.name || "Sin nombre"}</td>
                    <td>${cliente.phone || "Sin teléfono"}</td>
                    <td>${cliente.email || "Sin email"}</td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-edit" data-id="${clienteId}" ${clienteId === "N/A" ? 'disabled' : ''}>
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn-delete" data-id="${clienteId}" ${clienteId === "N/A" ? 'disabled' : ''}>
                                <i class="fas fa-trash-alt"></i>
                            </button>
                        </div>
                    </td>
                `;
                tbody.appendChild(tr);
            });

            agregarEventosBotones();
        })
        .catch(error => {
            console.error("❌ Error al actualizar la tabla de clientes:", error);
        });
}

// Función para eliminar un cliente
function eliminarCliente(id) {
    if (!id || isNaN(parseInt(id))) {
        alert("ID de cliente no válido");
        return;
    }

    if (confirm("¿Eliminar este cliente?")) {
        fetch(`http://localhost:8080/api/v1/customer/${id}`, {
            method: "DELETE",
            headers: {
                "Accept": "text/plain", // Aceptamos texto plano
                "Content-Type": "application/json"
            }
        })
        .then(async response => {
            const text = await response.text(); // Leemos como texto
            
            if (!response.ok) {
                throw new Error(text || "Error al eliminar");
            }
            
            console.log("Respuesta:", text);
            alert(text); 
            actualizarTablaClientes();
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Error al eliminar el cliente: " + error.message);
        });
    }
}
// Función para agregar eventos a los botones
function agregarEventosBotones() {
    // Eventos para botones de eliminar
    document.querySelectorAll(".btn-delete").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            eliminarCliente(id);
        });
    });

    // Eventos para botones de editar
    document.querySelectorAll(".btn-edit").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            editarCliente(id);
        });
    });
}

// Función para editar un cliente
function editarCliente(id) {
    // Obtener los datos del cliente por ID
    fetch(`http://localhost:8080/api/v1/customer/${id}`, {
        method: "GET",
        headers: {
            "Accept": "application/json"
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error al obtener los datos del cliente");
        }
        return response.json();
    })
    .then(cliente => {
        // Cargar los datos del cliente en el formulario del modal
        document.getElementById("nombreCliente").value = cliente.name;
        document.getElementById("emailCliente").value = cliente.email;
        document.getElementById("telefonoCliente").value = cliente.phone;

        // Cambiar el texto del botón para indicar que es una edición
        const botonEnvioCliente = document.getElementById("botonEnvioCliente");
        botonEnvioCliente.textContent = "Actualizar";
        botonEnvioCliente.dataset.id = id; // Guardar el ID del cliente en el botón

        // Abrir el modal
        const modal = new bootstrap.Modal(document.getElementById('exampleModal'));
        modal.show();
    })
    .catch(error => {
        console.error("Error al cargar los datos del cliente:", error);
        alert("Error al cargar los datos del cliente: " + error.message);
    });
}