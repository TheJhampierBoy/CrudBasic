document.addEventListener("DOMContentLoaded", function () {
    const botonEnvioPago = document.getElementById("botonEnvioPago");

    if (botonEnvioPago) {
        botonEnvioPago.addEventListener("click", async function (event) {
            event.preventDefault();
        
            // Obtener los valores del formulario
            const monto = document.getElementById("montoPago").value;
            const fecha = document.getElementById("fechaPago").value;
            const descripcion = document.getElementById("descripcionPago").value;
            const metodo = document.getElementById("metodoPago").value;
            const estado = document.getElementById("estadoPago").value;

            // Validar que los campos no estén vacíos
            if (!monto || !fecha || !descripcion || !metodo || !estado) {
                alert("Por favor, completa todos los campos.");
                return;
            }

            // Validar la fecha
            const fechaActual = new Date();
            const fechaPago = new Date(fecha);
            const fechaMinima = new Date();
            fechaMinima.setFullYear(fechaActual.getFullYear() - 20);

            if (fechaPago > fechaActual) {
                alert("La fecha del pago no puede ser en el futuro.");
                return;
            }

            if (fechaPago < fechaMinima) {
                alert("La fecha del pago no puede ser hace más de 20 años.");
                return;
            }

            // Crear el objeto pago
            const pagoData = {
                amount: monto,
                date: `${fecha}T00:00:00`, // Agregar la hora predeterminada
                description: descripcion,
                method: metodo,
                status: estado
            };

            // Verificar si es una edición o una creación
            const id = botonEnvioPago.dataset.id;
            const url = id 
                ? `http://localhost:8080/api/v1/payment/${id}` // Edición
                : "http://localhost:8080/api/v1/payment/"; // Creación
            const method = id ? "PUT" : "POST";

            try {
                // Enviar la solicitud al backend
                const response = await fetch(url, {
                    method: method,
                    body: JSON.stringify(pagoData),
                    headers: {
                        "Content-Type": "application/json"
                    }
                });

                if (!response.ok) {
                    const errorData = await response.json();
                    throw new Error(errorData.message || "Error al guardar el pago");
                }

                // Limpiar los campos del formulario y el estado del botón
                document.getElementById("pagoForm").reset();
                botonEnvioPago.textContent = "Guardar";
                delete botonEnvioPago.dataset.id;

                // Cerrar el modal
                const modal = bootstrap.Modal.getInstance(document.getElementById('exampleModal'));
                if (modal) {
                    modal.hide();
                }

                // Actualizar la tabla automáticamente
                actualizarTablaPagos();

            } catch (error) {
                console.error("❌ Error al guardar el pago:", error);
                alert("Error al guardar el pago: " + error.message);
            }
        });
    }

    // Configurar el botón de búsqueda
    const botonBuscar = document.querySelector(".filtrar-pagos button");
    if (botonBuscar) {
        botonBuscar.addEventListener("click", function () {
            actualizarTablaPagos();
        });
    }

    // Cargar la tabla al iniciar
    actualizarTablaPagos();
});

// Función para obtener pagos desde el servidor
function obtenerPagos() {
    return new Promise(async (resolve, reject) => {
        try {
            let url = 'http://localhost:8080/api/v1/payment/filter';
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
                throw new Error("Error al obtener los pagos");
            }

            const data = await response.json();
            resolve(data);
        } catch (error) {
            console.error("❌ Error al obtener los pagos:", error);
            reject(error);
        }
    });
}

function actualizarTablaPagos() {
    obtenerPagos()
        .then(pagos => {
            const tbody = document.querySelector(".pagos-table tbody");
            tbody.innerHTML = "";

            if (pagos.length === 0) {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td colspan="7" class="text-center">
                        No se encontraron pagos con ese filtro
                    </td>
                `;
                tbody.appendChild(tr);
                return;
            }

            pagos.forEach(pago => {
                const tr = document.createElement("tr");
                const pagoId = pago.id || "N/A"; // Ajusta el nombre del campo según el backend // Ajusta el nombre del campo según el backend
                tr.innerHTML = `
                    <td>${pagoId}</td>
                    <td>${pago.amount || "Sin monto"}</td>
                    <td>${pago.date || "Sin fecha"}</td>
                    <td>${pago.description || "Sin descripción"}</td>
                    <td>${pago.method || "Sin método"}</td>
                    <td>${pago.status || "Sin estado"}</td>
                    <td>
                        <button class="btn-edit" data-id="${pagoId}"><i class="fas fa-edit"></i></button>
                        <button class="btn-delete" data-id="${pagoId}"><i class="fas fa-trash-alt"></i></button>
                    </td>
                `;
                tbody.appendChild(tr);
            });

            agregarEventosBotones();
        })
        .catch(error => {
            console.error("❌ Error al actualizar la tabla de pagos:", error);
        });
}

function eliminarPago(id) {
    if (!id || isNaN(parseInt(id))) {
        alert("ID de pago no válido");
        return;
    }

    if (confirm("¿Eliminar este pago?")) {
        fetch(`http://localhost:8080/api/v1/payment/${id}`, {
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
            alert(text); // Muestra "Pago eliminado correctamente"
            actualizarTablaPagos();
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Error al eliminar el pago: " + error.message);
        });
    }
}

function agregarEventosBotones() {
    // Eventos para botones de eliminar
    document.querySelectorAll(".btn-delete").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            eliminarPago(id);
        });
    });

    // Eventos para botones de editar
    document.querySelectorAll(".btn-edit").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            editarPago(id);
        });
    });
}

function editarPago(id) {
    // Obtener los datos del pago por ID
    fetch(`http://localhost:8080/api/v1/payment/${id}`, {
        method: "GET",
        headers: {
            "Accept": "application/json"
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error al obtener los datos del pago");
        }
        return response.json();
    })
    .then(pago => {
        // Cargar los datos del pago en el formulario del modal
        document.getElementById("montoPago").value = pago.amount;
        document.getElementById("fechaPago").value = pago.date;
        document.getElementById("descripcionPago").value = pago.description;
        document.getElementById("metodoPago").value = pago.method;
        document.getElementById("estadoPago").value = pago.status;

        // Cambiar el texto del botón para indicar que es una edición
        const botonEnvioPago = document.getElementById("botonEnvioPago");
        botonEnvioPago.textContent = "Actualizar";
        botonEnvioPago.dataset.id = id; // Guardar el ID del pago en el botón

        // Abrir el modal
        const modal = new bootstrap.Modal(document.getElementById('exampleModal'));
        modal.show();
    })
    .catch(error => {
        console.error("Error al cargar los datos del pago:", error);
        alert("Error al cargar los datos del pago: " + error.message);
    });
}