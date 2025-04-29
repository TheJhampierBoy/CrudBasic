document.addEventListener("DOMContentLoaded", function () {
    const botonEnvioEmpleado = document.getElementById("botonEnvioEmpleado");

    if (botonEnvioEmpleado) {
        botonEnvioEmpleado.addEventListener("click", async function (event) {
            event.preventDefault();
        
            // Obtener los valores del formulario
            const nombre = document.getElementById("nombreEmpleado").value;
            const apellido = document.getElementById("apellidoEmpleado").value;
            const email = document.getElementById("emailEmpleado").value;
            const telefono = document.getElementById("telefonoEmpleado").value;
            const rol = document.getElementById("rolEmpleado").value;
        
            // Validar que los campos no estén vacíos
            if (!nombre || !apellido || !email || !telefono || !rol) {
                alert("Por favor, completa todos los campos.");
                return;
            }
        
            // Crear el objeto empleado
            const empleadoData = {
                firstName: nombre,
                lastName: apellido,
                email: email,
                phoneNumber: telefono,
                role: rol
            };
        
            // Verificar si es una edición o una creación
            const id = botonEnvioEmpleado.dataset.id;
            const url = id 
                ? `http://localhost:8080/api/v1/employee/${id}` // Edición
                : "http://localhost:8080/api/v1/employee/"; // Creación
            const method = id ? "PUT" : "POST";
        
            try {
                // Enviar la solicitud al backend
                const response = await fetch(url, {
                    method: method,
                    body: JSON.stringify(empleadoData),
                    headers: {
                        "Content-Type": "application/json"
                    }
                });
        
                if (!response.ok) {
                    const errorData = await response.json();
                    throw new Error(errorData.message || "Error al guardar el empleado");
                }
        
                // Limpiar los campos del formulario y el estado del botón
                document.getElementById("empleadoForm").reset();
                botonEnvioEmpleado.textContent = "Enviar";
                delete botonEnvioEmpleado.dataset.id;
        
                // Cerrar el modal
                const modal = bootstrap.Modal.getInstance(document.getElementById('exampleModal'));
                if (modal) {
                    modal.hide();
                }
        
                // Actualizar la tabla automáticamente
                actualizarTablaEmpleados();
        
            } catch (error) {
                console.error("❌ Error al guardar el empleado:", error);
                alert("Error al guardar el empleado: " + error.message);
            }
        });
    }

    // Configurar el botón de búsqueda
    const botonBuscar = document.querySelector(".filtrar-empleados button");
    if (botonBuscar) {
        botonBuscar.addEventListener("click", function () {
            actualizarTablaEmpleados();
        });
    }

    // Cargar la tabla al iniciar
    actualizarTablaEmpleados();
});

// Función para obtener empleados desde el servidor
function obtenerEmpleados() {
    return new Promise(async (resolve, reject) => {
        try {
            let url = 'http://localhost:8080/api/v1/employee/filter';
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
                throw new Error("Error al obtener los empleados");
            }

            const data = await response.json();
            resolve(data);
        } catch (error) {
            console.error("❌ Error al obtener los empleados:", error);
            reject(error);
        }
    });
}
function actualizarTablaEmpleados() {
    obtenerEmpleados()
        .then(empleados => {
            const tbody = document.querySelector(".empleados-table tbody");
            tbody.innerHTML = "";

            if (empleados.length === 0) {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td colspan="7" class="text-center">
                        No se encontraron empleados con ese filtro
                    </td>
                `;
                tbody.appendChild(tr);
                return;
            }

            empleados.forEach(empleado => {
                const tr = document.createElement("tr");
                // Verificación más robusta del ID
                const empleadoId = (empleado.employeeID !== undefined && empleado.employeeID !== null) 
                    ? empleado.employeeID 
                    : (console.error("ID no válido para empleado:", empleado), "N/A");

                tr.innerHTML = `
                    <td>${empleadoId}</td>
                    <td>${empleado.firstName || "Sin nombre"}</td>
                    <td>${empleado.lastName || "Sin apellido"}</td>
                    <td>${empleado.role || "Sin rol"}</td>
                    <td>${empleado.phoneNumber || "Sin teléfono"}</td>
                    <td>${empleado.email || "Sin email"}</td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-edit" data-id="${empleadoId}" ${empleadoId === "N/A" ? 'disabled' : ''}>
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn-delete" data-id="${empleadoId}" ${empleadoId === "N/A" ? 'disabled' : ''}>
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
            console.error("❌ Error al actualizar la tabla de empleados:", error);
        });
}
function eliminarEmpleado(id) {
    if (!id || isNaN(parseInt(id))) {
        alert("ID de empleado no válido");
        return;
    }

    if (confirm("¿Eliminar este empleado?")) {
        fetch(`http://localhost:8080/api/v1/employee/${id}`, {
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
            alert(text); // Muestra "Empleado eliminado correctamente"
            actualizarTablaEmpleados();
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Error al eliminar el empleado: " + error.message);
        });
    }
}
// Función para agregar eventos a los botones
function agregarEventosBotones() {
    // Eventos para botones de eliminar
    document.querySelectorAll(".btn-delete").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            eliminarEmpleado(id);
        });
    });

    // Eventos para botones de editar
    document.querySelectorAll(".btn-edit").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            editarEmpleado(id);
        });
    });
}

// Función para editar un empleado (puedes implementarla según sea necesario)
function editarEmpleado(id) {
    // Obtener los datos del empleado por ID
    fetch(`http://localhost:8080/api/v1/employee/${id}`, {
        method: "GET",
        headers: {
            "Accept": "application/json"
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error al obtener los datos del empleado");
        }
        return response.json();
    })
    .then(empleado => {
        // Cargar los datos del empleado en el formulario del modal
        document.getElementById("nombreEmpleado").value = empleado.firstName;
        document.getElementById("apellidoEmpleado").value = empleado.lastName;
        document.getElementById("emailEmpleado").value = empleado.email;
        document.getElementById("telefonoEmpleado").value = empleado.phoneNumber;
        document.getElementById("rolEmpleado").value = empleado.role;

        // Cambiar el texto del botón para indicar que es una edición
        const botonEnvioEmpleado = document.getElementById("botonEnvioEmpleado");
        botonEnvioEmpleado.textContent = "Actualizar";
        botonEnvioEmpleado.dataset.id = id; // Guardar el ID del empleado en el botón

        // Abrir el modal
        const modal = new bootstrap.Modal(document.getElementById('exampleModal'));
        modal.show();
    })
    .catch(error => {
        console.error("Error al cargar los datos del empleado:", error);
        alert("Error al cargar los datos del empleado: " + error.message);
    });
}