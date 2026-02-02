(function() {
    'use strict';

    // ========================================
    // SIDEBAR TOGGLE LOGIC
    // ========================================
    function initSidebar() {
        const sidebar = document.getElementById('sidebar');
        const sidebarToggle = document.getElementById('sidebar-toggle');
        const sidebarClose = document.getElementById('sidebar-close');
        const sidebarOverlay = document.getElementById('sidebar-overlay');
        const body = document.body;

        function toggleSidebar() {
            body.classList.toggle('sidebar-open');
        }

        function closeSidebar() {
            body.classList.remove('sidebar-open');
        }

        if (sidebarToggle) {
            sidebarToggle.addEventListener('click', toggleSidebar);
        }

        if (sidebarClose) {
            sidebarClose.addEventListener('click', closeSidebar);
        }

        if (sidebarOverlay) {
            sidebarOverlay.addEventListener('click', closeSidebar);
        }

        // Close sidebar on window resize if mobile
        window.addEventListener('resize', function() {
            if (window.innerWidth >= 992 && body.classList.contains('sidebar-open')) {
                closeSidebar();
            }
        });
    }

    // ========================================
    // SPOTLIGHT EFFECT
    // ========================================
    function initSpotlight() {
        const cards = document.querySelectorAll('.event-card');

        document.addEventListener('mousemove', (e) => {
            cards.forEach(card => {
                const rect = card.getBoundingClientRect();
                const x = e.clientX - rect.left;
                const y = e.clientY - rect.top;
                card.style.setProperty('--mouse-x', `${x}px`);
                card.style.setProperty('--mouse-y', `${y}px`);
            });
        });
    }

    // ========================================
    // EVENT DETAILS MODAL
    // ========================================
    function initEventModal() {
        const modal = document.getElementById('modal-event-details');
        const detailButtons = document.querySelectorAll('.btn-details');
        const closeButtons = document.querySelectorAll('.modal-close, .modal-close-btn');

        // Open modal when clicking Details button
        detailButtons.forEach(btn => {
            btn.addEventListener('click', function() {
                const eventId = this.getAttribute('data-event-id');
                const eventTitle = this.getAttribute('data-event-title');
                const eventDescription = this.getAttribute('data-event-description');
                const eventType = this.getAttribute('data-event-type');
                const eventLocation = this.getAttribute('data-event-location');
                const eventDate = this.getAttribute('data-event-date');
                const eventCover = this.getAttribute('data-event-cover');

                // Populate modal
                document.getElementById('modal-event-title').textContent = eventTitle;
                document.getElementById('modal-event-type').textContent = eventType;
                document.getElementById('modal-event-date').textContent = eventDate;
                document.getElementById('modal-event-location').textContent = eventLocation;
                document.getElementById('modal-event-description-text').textContent = eventDescription || 'No description available.';

                // Set cover image
                const coverElement = document.getElementById('modal-event-cover');
                if (eventCover && eventCover !== 'null') {
                    coverElement.style.backgroundImage = `linear-gradient(to bottom, transparent, rgba(3,3,5,0.5)), url('${eventCover}')`;
                } else {
                    coverElement.style.backgroundImage = 'linear-gradient(135deg, rgba(34, 211, 238, 0.1), rgba(139, 92, 246, 0.1))';
                }

                // Show modal
                modal.classList.add('active');
                document.body.style.overflow = 'hidden';
            });
        });

        // Close modal
        closeButtons.forEach(btn => {
            btn.addEventListener('click', function() {
                modal.classList.remove('active');
                document.body.style.overflow = '';
            });
        });

        // Close modal when clicking overlay
        modal.addEventListener('click', function(e) {
            if (e.target === this) {
                this.classList.remove('active');
                document.body.style.overflow = '';
            }
        });

        // Close modal on ESC key
        document.addEventListener('keydown', function(e) {
            if (e.key === 'Escape' && modal.classList.contains('active')) {
                modal.classList.remove('active');
                document.body.style.overflow = '';
            }
        });
    }

    // ========================================
    // SEARCH & FILTER FUNCTIONALITY
    // ========================================
    function initSearchAndFilter() {
        const searchInput = document.getElementById('eventSearchInput');
        const filterButtons = document.querySelectorAll('.filter-btn');
        const eventCards = document.querySelectorAll('.event-card');

        let currentFilter = 'all';

        // Search functionality
        if (searchInput) {
            searchInput.addEventListener('input', function() {
                const searchTerm = this.value.toLowerCase();
                filterEvents(searchTerm, currentFilter);
            });
        }

        // Filter buttons
        filterButtons.forEach(btn => {
            btn.addEventListener('click', function() {
                // Remove active class from all buttons
                filterButtons.forEach(b => b.classList.remove('active'));

                // Add active class to clicked button
                this.classList.add('active');

                // Get filter type
                currentFilter = this.getAttribute('data-filter');

                // Filter events
                const searchTerm = searchInput ? searchInput.value.toLowerCase() : '';
                filterEvents(searchTerm, currentFilter);
            });
        });

        function filterEvents(searchTerm, filterType) {
            eventCards.forEach(card => {
                const title = card.querySelector('.event-title').textContent.toLowerCase();
                const description = card.querySelector('.event-description').textContent.toLowerCase();
                const eventType = card.querySelector('.event-type-badge').textContent.toLowerCase();
                const location = card.querySelector('.event-location span').textContent.toLowerCase();

                const matchesSearch = title.includes(searchTerm) ||
                    description.includes(searchTerm) ||
                    eventType.includes(searchTerm);

                let matchesFilter = true;
                if (filterType === 'virtual') {
                    matchesFilter = location.includes('remote') || location.includes('virtual') || location.includes('online');
                } else if (filterType === 'in-person') {
                    matchesFilter = !location.includes('remote') && !location.includes('virtual') && !location.includes('online');
                }

                if (matchesSearch && matchesFilter) {
                    card.style.display = '';
                } else {
                    card.style.display = 'none';
                }
            });
        }
    }

    // ========================================
    // ADD TO CALENDAR FUNCTIONALITY
    // ========================================
    function initAddToCalendar() {
        const calendarButtons = document.querySelectorAll('.btn-add-calendar, .modal-content .btn-save');

        calendarButtons.forEach(btn => {
            btn.addEventListener('click', function(e) {
                e.preventDefault();

                // Get event details from the card or modal
                let eventTitle, eventDate;

                if (this.closest('.event-card')) {
                    // From event card
                    const card = this.closest('.event-card');
                    eventTitle = card.querySelector('.event-title').textContent;
                    eventDate = card.querySelector('.event-date').textContent;
                } else {
                    // From modal
                    eventTitle = document.getElementById('modal-event-title').textContent;
                    eventDate = document.getElementById('modal-event-date').textContent;
                }

                // Simple notification (you can replace with actual calendar integration)
                showNotification(`Event "${eventTitle}" reminder set!`);
            });
        });
    }

    function showNotification(message) {
        // Create notification element
        const notification = document.createElement('div');
        notification.style.cssText = `
            position: fixed;
            top: 2rem;
            right: 2rem;
            background: rgba(34, 211, 238, 0.9);
            color: #000;
            padding: 1rem 1.5rem;
            border-radius: 12px;
            font-weight: 600;
            z-index: 9999;
            animation: slideIn 0.3s ease;
            box-shadow: 0 10px 30px rgba(34, 211, 238, 0.3);
        `;
        notification.textContent = message;

        // Add animation
        const style = document.createElement('style');
        style.textContent = `
            @keyframes slideIn {
                from { transform: translateX(400px); opacity: 0; }
                to { transform: translateX(0); opacity: 1; }
            }
            @keyframes slideOut {
                from { transform: translateX(0); opacity: 1; }
                to { transform: translateX(400px); opacity: 0; }
            }
        `;
        document.head.appendChild(style);

        document.body.appendChild(notification);

        // Remove after 3 seconds
        setTimeout(() => {
            notification.style.animation = 'slideOut 0.3s ease';
            setTimeout(() => {
                notification.remove();
                style.remove();
            }, 300);
        }, 3000);
    }

    // ========================================
    // INITIALIZATION
    // ========================================
    function init() {
        initSidebar();
        initSpotlight();
        initEventModal();
        initSearchAndFilter();
        initAddToCalendar();
    }

    // Run when DOM is ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }
})();