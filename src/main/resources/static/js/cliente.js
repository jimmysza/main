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

    // Modal de Editar con ID dinámico
    setupModal(
        document.querySelectorAll('.text-open-edit'),
        '.modal-edit',
        '.close-modal',
        (openBtn, modal) => {
            const id = openBtn.getAttribute('data-id');
            const editForm = modal.querySelector('form');
            const nombreInput = editForm.querySelector('input[name="persona.nombre"]');
            const emailInput = editForm.querySelector('input[name="persona.correoElectronico"]');
            const usuarioInput = editForm.querySelector('input[name="usuario"]');
            const contrasenaInput = editForm.querySelector('input[name="contrasena"]');
            
            // Aquí puedes hacer una solicitud AJAX para obtener los datos del cliente con ese ID
            fetch(`/cliente/editar/${id}`)
                .then(response => response.json())
                .then(data => {
                    // Si la respuesta contiene datos, asigna los valores a los campos del formulario
                    if (data) {
                        nombreInput.value = data.persona.nombre || '';
                        emailInput.value = data.persona.correoElectronico || '';
                        usuarioInput.value = data.usuario || '';
                        contrasenaInput.value = ''; // Deja la contraseña vacía por seguridad
                    }
                })
                .catch(err => console.error('Error al obtener datos del cliente:', err));
        }
    );

    // Modal de Eliminar con ID dinámico
    setupModal(
        document.querySelectorAll('.text-open-delete'),
        '.modal-delete',
        '.close-btn-modal',
        (openBtn, modal) => {
            const id = openBtn.getAttribute('data-id');
            const deleteBtn = modal.querySelector('#delete-confirm-btn');
            if (deleteBtn && id) {
                deleteBtn.setAttribute('href', `/cliente/eliminar/${id}`);
            }
        }
    );

    // Modal de Agregar (si lo tienes)
    const addModalBtn = document.querySelector('#open-add-modal');
    if (addModalBtn) {
        setupModal([addModalBtn], '.modal-add', '.close-modal');
    }
});
