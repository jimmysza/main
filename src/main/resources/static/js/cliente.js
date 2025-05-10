document.addEventListener('DOMContentLoaded', () => {
    const setupModal = (openBtnSelector, modalSelector, closeBtnSelector) => {
        const openBtn = document.querySelector(openBtnSelector);
        const modal = document.querySelector(modalSelector);
        const closeBtn = modal.querySelector(closeBtnSelector);

        if (!openBtn || !modal || !closeBtn) return;

        openBtn.addEventListener('click', (e) => {
            e.preventDefault();
            modal.classList.add('show');
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

    setupModal('#open-add-modal', '.modal-add', '.close-modal');
    setupModal('.text-open-edit', '.modal-edit', '.close-modal');
    setupModal('.text-open-delete', '.modal-delete', '.close-btn-modal');
});

