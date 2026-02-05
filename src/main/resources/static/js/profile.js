(function() {
    'use strict';
// ========================================
    // SIDEBAR & TOGGLE LOGIC (UPDATED FROM TEAMS)
    // ========================================
    function initSidebar() {
        const body = document.body;
        const sidebarToggle = document.getElementById('sidebar-toggle');
        const sidebarClose = document.getElementById('sidebar-close');
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
    }

    function initToggleSections() {
        const toggleButtons = document.querySelectorAll('.toggle-btn');
        const contentSections = document.querySelectorAll('.content-section');

        toggleButtons.forEach(button => {
            button.addEventListener('click', function() {
                const targetSection = this.getAttribute('data-section');
                toggleButtons.forEach(btn => btn.classList.remove('active'));
                this.classList.add('active');
                contentSections.forEach(section => { section.classList.remove('active'); });

                const targetElement = document.getElementById(targetSection + '-section');
                if (targetElement) { targetElement.classList.add('active'); }
            });
        });
    }

    // ========================================
    // MODAL LOGIC (NEW)
    // ========================================
    function initModals() {
        // Modal Trigger Mapping
        // Inside initModals() -> triggers array
        const triggers = [
            { btnId: 'btn-add-project', modalId: 'modal-project' },
            { btnId: 'btn-add-experience', modalId: 'modal-experience' },
            { btnId: 'btn-add-education', modalId: 'modal-education' },
            // NEW:
            { btnId: 'btn-edit-pfp', modalId: 'modal-pfp' },
            { btnId: 'btn-edit-skills', modalId: 'modal-skills' },
            { btnId: 'btn-edit-interests', modalId: 'modal-interests' }
        ];

        // 1. Open Modal Logic
        triggers.forEach(trigger => {
            const btn = document.getElementById(trigger.btnId);
            const modal = document.getElementById(trigger.modalId);

            if (btn && modal) {
                btn.addEventListener('click', function() {
                    modal.classList.add('active');
                    document.body.style.overflow = 'hidden'; // Prevent background scrolling
                });
            }
        });

        // 2. Close Modal Logic (X button, Cancel button, Overlay click)
        const closeButtons = document.querySelectorAll('.modal-close, .modal-close-btn');
        const overlays = document.querySelectorAll('.modal-overlay');

        function closeModal(modal) {
            modal.classList.remove('active');
            document.body.style.overflow = '';
        }

        closeButtons.forEach(btn => {
            btn.addEventListener('click', function() {
                const modal = this.closest('.modal-overlay');
                closeModal(modal);
            });
        });

        overlays.forEach(overlay => {
            overlay.addEventListener('click', function(e) {
                if (e.target === this) { // Only close if clicking the dark overlay, not the card
                    closeModal(this);
                }
            });
        });

        // Close on ESC key
        document.addEventListener('keydown', function(e) {
            if (e.key === 'Escape') {
                const activeModal = document.querySelector('.modal-overlay.active');
                if (activeModal) closeModal(activeModal);
            }
        });

        // Form Submission Placeholder (Optional: prevents page reload for demo)
        // const forms = document.querySelectorAll('form');
        // forms.forEach(form => {
        //     form.addEventListener('submit', function(e) {
        //         e.preventDefault();
        //         alert('Form submitted! (Backend integration required)');
        //         const modal = this.closest('.modal-overlay');
        //         closeModal(modal);
        //     });
        // });
    }

    // ========================================
    // INITIALIZATION
    // ========================================
    function init() {
        initSidebar();
        initToggleSections();
        initModals(); // Initialize Modals
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }
})();