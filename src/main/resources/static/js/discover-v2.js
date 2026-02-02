(function() {
    'use strict';

    // ========================================
    // SIDEBAR LOGIC (Identical to Profile)
    // ========================================
    function initSidebar() {
        const sidebar = document.getElementById('sidebar');
        const sidebarToggle = document.getElementById('sidebar-toggle');
        const sidebarClose = document.getElementById('sidebar-close');
        const sidebarOverlay = document.getElementById('sidebar-overlay');
        const body = document.body;

        function toggleSidebar() { body.classList.toggle('sidebar-open'); }
        function closeSidebar() { body.classList.remove('sidebar-open'); }

        if (sidebarToggle) sidebarToggle.addEventListener('click', toggleSidebar);
        if (sidebarClose) sidebarClose.addEventListener('click', closeSidebar);
        if (sidebarOverlay) sidebarOverlay.addEventListener('click', closeSidebar);

        window.addEventListener('resize', function() {
            if (window.innerWidth >= 1024 && body.classList.contains('sidebar-open')) {
                closeSidebar();
            }
        });
    }

    // ========================================
    // TAB SWITCHING LOGIC (Projects vs People)
    // ========================================
    function initToggleSections() {
        const toggleButtons = document.querySelectorAll('.toggle-btn');
        const contentSections = document.querySelectorAll('.content-section');

        toggleButtons.forEach(button => {
            button.addEventListener('click', function() {
                // Remove active class from all buttons
                toggleButtons.forEach(btn => btn.classList.remove('active'));
                // Add active class to clicked button
                this.classList.add('active');

                // Hide all sections
                contentSections.forEach(section => { section.classList.remove('active'); });

                // Show target section
                const targetId = this.getAttribute('data-section') + '-section';
                const targetElement = document.getElementById(targetId);
                if (targetElement) {
                    targetElement.classList.add('active');
                }
            });
        });
    }

    // Initialize
    document.addEventListener('DOMContentLoaded', () => {
        initSidebar();
        initToggleSections();
    });
})();