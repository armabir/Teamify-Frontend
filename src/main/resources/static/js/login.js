    // Password Visibility Toggle
    const togglePassword = document.querySelector('#togglePassword');
    const password = document.querySelector('#password');

    togglePassword.addEventListener('click', function (e) {
    // Toggle type
    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
    password.setAttribute('type', type);

    // Toggle icon
    this.classList.toggle('fa-eye');
    this.classList.toggle('fa-eye-slash');
});

    // Toast Auto-hide
    setTimeout(() => {
    const toasts = document.querySelectorAll('.toast');
    toasts.forEach(toast => {
    toast.style.opacity = '0';
    setTimeout(() => toast.remove(), 500);
});
}, 5000);
