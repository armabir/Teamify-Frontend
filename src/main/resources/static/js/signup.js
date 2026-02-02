document.addEventListener('DOMContentLoaded', () => {
    // --- PRESERVED LOGIC ---
    const passwordInput = document.getElementById('password');
    const confirmInput = document.getElementById('confirmPassword');
    const strengthFill = document.getElementById('strengthFill');
    const strengthText = document.getElementById('strengthText');
    const strengthContainer = document.querySelector('.password-strength-container');
    const reqList = document.querySelector('.strength-requirements');
    const matchIcon = document.getElementById('matchIcon');

    const reqs = {
        length: document.getElementById('req-length'),
        upper: document.getElementById('req-upper'),
        lower: document.getElementById('req-lower'),
        num: document.getElementById('req-num'),
        special: document.getElementById('req-special')
    };

    const patterns = {
        length: /.{8,}/,
        upper: /[A-Z]/,
        lower: /[a-z]/,
        num: /[0-9]/,
        special: /[^A-Za-z0-9]/
    };

    passwordInput.addEventListener('input', () => {
        const val = passwordInput.value;
        if(val.length > 0) {
            strengthContainer.classList.add('active');
            reqList.classList.add('active');
        } else {
            strengthContainer.classList.remove('active');
            reqList.classList.remove('active');
        }

        let score = 0;
        if(patterns.length.test(val)) { reqs.length.classList.add('valid'); score++; } else reqs.length.classList.remove('valid');
        if(patterns.upper.test(val)) { reqs.upper.classList.add('valid'); score++; } else reqs.upper.classList.remove('valid');
        if(patterns.lower.test(val)) { reqs.lower.classList.add('valid'); score++; } else reqs.lower.classList.remove('valid');
        if(patterns.num.test(val)) { reqs.num.classList.add('valid'); score++; } else reqs.num.classList.remove('valid');
        if(patterns.special.test(val)) { reqs.special.classList.add('valid'); score++; } else reqs.special.classList.remove('valid');

        const width = (score / 5) * 100;
        strengthFill.style.width = `${width}%`;

        if(score <= 2) {
            strengthFill.style.backgroundColor = 'var(--danger)';
            strengthText.innerText = 'Weak';
        } else if (score <= 4) {
            strengthFill.style.backgroundColor = 'var(--warning)';
            strengthText.innerText = 'Medium';
        } else {
            strengthFill.style.backgroundColor = 'var(--accent-cyan)';
            strengthText.innerText = 'Strong';
        }
        checkMatch();
    });

    confirmInput.addEventListener('input', checkMatch);

    function checkMatch() {
        const p1 = passwordInput.value;
        const p2 = confirmInput.value;
        if (p2.length === 0) { matchIcon.className = 'fa-solid fa-check match-icon'; return; }
        if (p1 === p2 && p1 !== "") {
            matchIcon.className = 'fa-solid fa-check match-icon valid';
        } else {
            matchIcon.className = 'fa-solid fa-xmark match-icon invalid';
        }
    }

    // --- THEME LOGIC: THREE.JS PARTICLES (Copied from Landing) ---
    const initThreeJS = () => {
        const canvas = document.getElementById('webgl-canvas');
        if (!canvas) return;
        const scene = new THREE.Scene();
        const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
        const renderer = new THREE.WebGLRenderer({ canvas: canvas, alpha: true, antialias: true });
        renderer.setSize(window.innerWidth, window.innerHeight);

        const geometry = new THREE.BufferGeometry();
        const particlesCount = 2000;
        const posArray = new Float32Array(particlesCount * 3);
        for(let i = 0; i < particlesCount * 3; i++) { posArray[i] = (Math.random() - 0.5) * 20; }
        geometry.setAttribute('position', new THREE.BufferAttribute(posArray, 3));
        const material = new THREE.PointsMaterial({ size: 0.02, color: 0x22d3ee, transparent: true, opacity: 0.8 });
        const particlesMesh = new THREE.Points(geometry, material);
        scene.add(particlesMesh);

        camera.position.z = 5;
        const tick = () => {
            particlesMesh.rotation.y += 0.001;
            renderer.render(scene, camera);
            window.requestAnimationFrame(tick);
        }
        tick();
    };

    // --- THEME LOGIC: SPOTLIGHT (Copied from Landing) ---
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