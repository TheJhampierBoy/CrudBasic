document.addEventListener("DOMContentLoaded", function () {
    // Función para iniciar el contador
    function startCounter(element, start, end, duration) {
        let range = end - start;
        let current = start;
        let increment = end > start ? 1 : -1;
        let stepTime = Math.abs(Math.floor(duration / range));
        let timer = setInterval(function () {
            current += increment;
            element.innerHTML = `+${current.toLocaleString()}`;
            if (current === end) {
                clearInterval(timer);
            }
        }, stepTime);
    }

    // Verifica si un elemento está en el viewport
    function isElementInViewport(el) {
        let rect = el.getBoundingClientRect();
        return rect.top >= 0 && rect.bottom <= window.innerHeight;
    }

    // Inicia el conteo al hacer scroll
    function startCountingOnScroll() {
        let counters = document.querySelectorAll(".texto-superpuesto strong");
        let started = false;

        window.addEventListener("scroll", function () {
            if (!started && counters.length > 0 && isElementInViewport(counters[0])) {
                counters.forEach(counter => {
                    let endValue = parseInt(counter.getAttribute("data-target"));
                    startCounter(counter, 0, endValue, 2000);
                });
                started = true;
            }
        });
    }

    // Inicializa el conteo
    startCountingOnScroll();

    // Función para manejar el menú
    function setupMenu() {
        const menuToggle = document.getElementById('menu-toggle');
        const menu = document.getElementById('menu');

        if (menuToggle && menu) {
            menuToggle.addEventListener('click', function () {
                menu.classList.toggle('active');
                menuToggle.classList.toggle('active');
            });

            // Cierra el menú al hacer clic en un enlace
            const menuLinks = document.querySelectorAll('.menu a');
            menuLinks.forEach(link => {
                link.addEventListener('click', function () {
                    menu.classList.remove('active');
                    menuToggle.classList.remove('active');
                });
            });
        }
    }

    // Inicializa el menú
    setupMenu();
});