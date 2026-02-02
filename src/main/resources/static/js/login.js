document.addEventListener('DOMContentLoaded', () => {

    // 1. Existing Logic: Password Toggle
    const togglePassword = document.querySelector('#togglePassword');
    const password = document.querySelector('#password');

    if (togglePassword && password) {
        togglePassword.addEventListener('click', function (e) {
            const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
            password.setAttribute('type', type);
            this.classList.toggle('fa-eye');
            this.classList.toggle('fa-eye-slash');
        });
    }

    // 2. Existing Logic: Toast Auto-hide
    setTimeout(() => {
        const toasts = document.querySelectorAll('.toast');
        toasts.forEach(toast => {
            toast.style.opacity = '0';
            setTimeout(() => toast.remove(), 500);
        });
    }, 5000);

    // 3. New Theme: Three.js Particles
    const initThreeJS = () => {
        const canvas = document.getElementById('webgl-canvas');
        if (!canvas) return;
        const scene = new THREE.Scene();
        const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
        const renderer = new THREE.WebGLRenderer({ canvas: canvas, alpha: true, antialias: true });
        renderer.setSize(window.innerWidth, window.innerHeight);

        const geometry = new THREE.BufferGeometry();
        const particlesCount = 1500; // Slightly fewer for Login to keep it clean
        const posArray = new Float32Array(particlesCount * 3);
        for(let i = 0; i < particlesCount * 3; i++) { posArray[i] = (Math.random() - 0.5) * 20; }
        geometry.setAttribute('position', new THREE.BufferAttribute(posArray, 3));
        const material = new THREE.PointsMaterial({ size: 0.02, color: 0x8b5cf6, transparent: true, opacity: 0.6 }); // Violet particles
        const particlesMesh = new THREE.Points(geometry, material);
        scene.add(particlesMesh);

        camera.position.z = 5;
        const tick = () => {
            particlesMesh.rotation.y += 0.0005;
            renderer.render(scene, camera);
            window.requestAnimationFrame(tick);
        }
        tick();
    };

    // 4. New Theme: Spotlight Effect
    const initSpotlight = () => {
        const card = document.querySelector('.bento-card');
        document.addEventListener('mousemove', (e) => {
            if (!card) return;
            const rect = card.getBoundingClientRect();
            card.style.setProperty('--mouse-x', `${e.clientX - rect.left}px`);
            card.style.setProperty('--mouse-y', `${e.clientY - rect.top}px`);
        });
    };

    initThreeJS();
    initSpotlight();
});