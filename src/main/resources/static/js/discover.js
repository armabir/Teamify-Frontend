document.addEventListener('DOMContentLoaded', () => {

    /* ========================================
       SIDEBAR TOGGLE LOGIC (Visible Default)
       ======================================== */
    const toggleBtn = document.getElementById('sidebar-toggle'); // Hamburger
    const closeBtn = document.getElementById('sidebar-close');   // X Button
    const overlay = document.getElementById('sidebar-overlay');
    const body = document.body;

    // Default Desktop: Sidebar is Open.
    // Click 'X' -> Add 'sidebar-closed'
    if (closeBtn) {
        closeBtn.addEventListener('click', () => {
            // Check if mobile or desktop logic needed
            if (window.innerWidth < 992) {
                body.classList.remove('sidebar-open'); // Mobile close
            } else {
                body.classList.add('sidebar-closed');  // Desktop hide
            }
        });
    }

    // Click 'Hamburger' -> Remove 'sidebar-closed'
    if (toggleBtn) {
        toggleBtn.addEventListener('click', () => {
            if (window.innerWidth < 992) {
                body.classList.add('sidebar-open');   // Mobile open
            } else {
                body.classList.remove('sidebar-closed'); // Desktop show
            }
        });
    }

    if (overlay) {
        overlay.addEventListener('click', () => {
            body.classList.remove('sidebar-open');
        });
    }

    /* ========================================
       SEARCH & FILTER LOGIC
       ======================================== */
    const searchInput = document.getElementById('searchInput');
    const userCards = document.querySelectorAll('.user-card');
    const teamCards = document.querySelectorAll('.team-card');
    const noResultsMsg = document.getElementById('noResultsMsg');

    // Simple search function
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
                // Hide empty state messages if searching
                const noTeams = document.getElementById('noTeamsMsg');
                if(noTeams) noTeams.style.display = "none";
            } else {
                if(noResultsMsg) noResultsMsg.style.display = "none";
            }
        });
    }
});

// Helper for Filter Tags (All/Devs/Teams)
function filterType(type) {
    const userSection = document.getElementById('usersSection');
    const teamSection = document.getElementById('teamsSection');

    if (type === 'all') {
        userSection.style.display = 'block';
        teamSection.style.display = 'block';
    } else if (type === 'user') {
        userSection.style.display = 'block';
        teamSection.style.display = 'none';
    } else if (type === 'team') {
        userSection.style.display = 'none';
        teamSection.style.display = 'block';
    }

    // Update active class on buttons
    const btns = document.querySelectorAll('.filter-tag');
    btns.forEach(btn => btn.classList.remove('active'));
    event.target.classList.add('active');
}