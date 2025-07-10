// Datos de ejemplo para los productos
const productos = [
    {
        id: 1,
        nombre: "Vitaminas Multivitamínicas",
        descripcion: "Complejo vitamínico para reforzar el sistema inmunológico",
        precio: 24.99,
        imagen: "https://via.placeholder.com/300x200/1a6bb2/ffffff?text=Vitaminas",
        destacado: true
    },
    {
        id: 2,
        nombre: "Analgésicos Extra Fuertes",
        descripcion: "Alivio rápido para dolores intensos",
        precio: 12.50,
        imagen: "https://via.placeholder.com/300x200/1a6bb2/ffffff?text=Analgesicos"
    },
    {
        id: 3,
        nombre: "Crema Hidratante Facial",
        descripcion: "Hidratación profunda para todo tipo de piel",
        precio: 18.75,
        imagen: "https://via.placeholder.com/300x200/1a6bb2/ffffff?text=Crema+Facial"
    },
    {
        id: 4,
        nombre: "Termómetro Digital",
        descripcion: "Medición precisa y rápida de temperatura",
        precio: 15.99,
        imagen: "https://via.placeholder.com/300x200/1a6bb2/ffffff?text=Termometro",
        destacado: true
    },
    {
        id: 5,
        nombre: "Protector Solar FPS 50",
        descripcion: "Protección avanzada contra rayos UVA/UVB",
        precio: 22.50,
        imagen: "https://via.placeholder.com/300x200/1a6bb2/ffffff?text=Protector+Solar"
    },
    {
        id: 6,
        nombre: "Gel Antibacterial",
        descripcion: "Elimina el 99.9% de bacterias sin agua",
        precio: 8.99,
        imagen: "https://via.placeholder.com/300x200/1a6bb2/ffffff?text=Gel+Antibacterial"
    },
    {
        id: 7,
        nombre: "Suplemento de Omega 3",
        descripcion: "Ácidos grasos esenciales para la salud cardiovascular",
        precio: 32.99,
        imagen: "https://via.placeholder.com/300x200/1a6bb2/ffffff?text=Omega+3"
    },
    {
        id: 8,
        nombre: "Kit de Primeros Auxilios",
        descripcion: "Todo lo necesario para emergencias básicas",
        precio: 45.50,
        imagen: "https://via.placeholder.com/300x200/1a6bb2/ffffff?text=Kit+Primeros+Auxilios",
        destacado: true
    }
];

// Función para cargar los productos en la página
function cargarProductos() {
    const productosContainer = document.getElementById('productos-lista');
    
    // Limpiar el contenedor por si hubiera contenido previo
    productosContainer.innerHTML = '';
    
    productos.forEach((producto) => {
        const productoElement = document.createElement('div');
        productoElement.className = `producto ${producto.destacado ? 'destacado' : ''}`;
        
        productoElement.innerHTML = `
            <img src="${producto.imagen}" alt="${producto.nombre}">
            <div class="producto-info">
                <div>
                    <h3>${producto.nombre}</h3>
                    <p>${producto.descripcion}</p>
                </div>
                <div class="precio">$${producto.precio.toFixed(2)}</div>
            </div>
        `;
        
        productosContainer.appendChild(productoElement);
    });
}

// Funcionalidad para botones de navegación
function inicializarNavegacion() {
    const navLinks = document.querySelectorAll('nav a');
    
    navLinks.forEach(link => {
        link.addEventListener('mouseenter', (e) => {
            // Animación adicional al pasar el mouse (opcional)
            const ripple = document.createElement('span');
            ripple.style.position = 'absolute';
            ripple.style.background = 'rgba(255, 255, 255, 0.7)';
            ripple.style.width = '5px';
            ripple.style.height = '5px';
            ripple.style.borderRadius = '50%';
            ripple.style.transformOrigin = 'center';
            ripple.style.left = (e.offsetX) + 'px';
            ripple.style.top = (e.offsetY) + 'px';
            ripple.style.animation = 'ripple 0.6s linear';
            
            link.appendChild(ripple);
            
            setTimeout(() => {
                ripple.remove();
            }, 600);
        });
    });
}

// Función para añadir estilos de animación adicionales
function añadirEstilosAnimacion() {
    const styleElement = document.createElement('style');
    styleElement.textContent = `
        @keyframes ripple {
            to {
                transform: scale(15);
                opacity: 0;
            }
        }
    `;
    document.head.appendChild(styleElement);
}

// Función para ajustar alturas de productos para que todos queden alineados
function igualarAlturasProductos() {
    // Ejecutar después de que las imágenes cargan
    window.addEventListener('load', () => {
        const productos = document.querySelectorAll('.producto');
        let maxAlturaTitulo = 0;
        let maxAlturaDescripcion = 0;
        
        // Primero, encontrar las alturas máximas
        productos.forEach(producto => {
            const titulo = producto.querySelector('h3');
            const descripcion = producto.querySelector('p');
            
            if (titulo && titulo.offsetHeight > maxAlturaTitulo) {
                maxAlturaTitulo = titulo.offsetHeight;
            }
            
            if (descripcion && descripcion.offsetHeight > maxAlturaDescripcion) {
                maxAlturaDescripcion = descripcion.offsetHeight;
            }
        });
        
        // Luego, aplicar las alturas máximas a todos los elementos
        productos.forEach(producto => {
            const titulo = producto.querySelector('h3');
            const descripcion = producto.querySelector('p');
            
            if (titulo) {
                titulo.style.height = `${maxAlturaTitulo}px`;
            }
            
            if (descripcion) {
                descripcion.style.height = `${maxAlturaDescripcion}px`;
            }
        });
    });
}

// Evento principal cuando el DOM está cargado
document.addEventListener('DOMContentLoaded', function() {
    añadirEstilosAnimacion();
    cargarProductos();
    inicializarNavegacion();
    igualarAlturasProductos();
});