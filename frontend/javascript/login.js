/**
 * Valida las credenciales del formulario de login
 * Usuario correcto: Ore
 * Contraseña correcta: 123456
 * @returns {boolean} Falso para evitar el envío del formulario
 */
function validateForm() {
    // Obtener valores de los campos
    const username = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    
    // Verificar credenciales hardcodeadas
    if (username === "Ore" && password === "123456") {
        // Redireccionar al usuario a la página de funcionalidad
        window.location.href = "Funcionalidad.html";
        return false; // Evita que el formulario se envíe
    } else {
        // Mostrar mensaje de error
        alert("Nombre de usuario o contraseña incorrectos. Inténtalo nuevamente.");
        return false; // Evita que el formulario se envíe
    }
}

// Mejoras opcionales - añadir efecto visual (puedes eliminar esta parte si prefieres mantener solo la validación)
document.addEventListener('DOMContentLoaded', function() {
    // Aplicar clase 'focused' cuando el usuario hace clic en un campo de entrada
    const inputs = document.querySelectorAll('input[type="text"], input[type="password"]');
    
    inputs.forEach(input => {
        input.addEventListener('focus', function() {
            this.classList.add('focused');
        });
        
        input.addEventListener('blur', function() {
            if (this.value.trim() === '') {
                this.classList.remove('focused');
            }
        });
    });
});