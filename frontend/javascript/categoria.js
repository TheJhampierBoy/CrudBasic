document.addEventListener("DOMContentLoaded", function () {
    const botonEnvioCategoria = document.getElementById("botonEnvioCategoria");

    if (botonEnvioCategoria) {
        botonEnvioCategoria.addEventListener("click", async function (event) {
            event.preventDefault();

            // Obtener los valores del formulario
            const nombre = document.getElementById("nombreCategoria").value;
            const descripcion = document.getElementById("descripcionCategoria").value;

            // Validar que los campos no estén vacíos
            if (!nombre || !descripcion) {
                alert("Por favor, completa todos los campos.");
                return;
            }

            try {
                // Crear el objeto categoría
                const categoriaData = {
                    name: nombre,
                    description: descripcion
                };

                // Verificar si es una edición o una creación
                const id = botonEnvioCategoria.dataset.id;
                const url = id 
                    ? `http://localhost:8080/api/v1/category/${id}` // Edición
                    : "http://localhost:8080/api/v1/category/"; // Creación
                const method = id ? "PUT" : "POST";

                // Enviar la solicitud al backend
                const response = await fetch(url, {
                    method: method,
                    body: JSON.stringify(categoriaData),
                    headers: {
                        "Content-Type": "application/json"
                    }
                });

                if (!response.ok) {
                    const errorData = await response.json();
                    throw new Error(errorData.message || "Error al guardar la categoría");
                }

                // Limpiar los campos del formulario y el estado del botón
                document.getElementById("categoriaForm").reset();
                botonEnvioCategoria.textContent = "Enviar";
                delete botonEnvioCategoria.dataset.id;

                // Cerrar el modal
                const modal = bootstrap.Modal.getInstance(document.getElementById('exampleModal'));
                if (modal) {
                    modal.hide();
                }

                // Actualizar la tabla automáticamente
                actualizarTablaCategorias();

            } catch (error) {
                console.error("❌ Error al guardar la categoría:", error);
                alert("Error al guardar la categoría: " + error.message);
            }
        });
    }

    // Configurar el botón de búsqueda
    const botonBuscar = document.querySelector(".filtrar-categorias button");
    if (botonBuscar) {
        botonBuscar.addEventListener("click", function () {
            actualizarTablaCategorias();
        });
    }

    // Cargar la tabla al iniciar
    actualizarTablaCategorias();
});

// Función para obtener categorías desde el servidor
function obtenerCategorias() {
    return new Promise(async (resolve, reject) => {
        try {
            let url = 'http://localhost:8080/api/v1/category/filter';
            let filtro = document.getElementById("nameFilter").value;

            // Si hay un filtro, agregarlo a la URL
            if (filtro !== '') {
                url += `?name=${encodeURIComponent(filtro)}`;
            }

            let response = await fetch(url, {
                method: 'GET',
                headers: {
                    "Accept": "application/json"
                }
            });

            if (!response.ok) {
                throw new Error("Error al obtener las categorías");
            }

            const data = await response.json();
            resolve(data);
        } catch (error) {
            console.error("❌ Error al obtener las categorías:", error);
            reject(error);
        }
    });
}

// Función para actualizar la tabla de categorías
function actualizarTablaCategorias() {
    obtenerCategorias()
        .then(categorias => {
            const tbody = document.querySelector(".categorias-table tbody");
            tbody.innerHTML = "";

            if (categorias.length === 0) {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td colspan="4" class="text-center">
                        No se encontraron categorías con ese filtro
                    </td>
                `;
                tbody.appendChild(tr);
                return;
            }

            categorias.forEach(categoria => {
                const tr = document.createElement("tr");
                // Obtener el ID correctamente (usando id o categoryID)
                const categoriaId = categoria.id !== undefined ? categoria.id : 
                                  (categoria.categoryID !== undefined ? categoria.categoryID : "N/A");

                tr.innerHTML = `
                    <td>${categoriaId}</td>
                    <td>${categoria.name || "Sin nombre"}</td>
                    <td>${categoria.description || "Sin descripción"}</td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-edit" data-id="${categoriaId}">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn-delete" data-id="${categoriaId}">
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
            console.error("❌ Error al actualizar la tabla de categorías:", error);
            alert("Error al cargar las categorías: " + error.message);
        });
}

// Función para eliminar una categoría
function eliminarCategoria(id) {
    if (!id || id === "N/A") {
        alert("ID de categoría no válido");
        return;
    }

    if (confirm("¿Eliminar esta categoría?")) {
        fetch(`http://localhost:8080/api/v1/category/${id}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(text => {
            alert(text || "Categoría eliminada correctamente");
            actualizarTablaCategorias();
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Error al eliminar la categoría: " + error.message);
        });
    }
}

// Función para editar una categoría
function editarCategoria(id) {
    if (!id || id === "N/A") {
        alert("ID de categoría no válido");
        return;
    }

    fetch(`http://localhost:8080/api/v1/category/${id}`, {
        method: "GET",
        headers: {
            "Accept": "application/json"
        }
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => { throw new Error(text) });
        }
        return response.json();
    })
    .then(categoria => {
        // Cargar los datos en el formulario
        document.getElementById("nombreCategoria").value = categoria.name || "";
        document.getElementById("descripcionCategoria").value = categoria.description || "";

        // Configurar botón de actualización
        const boton = document.getElementById("botonEnvioCategoria");
        boton.textContent = "Actualizar";
        boton.dataset.id = id;

        // Mostrar modal
        new bootstrap.Modal(document.getElementById('exampleModal')).show();
    })
    .catch(error => {
        console.error("Error al cargar categoría:", error);
        alert("Error: " + error.message);
    });
}

// Función para agregar eventos a los botones
function agregarEventosBotones() {
    // Eventos para botones de eliminar
    document.querySelectorAll(".btn-delete").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            eliminarCategoria(id);
        });
    });

    // Eventos para botones de editar
    document.querySelectorAll(".btn-edit").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            editarCategoria(id);
        });
    });
}