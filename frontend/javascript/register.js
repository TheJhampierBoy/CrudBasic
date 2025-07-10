document.addEventListener('DOMContentLoaded', function () {
    // Configuración base
    const API_BASE_URL = "http://localhost:8080/api/v1";

    // Función para obtener los roles - CORREGIDA
    async function obtenerRoles() {
        const selectRoles = document.getElementById("opciones-rol");
        
        if (!selectRoles) {
            console.error("El elemento select para roles no existe.");
            return;
        }
        try {
            // Endpoint corregido (de /role/ a /roles/)
            const response = await fetch(`${API_BASE_URL}/roles/Listroles`);
            
            if (!response.ok) {
                throw new Error("Error al obtener los roles.");
            }

            const roles = await response.json();
            
            selectRoles.innerHTML = '<option value="">Seleccione un rol</option>';
            
            // Campos corregidos (id_roles y name_rol)
            roles.forEach(role => {
                const option = document.createElement("option");
                option.value = role.id_roles; // Cambiado de roleID a id_roles
                option.textContent = role.name_rol; // Cambiado de name a name_rol
                selectRoles.appendChild(option);
            });
        } catch (error) {
            console.error("Error al cargar los roles:", error);
            alert("No se pudieron cargar los roles. Intente nuevamente más tarde.");
        }
    }

    obtenerRoles();

    document.getElementById('registerForm').addEventListener('submit', async function (e) {
        e.preventDefault();
        
        // Obtener valores del formulario
        const name = document.getElementById('registerName').value.trim();
        const email = document.getElementById('registerEmail').value.trim();
        const password = document.getElementById('registerPassword').value;
        const number = document.getElementById('registerNumber')?.value.trim() || ""; // Campo añadido
        const roleId = document.getElementById('opciones-rol').value;
    
        // Validaciones mejoradas
        if (!name || !email || !password || !roleId) {
            alert("Por favor, complete todos los campos obligatorios.");
            return;
        }
    
        if (!/\S+@\S+\.\S+/.test(email)) {
            alert("Por favor, ingrese un correo electrónico válido.");
            return;
        }

        if (password.length < 6) {
            alert("La contraseña debe tener al menos 6 caracteres.");
            return;
        }
        
        // Estructura de datos CORREGIDA para coincidir con userDTO
        const requestBody = {
            name: name,
            email: email,
            password: password,
            number: number,
            roles: {  // Objeto roles con id_roles (no roleID)
                id_roles: parseInt(roleId)
            }
        };

        try {
            const response = await fetch(`${API_BASE_URL}/user/register`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(requestBody)
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || "Error al registrar el usuario");
            }

            console.log("Registro exitoso:", data);
            
            // Manejo de respuesta mejorado
            if (data.status === "CREATED" || response.status === 201) {
                alert("Usuario registrado correctamente. Redirigiendo...");
                window.location.href = 'index.html';
            } else {
                alert(data.message || "Registro completado pero con observaciones");
            }
            
        } catch (error) {
            console.error("Error en la petición:", error);
            alert("Error al registrar: " + error.message);
        }
    });
});