(function() {
    'use strict';

    // ========================================
    // 1. SIDEBAR TOGGLE LOGIC (Standardized)
    // ========================================
    function initSidebar() {
        const body = document.body;
        const sidebarToggle = document.getElementById('sidebar-toggle');
        const sidebarClose = document.getElementById('sidebar-close');
        const sidebarOverlay = document.getElementById('sidebar-overlay');

        function toggleSidebar() {
            // BREAKPOINT: 992px
            const isDesktop = window.innerWidth >= 992;

            if (isDesktop) {
                // DESKTOP: Toggle the CLOSED state
                body.classList.toggle('sidebar-closed');
            } else {
                // MOBILE: Toggle the OPEN state
                body.classList.toggle('sidebar-open');
            }
        }

        if(sidebarToggle) sidebarToggle.addEventListener('click', toggleSidebar);
        if(sidebarClose) sidebarClose.addEventListener('click', toggleSidebar);
        if(sidebarOverlay) sidebarOverlay.addEventListener('click', toggleSidebar);
    }

    // ========================================
    // 2. SEARCH & FILTER LOGIC
    // ========================================
    function initSearch() {
        const searchInput = document.getElementById('searchInput');
        const userCards = document.querySelectorAll('.user-card');
        const teamCards = document.querySelectorAll('.team-card');
        const noResultsMsg = document.getElementById('noResultsMsg');
        const noTeamsMsg = document.getElementById('noTeamsMsg');

        if (searchInput) {
            searchInput.addEventListener('keyup', (e) => {
                const query = e.target.value.toLowerCase().trim();
                let visibleCount = 0;

                // Filter Users
                userCards.forEach(card => {
                    const terms = card.getAttribute('data-search-terms') || card.innerText.toLowerCase();
                    if (terms.includes(query)) {
                        card.style.display = "flex";
                        visibleCount++;
                    } else {
                        card.style.display = "none";
                    }
                });

                // Filter Teams
                teamCards.forEach(card => {
                    const terms = card.getAttribute('data-search-terms') || card.innerText.toLowerCase();
                    if (terms.includes(query)) {
                        card.style.display = "flex";
                        visibleCount++;
                    } else {
                        card.style.display = "none";
                    }
                });

                // Toggle No Results
                if (visibleCount === 0 && noResultsMsg) {
                    noResultsMsg.style.display = "block";
                    if(noTeamsMsg) noTeamsMsg.style.display = "none";
                } else {
                    if(noResultsMsg) noResultsMsg.style.display = "none";
                }
            });
        }
    }

    // ========================================
    // 3. INITIALIZATION
    // ========================================
    function init() {
        initSidebar();
        initSearch();
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }
})();

// Helper for Filter Tags (kept global for HTML onclick attributes)
function filterType(type) {
    const userSection = document.getElementById('usersSection');
    const teamSection = document.getElementById('teamsSection');
    const btns = document.querySelectorAll('.filter-tag');

    if (type === 'all') {
        if(userSection) userSection.style.display = 'block';
        if(teamSection) teamSection.style.display = 'block';
    } else if (type === 'user') {
        if(userSection) userSection.style.display = 'block';
        if(teamSection) teamSection.style.display = 'none';
    } else if (type === 'team') {
        if(userSection) userSection.style.display = 'none';
        if(teamSection) teamSection.style.display = 'block';
    }

    // Update active class
    btns.forEach(btn => btn.classList.remove('active'));
    // Note: 'event' is available because this is called via onclick
    if(event && event.target) {
        event.target.classList.add('active');
    }
}