
const sections = document.querySelectorAll("section");
const navLinks = document.querySelectorAll(".nav-link");

window.addEventListener("scroll", () => {
    let current = "";

    sections.forEach((section) => {
        const sectionTop = section.offsetTop - 100;
        if (scrollY >= sectionTop) {
            current = section.getAttribute("id");
        }
    });

    navLinks.forEach((link) => {
        link.classList.remove("active");
        if (link.getAttribute("href") === `#${current}`) {
            link.classList.add("active");
        }
    });
});

// JavaScript para manejar el modal de servicios
document.addEventListener('DOMContentLoaded', () => {
    const modal = document.querySelector('.services-modal');
    const openModalBtn = document.querySelector('.btn__more-services .text-open');
    const closeModalBtn = document.querySelector('.close .close-modal');

    openModalBtn.addEventListener('click', (e) => {
        e.preventDefault();
        modal.classList.add('show');
    });
    closeModalBtn.addEventListener('click', () => {
        modal.classList.remove('show');
    });

    // Cerrar el modal al hacer clic fuera de la caja
    window.addEventListener('click', (e) => {
        if (e.target === modal) {
            modal.classList.remove('show');
        }
    });
});


// Galeria

// Obtener el modal
var modal = document.getElementById("modal");

// Obtener la imagen modal y el elemento <img> dentro del modal
var modalImg = document.getElementById("modal-image");

// Obtener todas las imágenes de la galería
var galleryItems = document.getElementsByClassName("galeria-item");

// Variables para seguir la imagen actual
var currentIndex;

// Función para mostrar la imagen en el modal
function showImage(index) {
    modal.style.display = "block";
    modalImg.src = galleryItems[index].src;
    currentIndex = index;
}

// Recorrer cada imagen y agregar el evento de clic
for (var i = 0; i < galleryItems.length; i++) {
    galleryItems[i].onclick = (function (index) {
        return function () {
            showImage(index);
        }
    })(i);
}

// Obtener el elemento de cerrar modal
var span = document.getElementsByClassName("close")[0];

// Cuando el usuario haga clic en <span> (x), cerrar el modal
span.onclick = function () {
    modal.style.display = "none";
}

// Obtener los botones de navegación
var prev = document.getElementById("prev");
var next = document.getElementById("next");

// Función para mostrar la imagen anterior
prev.onclick = function () {
    currentIndex = (currentIndex === 0) ? galleryItems.length - 1 : currentIndex - 1;
    showImage(currentIndex);
}

// Función para mostrar la siguiente imagen
next.onclick = function () {
    currentIndex = (currentIndex === galleryItems.length - 1) ? 0 : currentIndex + 1;
    showImage(currentIndex);
}



document.addEventListener('DOMContentLoaded', function () {
    // Obtener el modal y el botón de cerrar
    const modal = document.querySelector('.services-modal');
    const closeModalBtn = document.querySelector('.close-modal');

    // Función para mostrar el modal
    function showModal() {
        modal.style.display = 'flex';
    }

    // Función para cerrar el modal
    function closeModal() {
        modal.style.display = 'none';
    }

    // Evento de clic para el botón de cerrar
    closeModalBtn.addEventListener('click', closeModal);

    // Evento de clic para cerrar el modal si se hace clic fuera del contenido
    modal.addEventListener('click', function (event) {
        if (event.target === modal) {
            closeModal();
        }
    });
});


// COMENTARIOS // COMENTARIOS// COMENTARIOS// COMENTARIOS// COMENTARIOS//
//.btn-modal-comment .text-modal-comment
//close-modal close-modal-comment

// JavaScript para manejar el modal de COMENTARIOS
document.addEventListener('DOMContentLoaded', () => {
    const modal = document.querySelector('.comment-modal');
    const openModalBtn = document.querySelector('.btn-modal-comment .text-modal-comment');
    const closeModalBtn = document.querySelector('.close-modal-comment');

    openModalBtn.addEventListener('click', (e) => {
        e.preventDefault();
        modal.classList.add('show');
    });

    closeModalBtn.addEventListener('click', () => {
        modal.classList.remove('show');
    });

    // Cerrar el modal al hacer clic fuera de la caja
    window.addEventListener('click', (e) => {
        if (e.target === modal) {
            modal.classList.remove('show');
        }
    });
});





//navbar sticky shadow
window.addEventListener("scroll", function () {
    const sticky = document.querySelector(".sticky-container");
    const navbar = document.querySelector("#navbar");
    if (window.scrollY > 205) {
        sticky.classList.add("shadow");
        navbar.classList.add("border-none");
    } else {
        sticky.classList.remove("shadow");
        navbar.classList.remove("border-none");
    }
});
