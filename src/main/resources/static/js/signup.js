document.addEventListener('DOMContentLoaded', () => {
    // --- 1. USERNAME VALIDATION ---
    const usernameInput = document.getElementById('username');
    const usernameValidation = document.getElementById('username-validation');
    const userIcon = document.getElementById('userIcon');

    const uReqs = {
        start: document.getElementById('u-req-start'),
        chars: document.getElementById('u-req-chars'),
        space: document.getElementById('u-req-space'),
        length: document.getElementById('u-req-length')
    };

    if (usernameInput) {
        usernameInput.addEventListener('input', () => {
            const val = usernameInput.value;

            if (val.length > 0) {
                usernameValidation.style.display = 'block';
            } else {
                usernameValidation.style.display = 'none';
                userIcon.className = 'fa-solid fa-check match-icon';
                return;
            }

            let isValid = true;

            // Start with letter
            if (/^[a-zA-Z]/.test(val)) {
                uReqs.start.classList.add('valid');
            } else {
                uReqs.start.classList.remove('valid');
                isValid = false;
            }

            // Allowed chars
            if (/^[a-zA-Z0-9._-]+$/.test(val)) {
                uReqs.chars.classList.add('valid');
            } else {
                uReqs.chars.classList.remove('valid');
                isValid = false;
            }

            // No spaces
            if (!/\s/.test(val)) {
                uReqs.space.classList.add('valid');
            } else {
                uReqs.space.classList.remove('valid');
                isValid = false;
            }

            // Length 3-30
            if (val.length >= 3 && val.length <= 30) {
                uReqs.length.classList.add('valid');
            } else {
                uReqs.length.classList.remove('valid');
                isValid = false;
            }

            // Icon Update
            if (isValid) {
                userIcon.className = 'fa-solid fa-check match-icon valid';
            } else {
                userIcon.className = 'fa-solid fa-xmark match-icon invalid';
            }
        });
    }

    // --- 2. PASSWORD STRENGTH (FIXED SELECTORS) ---
    const passwordInput = document.getElementById('password');
    const confirmInput = document.getElementById('confirmPassword');
    const strengthFill = document.getElementById('strengthFill');
    const strengthText = document.getElementById('strengthText');
    const matchIcon = document.getElementById('matchIcon');

    // Fixed Selectors using ID
    const strengthContainer = document.getElementById('password-validation');
    const reqList = document.getElementById('password-req-list');

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

    if (passwordInput) {
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
    }

    if (confirmInput) {
        confirmInput.addEventListener('input', checkMatch);
    }

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

    // --- 3. PASSWORD TOGGLE LOGIC ---
    const togglePassBtn = document.getElementById('togglePassword');
    const toggleConfirmBtn = document.getElementById('toggleConfirm');

    function toggleVisibility(inputField, toggleIcon) {
        const type = inputField.getAttribute('type') === 'password' ? 'text' : 'password';
        inputField.setAttribute('type', type);

        toggleIcon.classList.toggle('fa-eye');
        toggleIcon.classList.toggle('fa-eye-slash');
    }

    if(togglePassBtn && passwordInput) {
        togglePassBtn.addEventListener('click', () => {
            toggleVisibility(passwordInput, togglePassBtn);
        });
    }

    if(toggleConfirmBtn && confirmInput) {
        toggleConfirmBtn.addEventListener('click', () => {
            toggleVisibility(confirmInput, toggleConfirmBtn);
        });
    }

    // --- 4. THEME LOGIC: THREE.JS PARTICLES ---
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

    // --- 5. THEME LOGIC: SPOTLIGHT ---
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