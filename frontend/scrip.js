document.addEventListener("DOMContentLoaded", function () {
    const productos = [
        { id: 1, nombre: "Crema Ponds Rejuvenecedora", precio: 1500, img: "./imagenes/ponds.jpg" },
        { id: 2, nombre: "Gel Facial Neutrogena", precio: 2000, img: "./imagenes/neu.jpg" },
        { id: 3, nombre: "Crema Nivea Hidratante", precio: 1200, img: "./imagenes/nivea.jpg" },
        { id: 4, nombre: "Serum Garnier Vitamina C", precio: 2500, img: "./imagenes/cc.jpg" },
        { id: 5, nombre: "Crema L'Oreal Antiarrugas", precio: 3000, img: "./imagenes/lo.jpg" },
        { id: 6, nombre: "Gel Limpiador Cetaphil", precio: 1800, img: "./imagenes/ceta.jpg" },
        { id: 7, nombre: "Crema Eucerin Regeneradora", precio: 2200, img: "./imagenes/eucerin.jpg" },
        { id: 8, nombre: "Crema Cerave", precio: 2700, img: "./imagenes/cerave.jpg" },
        { id: 9, nombre: "Crema Dr. Organic Facial", precio: 1900, img: "./imagenes/geeel.jpg" }
        
    ];

    const carouselContainer = document.getElementById("productos-lista");
    carouselContainer.innerHTML = `
        <div class="carousel-wrapper">
            <div class="carousel-inner">
                <div class="carousel-track"></div>
            </div>
            <div class="carousel-controls">
                <button class="carousel-btn prev-btn">&#10094;</button>
                <button class="carousel-btn next-btn">&#10095;</button>
            </div>
        </div>
    `;

    const track = carouselContainer.querySelector('.carousel-track');
    const prevBtn = carouselContainer.querySelector('.prev-btn');
    const nextBtn = carouselContainer.querySelector('.next-btn');

    // Render productos
    productos.forEach((producto) => {
        const slide = document.createElement('div');
        slide.classList.add('carousel-slide');
        slide.innerHTML = `
            <div class="producto-tarjeta">
                <img src="${producto.img}" alt="${producto.nombre}" class="producto-imagen">
                <div class="producto-info">
                    <h3>${producto.nombre}</h3>
                    <p class="producto-precio">$${producto.precio.toLocaleString()}</p>
                    <button onclick="agregarAlCarrito(${producto.id})" class="btn btn-producto">Agregar al Carrito</button>
                </div>
            </div>
        `;
        track.appendChild(slide);
    });

    const slides = track.querySelectorAll('.carousel-slide');
    const totalSlides = slides.length;

    let currentIndex = 0;

    // Estilo del track
    track.style.display = 'flex';
    track.style.transition = 'transform 0.3s ease';

    function updateCarousel() {
        const slideWidth = slides[0].offsetWidth;
        track.style.transform = `translateX(-${currentIndex * slideWidth}px)`;
    }

    nextBtn.addEventListener('click', () => {
        currentIndex = (currentIndex + 1) % totalSlides; // Bucle infinito hacia adelante
        updateCarousel();
    });

    prevBtn.addEventListener('click', () => {
        currentIndex = (currentIndex - 1 + totalSlides) % totalSlides; // Bucle infinito hacia atrás
        updateCarousel();
    });

    updateCarousel();
});

function agregarAlCarrito(id) {
    alert("Producto agregado al carrito: " + id);
}
