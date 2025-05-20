document.addEventListener('DOMContentLoaded', () => {
    const setupModal = (openBtnElements, modalSelector, closeBtnSelector, onOpenCallback) => {
        const modal = document.querySelector(modalSelector);
        const closeBtn = modal?.querySelector(closeBtnSelector);

        if (!modal || !closeBtn) return;

        openBtnElements.forEach(openBtn => {
            openBtn.addEventListener('click', (e) => {
                e.preventDefault();
                if (onOpenCallback) onOpenCallback(openBtn, modal);
                modal.classList.add('show');
            });
        });

        closeBtn.addEventListener('click', () => {
            modal.classList.remove('show');
        });

        window.addEventListener('click', (e) => {
            if (e.target === modal) {
                modal.classList.remove('show');
            }
        });
    };

    // Modal de Eliminar con ID dinámico
    setupModal(
        document.querySelectorAll('.text-open-delete'),
        '.modal-delete',
        '.close-btn-modal',
        (openBtn, modal) => {
            const id = openBtn.getAttribute('data-id');
            const deleteBtn = modal.querySelector('#delete-confirm-btn');
            if (deleteBtn && id) {
                deleteBtn.setAttribute('href', `/planes/eliminar/${id}`);
            }
        }
    );

    // Modal de Agregar (si lo tienes)
    const addModalBtn = document.querySelector('#open-add-modal');
    if (addModalBtn) {
        setupModal([addModalBtn], '.modal-add', '.close-modal');
    }
});



document.addEventListener('DOMContentLoaded', function () {
    
    // Obtiene el elemento <select> del colaborador por su ID
    const colaboradorSelect = document.getElementById('colaboradorSelect');

    // Obtiene el elemento <select> de actividad por su ID
    const actividadSelect = document.getElementById('actividadSelect');

    // Función que filtra las actividades según el colaborador seleccionado
    function filtrarActividades() {
        // Obtiene el ID del colaborador actualmente seleccionado
        const colabId = colaboradorSelect.value;

        // Recorre todas las opciones del <select> de actividades
        Array.from(actividadSelect.options).forEach(opt => {
            // Si la opción no tiene valor (ej. la opción "Selecciona una actividad"), la ignora
            if (!opt.value) return;

            // Compara el atributo "data-colab" de la opción con el ID del colaborador seleccionado
            // Si coinciden, muestra la opción; si no, la oculta
            opt.style.display = (opt.getAttribute('data-colab') === colabId) ? '' : 'none';
        });

        // Limpia la selección actual del <select> de actividad
        actividadSelect.value = '';
    }

    // Asocia la función anterior al evento "change" del <select> de colaborador
    // Cada vez que el usuario cambia el colaborador, se filtran las actividades
    colaboradorSelect.addEventListener('change', filtrarActividades);

    // Ejecuta la función una vez al cargar la página para filtrar inicialmente
    filtrarActividades();
});
