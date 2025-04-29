// Función para validar el formulario
function validateForm() {
    const username = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    
    // Validación básica
    if (username.trim() === '') {
        alert('Por favor, ingresa tu nombre de usuario');
        return false;
    }
    
    if (password.trim() === '') {
        alert('Por favor, ingresa tu contraseña');
        return false;
    }
    
    if (password.length < 6) {
        alert('La contraseña debe tener al menos 6 caracteres');
        return false;
    }
    
    // Aquí podrías agregar más validaciones según tus necesidades
    
    // Si todo está bien, el formulario se enviará
    return true;
}

// Efecto visual para los campos del formulario
document.addEventListener('DOMContentLoaded', function() {
    const inputs = document.querySelectorAll('input[type="text"], input[type="password"]');
    
    inputs.forEach(input => {
        // Añadir clase cuando el campo está enfocado
        input.addEventListener('focus', function() {
            this.parentElement.classList.add('active');
        });
        
        // Remover clase cuando pierde el foco
        input.addEventListener('blur', function() {
            if (this.value.trim() === '') {
                this.parentElement.classList.remove('active');
            }
        });
    });
});