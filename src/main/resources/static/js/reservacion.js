document.addEventListener('DOMContentLoaded', function () {
    const actividadSelect = document.querySelector('select[name="actividad.idActividad"]');
    const fechaSelect = document.querySelector('select[name="fecha.idFecha"]');
    const planSelect = document.querySelector('select[name="plan.idPlan"]');

    function filtrarPorActividad() {
        const actId = actividadSelect.value;

        // Filtra fechas
        Array.from(fechaSelect.options).forEach(opt => {
            if (!opt.value) return; // Opción por defecto
            opt.style.display = (opt.getAttribute('data-act') === actId) ? '' : 'none';
        });
        fechaSelect.value = '';

        // Filtra planes
        Array.from(planSelect.options).forEach(opt => {
            if (!opt.value) return;
            opt.style.display = (opt.getAttribute('data-act') === actId) ? '' : 'none';
        });
        planSelect.value = '';
    }

    if (actividadSelect && fechaSelect && planSelect) {
        actividadSelect.addEventListener('change', filtrarPorActividad);
        filtrarPorActividad(); // Filtra al cargar la página
    }
});

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
        document.querySelectorAll('.text-open-delete'),'.modal-delete','.close-btn-modal',
        (openBtn, modal) => {

            //coge el id value de data-id de dicho btn
            const id = openBtn.getAttribute('data-id');
            const deleteBtn = modal.querySelector('#delete-confirm-btn');

            // si existe btn y id redirige a la url de eliminar
            if (deleteBtn && id) {
                deleteBtn.setAttribute('href', `/reservacion/eliminar/${id}`);
                //<a id="delete-confirm-btn" href="/planes/eliminar/5">Sí, eliminar</a>
            }
        }
    );

    // Modal de Agregar (si lo tienes)
    const addModalBtn = document.querySelector('#open-add-modal');
    if (addModalBtn) {
        setupModal([addModalBtn], '.modal-add', '.close-modal', (openBtn, modal) => {
            // Resetea el formulario al abrir el modal
            const form = modal.querySelector('form');
            if (form) {
                form.reset();
            }
            // Opcional: fuerza selects a la opción vacía
            const selects = form.querySelectorAll('select');
            selects.forEach(sel => {
                sel.value = '';
            });
        });
    }
});
