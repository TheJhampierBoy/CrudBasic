document.addEventListener("DOMContentLoaded", function () {
    const botonEnvioDroga = document.getElementById("botonEnvioDroga");

    if (botonEnvioDroga) {
        botonEnvioDroga.addEventListener("click", async function (event) {
            event.preventDefault();

            // Obtener los valores del formulario
            const drugName = document.getElementById("drugName").value;
            const drugDescription = document.getElementById("drugDescription").value;
            const drugPrice = document.getElementById("drugPrice").value;
            const stockQuantity = document.getElementById("stockQuantity").value;
            const expirationDate = document.getElementById("expirationDate").value;
            const categoryID = document.getElementById("categoryID").value;
            const supplierID = document.getElementById("supplierID").value;

            // Validar que los campos no estén vacíos
            if (!drugName || !drugPrice || !stockQuantity || !expirationDate || !categoryID || !supplierID) {
                alert("Por favor, completa todos los campos obligatorios.");
                return;
            }

            // Validar que la fecha de expiración no sea anterior a la fecha actual
            const fechaActual = new Date();
            const fechaExpiracion = new Date(expirationDate);
            if (fechaExpiracion < fechaActual) {
                alert("La fecha de expiración no puede ser anterior a la fecha actual.");
                return;
            }

            // Crear el objeto de droga
            const drugData = {
                name: drugName,
                description: drugDescription,
                price: parseFloat(drugPrice),
                stockQuantity: parseInt(stockQuantity, 10),
                expirationDate: expirationDate,
                categoryID: parseInt(categoryID, 10),
                supplierID: parseInt(supplierID, 10)
            };

            // Verificar si es una edición o una creación
            const id = botonEnvioDroga.dataset.id;
            const url = id
                ? `http://localhost:8080/api/v1/drug/${id}` // Edición
                : "http://localhost:8080/api/v1/drug/"; // Creación
            const method = id ? "PUT" : "POST";

            try {
                // Enviar la solicitud al backend
                const response = await fetch(url, {
                    method: method,
                    body: JSON.stringify(drugData),
                    headers: {
                        "Content-Type": "application/json"
                    }
                });

                if (!response.ok) {
                    const errorData = await response.json();
                    throw new Error(errorData.message || "Error al guardar la droga");
                }

                // Limpiar los campos del formulario y el estado del botón
                document.getElementById("drogaForm").reset();
                botonEnvioDroga.textContent = "Guardar";
                delete botonEnvioDroga.dataset.id;

                // Cerrar el modal
                const modal = bootstrap.Modal.getInstance(document.getElementById("exampleModal"));
                if (modal) {
                    modal.hide();
                }

                // Actualizar la tabla automáticamente
                actualizarTablaDrogas();
            } catch (error) {
                console.error("❌ Error al guardar la droga:", error);
                alert("Error al guardar la droga: " + error.message);
            }
        });
    }

    // Configurar el botón de búsqueda
    const botonBuscar = document.querySelector(".filtrar-drogas button");
    if (botonBuscar) {
        botonBuscar.addEventListener("click", function () {
            actualizarTablaDrogas();
        });
    }

    // Cargar la tabla al iniciar
    actualizarTablaDrogas();
});

function obtenerDrogas() {
    return new Promise(async (resolve, reject) => {
        try {
            let url = "http://localhost:8080/api/v1/drug/";
            const filtro = document.getElementById("drugFilter").value;

            // Si hay un filtro, agregarlo a la URL
            if (filtro) {
                url += `filter?name=${encodeURIComponent(filtro)}`;
            }

            const response = await fetch(url, {
                method: "GET",
                headers: {
                    "Accept": "application/json"
                }
            });

            if (!response.ok) {
                throw new Error("Error al obtener las drogas");
            }

            const data = await response.json();
            resolve(data);
        } catch (error) {
            console.error("❌ Error al obtener las drogas:", error);
            reject(error);
        }
    });
}

function actualizarTablaDrogas() {
    obtenerDrogas()
        .then(drogas => {
            const tbody = document.querySelector(".drogas-table tbody");
            tbody.innerHTML = "";

            if (drogas.length === 0) {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td colspan="9" class="text-center">
                        No se encontraron drogas con ese filtro.
                    </td>
                `;
                tbody.appendChild(tr);
                return;
            }

            drogas.forEach(droga => {
                const drugID = droga.drugID; // Captura correctamente el ID

                // Verifica si el ID existe antes de continuar
                if (!drugID) {
                    console.error("Error: drugID no está definido para la droga:", droga);
                }

                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td>${drugID || "N/A"}</td>
                    <td>${droga.name || "Sin nombre"}</td>
                    <td>${droga.description || "Sin descripción"}</td>
                    <td>${droga.price || "Sin precio"}</td>
                    <td>${droga.stockQuantity || "Sin stock"}</td>
                    <td>${droga.expirationDate || "Sin fecha"}</td>
                    <td>${droga.categoryID || "Sin categoría"}</td>
                    <td>${droga.supplierID || "Sin proveedor"}</td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-edit btn btn-warning btn-sm" data-id="${drugID || ""}">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn-delete btn btn-danger btn-sm" data-id="${drugID || ""}">
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
            console.error("❌ Error al actualizar la tabla de drogas:", error);
        });
}

function agregarEventosBotones() {
    document.querySelectorAll(".btn-delete").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            if (!id || id === "undefined" || id === "N/A") {
                alert("ID de droga no válido. Verifica la tabla y asegúrate de que el ID esté presente.");
                return;
            }
            eliminarDroga(id);
        });
    });

    document.querySelectorAll(".btn-edit").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            if (!id || id === "undefined" || id === "N/A") {
                alert("ID de droga no válido. Verifica la tabla y asegúrate de que el ID esté presente.");
                return;
            }
            editarDroga(id);
        });
    });
}

function eliminarDroga(id) {
    if (!id || isNaN(parseInt(id))) {
        alert("ID de droga no válido. Verifica la tabla y asegúrate de que el ID esté presente.");
        return;
    }

    if (confirm("¿Eliminar esta droga?")) {
        fetch(`http://localhost:8080/api/v1/drug/${id}`, {
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
            actualizarTablaDrogas();
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Error al eliminar la droga: " + error.message);
        });
    }
}

function editarDroga(id) {
    fetch(`http://localhost:8080/api/v1/drug/${id}`, {
        method: "GET",
        headers: {
            "Accept": "application/json"
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error al obtener los datos de la droga");
        }
        return response.json();
    })
    .then(droga => {
        document.getElementById("drugName").value = droga.name;
        document.getElementById("drugDescription").value = droga.description;
        document.getElementById("drugPrice").value = droga.price;
        document.getElementById("stockQuantity").value = droga.stockQuantity;
        document.getElementById("expirationDate").value = droga.expirationDate;
        document.getElementById("categoryID").value = droga.categoryID;
        document.getElementById("supplierID").value = droga.supplierID;

        const botonEnvioDroga = document.getElementById("botonEnvioDroga");
        botonEnvioDroga.textContent = "Actualizar";
        botonEnvioDroga.dataset.id = droga.drugID; // Asignar correctamente el ID

        const modal = new bootstrap.Modal(document.getElementById("exampleModal"));
        modal.show();
    })
    .catch(error => {
        console.error("Error al cargar los datos de la droga:", error);
        alert("Error al cargar los datos de la droga: " + error.message);
    });
}