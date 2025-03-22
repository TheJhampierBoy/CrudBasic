document.addEventListener("DOMContentLoaded", function () {
    const productos = [
        { id: 1, nombre: "Camiseta Negra", precio: 20, img: "images/camiseta.jpg" },
        { id: 2, nombre: "Pantalón Jeans", precio: 35, img: "images/jeans.jpg" },
        { id: 3, nombre: "Zapatillas Deportivas", precio: 50, img: "images/zapatillas.jpg" }
    ];

    let listaProductos = document.getElementById("productos-lista");

    if (listaProductos) {
        productos.forEach(producto => {
            let item = document.createElement("div");
            item.classList.add("producto");
            item.innerHTML = `
                <img src="${producto.img}" alt="${producto.nombre}">
                <h3>${producto.nombre}</h3>
                <p>$${producto.precio}</p>
                <button onclick="agregarAlCarrito(${producto.id})">Agregar al Carrito</button>
            `;
            listaProductos.appendChild(item);
        });
    }
});

function agregarAlCarrito(id) {
    alert("Producto agregado al carrito: " + id);
}
