document.addEventListener('DOMContentLoaded', () => {

    // ==========================================
    // 1. ROBUST SIDEBAR LOGIC (Desktop & Mobile)
    // ==========================================
    const body = document.body;
    const sidebarToggle = document.getElementById('sidebar-toggle'); // Hamburger
    const sidebarClose = document.getElementById('sidebar-close');   // Cross Button
    const sidebarOverlay = document.getElementById('sidebar-overlay');

    function toggleSidebar() {
        // BREAKPOINT: 992px (Matches CSS @media queries)
        const isDesktop = window.innerWidth >= 992;

        if (isDesktop) {
            // DESKTOP LOGIC:
            // Sidebar is OPEN by default.
            // We toggle the 'sidebar-closed' class to hide it.
            body.classList.toggle('sidebar-closed');
        } else {
            // MOBILE LOGIC:
            // Sidebar is HIDDEN by default.
            // We toggle the 'sidebar-open' class to show it.
            body.classList.toggle('sidebar-open');
        }
    }

    if(sidebarToggle) sidebarToggle.addEventListener('click', toggleSidebar);
    if(sidebarClose) sidebarClose.addEventListener('click', toggleSidebar);
    if(sidebarOverlay) sidebarOverlay.addEventListener('click', toggleSidebar);


    // ==========================================
    // 2. MODAL LOGIC (Create Team)
    // ==========================================
    const modal = document.getElementById('modal-create-team');
    const btnCreate = document.getElementById('btn-create-team');

    // Safety check in case modal elements aren't rendered
    if (modal && btnCreate) {
        const btnCloseModal = modal.querySelector('.modal-close');
        const btnCancelModal = modal.querySelector('.btn-cancel');

        function openModal() {
            modal.classList.add('active');
            // Auto-focus the first input for better UX
            const firstInput = modal.querySelector('input');
            if(firstInput) firstInput.focus();
        }

        function closeModal() {
            modal.classList.remove('active');
        }

        btnCreate.addEventListener('click', openModal);

        if(btnCloseModal) btnCloseModal.addEventListener('click', closeModal);
        if(btnCancelModal) btnCancelModal.addEventListener('click', closeModal);

        // Close on overlay click (clicking outside the modal box)
        modal.addEventListener('click', (e) => {
            if (e.target === modal) closeModal();
        });
    }

    // ==========================================
    // 3. CLIENT-SIDE SEARCH (Filtering the Grid)
    // ==========================================
    const searchInput = document.getElementById('teamSearchInput');
    const teamCards = document.querySelectorAll('.team-card');

    if(searchInput) {
        searchInput.addEventListener('input', (e) => {
            const term = e.target.value.toLowerCase();

            teamCards.forEach(card => {
                const title = card.querySelector('.team-title').innerText.toLowerCase();
                const goal = card.querySelector('.team-goal').innerText.toLowerCase();

                // Simple partial match check
                if(title.includes(term) || goal.includes(term)) {
                    card.style.display = 'flex'; // Show matches
                } else {
                    card.style.display = 'none'; // Hide non-matches
                }
            });
        });
    }
});