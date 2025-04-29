document.addEventListener("DOMContentLoaded", function () {
    const botonEnvioPrescripcion = document.getElementById("botonEnvioPrescripcion");

    if (botonEnvioPrescripcion) {
        botonEnvioPrescripcion.addEventListener("click", async function (event) {
            event.preventDefault();
        
            // Obtener los valores del formulario
            const doctorName = document.getElementById("doctorName").value;
            const prescriptionDate = document.getElementById("prescriptionDate").value;

            // Validar que los campos no estén vacíos
            if (!doctorName || !prescriptionDate) {
                alert("Por favor, completa todos los campos.");
                return;
            }

            // Validar que la fecha no sea futura
            const fechaActual = new Date();
            const fechaPrescripcion = new Date(prescriptionDate);
            if (fechaPrescripcion > fechaActual) {
                alert("La fecha de la prescripción no puede ser en el futuro.");
                return;
            }

            // Validar que el doctor no tenga más de 80 años
            const edadMaximaDoctor = 1;
            const anioActual = fechaActual.getFullYear();
            const anioPrescripcion = fechaPrescripcion.getFullYear();
            if (anioActual - anioPrescripcion > edadMaximaDoctor) {
                alert(`La prescripcion no puede tener más de ${edadMaximaDoctor} años.`);
                return;
            }
        
            // Crear el objeto prescripción
            const prescripcionData = {
                doctorName: doctorName,
                prescriptionDate: prescriptionDate
            };
        
            // Verificar si es una edición o una creación
            const id = botonEnvioPrescripcion.dataset.id;
            const url = id 
                ? `http://localhost:8080/api/v1/prescription/${id}` // Edición
                : "http://localhost:8080/api/v1/prescription/"; // Creación
            const method = id ? "PUT" : "POST";
        
            try {
                // Enviar la solicitud al backend
                const response = await fetch(url, {
                    method: method,
                    body: JSON.stringify(prescripcionData),
                    headers: {
                        "Content-Type": "application/json"
                    }
                });
        
                if (!response.ok) {
                    const errorData = await response.json();
                    throw new Error(errorData.message || "Error al guardar la prescripción");
                }
        
                // Limpiar los campos del formulario y el estado del botón
                document.getElementById("prescripcionForm").reset();
                botonEnvioPrescripcion.textContent = "Guardar";
                delete botonEnvioPrescripcion.dataset.id;
        
                // Cerrar el modal
                const modal = bootstrap.Modal.getInstance(document.getElementById('exampleModal'));
                if (modal) {
                    modal.hide();
                }
        
                // Actualizar la tabla automáticamente
                actualizarTablaPrescripciones();
        
            } catch (error) {
                console.error("❌ Error al guardar la prescripción:", error);
                alert("Error al guardar la prescripción: " + error.message);
            }
        });
    }

    // Configurar el botón de búsqueda
    const botonBuscar = document.querySelector(".filtrar-prescripciones button");
    if (botonBuscar) {
        botonBuscar.addEventListener("click", function () {
            actualizarTablaPrescripciones();
        });
    }

    // Cargar la tabla al iniciar
    actualizarTablaPrescripciones();
});

function obtenerPrescripciones() {
    return new Promise(async (resolve, reject) => {
        try {
            let url = 'http://localhost:8080/api/v1/prescription/filter';
            let filtro = document.getElementById("doctorFilter").value;

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
                throw new Error("Error al obtener las prescripciones");
            }

            const data = await response.json();
            resolve(data);
        } catch (error) {
            console.error("❌ Error al obtener las prescripciones:", error);
            reject(error);
        }
    });
}

function actualizarTablaPrescripciones() {
    obtenerPrescripciones()
        .then(prescripciones => {
            const tbody = document.querySelector(".prescripciones-table tbody");
            tbody.innerHTML = "";

            if (prescripciones.length === 0) {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td colspan="4" class="text-center">
                        No se encontraron prescripciones con ese filtro
                    </td>
                `;
                tbody.appendChild(tr);
                return;
            }

            prescripciones.forEach(prescripcion => {
                const tr = document.createElement("tr");
                const prescripcionId = prescripcion.prescriptionID || "N/A";

                tr.innerHTML = `
                    <td>${prescripcionId}</td>
                    <td>${prescripcion.doctorName || "Sin nombre"}</td>
                    <td>${prescripcion.prescriptionDate || "Sin fecha"}</td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-edit" data-id="${prescripcionId}">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn-delete" data-id="${prescripcionId}">
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
            console.error("❌ Error al actualizar la tabla de prescripciones:", error);
        });
}

function agregarEventosBotones() {
    document.querySelectorAll(".btn-delete").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            eliminarPrescripcion(id);
        });
    });

    document.querySelectorAll(".btn-edit").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            editarPrescripcion(id);
        });
    });
}

function eliminarPrescripcion(id) {
    if (!id || isNaN(parseInt(id))) {
        alert("ID de prescripción no válido");
        return;
    }

    if (confirm("¿Eliminar esta prescripción?")) {
        fetch(`http://localhost:8080/api/v1/prescription/${id}`, {
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
            actualizarTablaPrescripciones();
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Error al eliminar la prescripción: " + error.message);
        });
    }
}

function editarPrescripcion(id) {
    fetch(`http://localhost:8080/api/v1/prescription/${id}`, {
        method: "GET",
        headers: {
            "Accept": "application/json"
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error al obtener los datos de la prescripción");
        }
        return response.json();
    })
    .then(prescripcion => {
        document.getElementById("doctorName").value = prescripcion.doctorName;
        document.getElementById("prescriptionDate").value = prescripcion.prescriptionDate;

        const botonEnvioPrescripcion = document.getElementById("botonEnvioPrescripcion");
        botonEnvioPrescripcion.textContent = "Actualizar";
        botonEnvioPrescripcion.dataset.id = id;

        const modal = new bootstrap.Modal(document.getElementById('exampleModal'));
        modal.show();
    })
    .catch(error => {
        console.error("Error al cargar los datos de la prescripción:", error);
        alert("Error al cargar los datos de la prescripción: " + error.message);
    });
}