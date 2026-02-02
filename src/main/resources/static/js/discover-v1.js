document.addEventListener('DOMContentLoaded', () => {
    const toggleBtn = document.getElementById('sidebar-toggle');
    const closeBtn = document.getElementById('sidebar-close'); // <--- The X button
    const body = document.body;

    // 1. Open Sidebar
    if (toggleBtn) {
        toggleBtn.addEventListener('click', () => {
            body.classList.add('sidebar-open');
        });
    }

    // 2. Close Sidebar (The functionality you requested)
    if (closeBtn) {
        closeBtn.addEventListener('click', () => {
            body.classList.remove('sidebar-open');
        });
    }
});