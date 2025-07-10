    document.addEventListener('DOMContentLoaded', function() {
        // Configuración
        const config = {
            apiBaseUrl: 'http://localhost:8080/api/v1',
            roles: {
                1: 'Administrador',
                2: 'Farmacéutico',
                3: 'Vendedor',
                4: 'Inventario'
            }
        };

        // Elementos del DOM
        const dom = {
            form: document.getElementById('registerForm'),
            userName: document.getElementById('userName'),
            userEmail: document.getElementById('userEmail'),
            userPassword: document.getElementById('userPassword'),
            userNumber: document.getElementById('userNumber'),
            userRoles: document.getElementById('userRoles'),
            btnRegister: document.getElementById('btnRegister'),
            btnText: document.getElementById('btnText'),
            btnSpinner: document.getElementById('btnSpinner'),
            errorAlert: document.getElementById('errorAlert'),
            successAlert: document.getElementById('successAlert')
        };

        // Inicialización
        initForm();
        loadRoles();

        function initForm() {
            if (dom.form) {
                dom.form.addEventListener('submit', handleSubmit);
            }
        }

        async function handleSubmit(e) {
            e.preventDefault();
            
            try {
                // Validar formulario
                const formData = validateForm();
                if (!formData) return;

                // Verificar reCAPTCHA
                if (!grecaptcha.getResponse()) {
                    showFeedback('Por favor, completa el reCAPTCHA.', 'error');
                    return;
                }

                // Mostrar carga
                setLoading(true);

                // Registrar usuario
                const response = await registerUser(formData);
                
                if (response && response.status === 'CREATED') {
                    showFeedback('¡Usuario registrado exitosamente!', 'success');
                    resetForm();
                    
                    // Redirigir después de 2 segundos
                    setTimeout(() => {
                        window.location.href = 'index.html';
                    }, 2000);
                } else {
                    const errorMsg = response?.message || 'Error al registrar usuario';
                    showFeedback(errorMsg, 'error');
                }

            } catch (error) {
                console.error('Error en registro:', error);
                showFeedback(error.message || 'Error al registrar usuario. Intente nuevamente.', 'error');
            } finally {
                setLoading(false);
                grecaptcha.reset();
            }
        }

// Dentro de user.js - versión completa corregida
function validateForm() {
    // Limpiar errores previos
    clearErrors();

    // Obtener valores del formulario
    const formData = {
        name: dom.userName.value.trim(),
        email: dom.userEmail.value.trim(),
        password: dom.userPassword.value,
        phone: dom.userNumber.value.trim(),
        role: dom.userRoles.value
    };

    // Validaciones
    let isValid = true;

    // Validación de nombre (al menos 3 caracteres)
    if (!formData.name || formData.name.length < 3) {
        markInvalid('userName', 'Nombre debe tener al menos 3 caracteres');
        isValid = false;
    }

    // Validación de email (formato correcto)
    if (!formData.email || !isValidEmail(formData.email)) {
        markInvalid('userEmail', 'Ingrese un email válido');
        isValid = false;
    }

    // Validación de contraseña (al menos 6 caracteres)
    if (!formData.password || formData.password.length < 6) {
        markInvalid('userPassword', 'La contraseña debe tener al menos 6 caracteres');
        isValid = false;
    }

    // Validación de teléfono (al menos 8 dígitos)
    if (!formData.phone || !isValidPhone(formData.phone)) {
        markInvalid('userNumber', 'Teléfono debe tener al menos 8 dígitos');
        isValid = false;
    }

    // Validación de rol (debe estar seleccionado)
    if (!formData.role) {
        markInvalid('userRoles', 'Seleccione un tipo de usuario');
        isValid = false;
    }

    return isValid ? formData : null;
}

// Funciones auxiliares para validación
function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function isValidPhone(phone) {
    return /^\d{8,}$/.test(phone);
}
async function registerUser(userData) {
    try {
        const response = await fetch(`${config.apiBaseUrl}/user/register`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({
                name: userData.name,
                email: userData.email,
                password: userData.password,
                number: userData.phone,
                roles: {
                    id_roles: parseInt(userData.role)
                }
            }),
            credentials: 'include' // Importante para cookies si las usas
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || `Error ${response.status} al registrar usuario`);
        }

        return await response.json();
    } catch (error) {
        console.error("Error en registerUser:", error);
        throw error;
    }
}async function loadRoles() {
    try {
        const response = await fetch(`${config.apiBaseUrl}/roles/Listroles`);
        
        if (!response.ok) {
            throw new Error(`Error: ${response.status}`);
        }

        const roles = await response.json();
        console.log("Roles recibidos:", roles);

        // Limpiar y llenar el select
        const roleSelect = document.getElementById('userRoles');
        roleSelect.innerHTML = '<option value="" disabled selected>Seleccione un rol</option>';
        
        roles.forEach(role => {
            const option = document.createElement('option');
            option.value = role.id_roles;
            option.textContent = role.name_rol;
            roleSelect.appendChild(option);
        });

    } catch (error) {
        console.error("Error al cargar roles:", error);
        // Mostrar roles de prueba si el backend falla
        const testRoles = [
            {id_roles: 1, name_rol: "Administrador"},
            {id_roles: 2, name_rol: "Farmacéutico"}
        ];
        
        const roleSelect = document.getElementById('userRoles');
        testRoles.forEach(role => {
            const option = document.createElement('option');
            option.value = role.id_roles;
            option.textContent = role.name_rol;
            roleSelect.appendChild(option);
        });
        
        showFeedback("Se cargaron roles de prueba", "error");
    }
}

        function showFeedback(message, type = 'error') {
            if (type === 'error') {
                dom.errorAlert.textContent = message;
                dom.errorAlert.classList.remove('d-none');
                dom.successAlert.classList.add('d-none');
            } else {
                dom.successAlert.textContent = message;
                dom.successAlert.classList.remove('d-none');
                dom.errorAlert.classList.add('d-none');
            }
            
            // Ocultar después de 5 segundos
            setTimeout(() => {
                if (type === 'error') {
                    dom.errorAlert.classList.add('d-none');
                } else {
                    dom.successAlert.classList.add('d-none');
                }
            }, 5000);
        }

        function setLoading(isLoading) {
            if (isLoading) {
                dom.btnText.textContent = 'Registrando...';
                dom.btnSpinner.classList.remove('d-none');
                dom.btnRegister.disabled = true;
            } else {
                dom.btnText.textContent = 'Registrar Usuario';
                dom.btnSpinner.classList.add('d-none');
                dom.btnRegister.disabled = false;
            }
        }

        function resetForm() {
            dom.form.reset();
            clearErrors();
        }

        function clearErrors() {
            document.querySelectorAll('.is-invalid').forEach(el => {
                el.classList.remove('is-invalid');
            });
            document.querySelectorAll('.invalid-feedback').forEach(el => {
                el.remove();
            });
        }

        function markInvalid(elementId, message) {
            const element = document.getElementById(elementId);
            if (!element) return;

            element.classList.add('is-invalid');
            
            const errorDiv = document.createElement('div');
            errorDiv.className = 'invalid-feedback';
            errorDiv.textContent = message;
            element.parentNode.appendChild(errorDiv);
        }

        function isValidEmail(email) {
            return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
        }

        function isValidPhone(phone) {
            return /^\d{8,}$/.test(phone);
        }
    });