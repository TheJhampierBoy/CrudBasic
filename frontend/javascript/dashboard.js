document.addEventListener('DOMContentLoaded', function() {
    const token = localStorage.getItem('jwtToken');
    
    if (!token) {
        window.location.href = 'login.html';
        return;
    }

    // Mostrar nombre de usuario
    const username = localStorage.getItem('username');
    if (username) {
        document.getElementById('userName').textContent = username;
    }

    // Cargar datos adicionales del usuario
    loadUserData();
    
    // Configurar logout
    document.getElementById('logoutBtn').addEventListener('click', function() {
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('userId');
        localStorage.removeItem('username');
        window.location.href = 'login.html';
    });
});

async function loadUserData() {
    try {
        const response = await fetch('http://localhost:8080/api/v1/user/profile', {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
            }
        });
        
        if (!response.ok) throw new Error('Error al cargar datos');
        
        const userData = await response.json();
        // Actualizar UI con datos del usuario
        
    } catch (error) {
        console.error(error);
        localStorage.removeItem('jwtToken');
        window.location.href = 'login.html';
    }
}