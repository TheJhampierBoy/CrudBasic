document.addEventListener('DOMContentLoaded', function() {
    // Configuración
    const config = {
        apiBaseUrl: 'http://localhost:8080/api/v1'
    };

    // Elementos del DOM (actualizados)
    const dom = {
        loginForm: document.getElementById('loginForm'),
        usernameInput: document.getElementById('username'),
        passwordInput: document.getElementById('password'),
        btnLogin: document.querySelector('#loginForm button[type="submit"]'),
        errorAlert: document.getElementById('loginError'),
        // Elementos opcionales (verificamos si existen)
        loginNavItem: document.getElementById('iniciar'),
        navbar: document.getElementById('navbarSupportedContent'),
        footer: document.getElementById('footer')
    };

    // Verificar si ya está logueado
    checkAuthStatus();

    if (dom.loginForm) {
        dom.loginForm.addEventListener('submit', handleLogin);
    }

async function handleLogin(e) {
    e.preventDefault();
    try {
        const response = await fetch('http://localhost:8080/api/v1/auth/login', {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({
                username: dom.usernameInput.value,
                password: dom.passwordInput.value
            }),
            credentials: 'include'
        });

        if (!response.ok) throw new Error('Credenciales incorrectas');
        
        const data = await response.json();
        localStorage.setItem('jwtToken', data.token);
        window.location.href = 'Funcionalidad.html';
    } catch (error) {
        console.error('Error de conexión:', error);
        showError('No se pudo conectar al servidor. Verifica:');
        alert('¿El backend está corriendo? Revisa la consola de Spring Boot');
    }
}
    function checkAuthStatus() {
        const token = localStorage.getItem('jwtToken');
        if (token) {
            window.location.href = 'Funcionalidad.html';
        }
    }

    function showError(message) {
        if (dom.errorAlert) {
            dom.errorAlert.textContent = message;
            dom.errorAlert.classList.remove('d-none');
            setTimeout(() => {
                if (dom.errorAlert) {
                    dom.errorAlert.classList.add('d-none');
                }
            }, 5000);
        }
    }

    function manageSessionElements(isLoggedIn) {
        try {
       
            if (dom.loginNavItem) {
                dom.loginNavItem.style.display = isLoggedIn ? 'none' : 'block';
            }
            

        } catch (error) {
            console.error("Error gestionando elementos de sesión:", error);
        }
    }
});