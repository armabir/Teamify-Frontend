document.addEventListener('DOMContentLoaded', () => {
    const passwordInput = document.getElementById('password');
    const confirmInput = document.getElementById('confirmPassword');
    const strengthFill = document.getElementById('strengthFill');
    const strengthText = document.getElementById('strengthText');
    const strengthContainer = document.querySelector('.password-strength-container');
    const reqList = document.querySelector('.strength-requirements');
    const matchIcon = document.getElementById('matchIcon');

    // Requirements Elements
    const reqs = {
        length: document.getElementById('req-length'),
        upper: document.getElementById('req-upper'),
        lower: document.getElementById('req-lower'),
        num: document.getElementById('req-num'),
        special: document.getElementById('req-special')
    };

    // Regex Patterns
    const patterns = {
        length: /.{8,}/,
        upper: /[A-Z]/,
        lower: /[a-z]/,
        num: /[0-9]/,
        special: /[^A-Za-z0-9]/
    };

    // 1. Password Strength Logic
    passwordInput.addEventListener('input', () => {
        const val = passwordInput.value;

        // Show/Hide UI based on input
        if(val.length > 0) {
            strengthContainer.classList.add('active');
            reqList.classList.add('active');
        } else {
            strengthContainer.classList.remove('active');
            reqList.classList.remove('active');
        }

        let score = 0;

        // Check Validity and Style List Items
        // Length
        if(patterns.length.test(val)) { reqs.length.classList.add('valid'); score++; }
        else reqs.length.classList.remove('valid');

        // Uppercase
        if(patterns.upper.test(val)) { reqs.upper.classList.add('valid'); score++; }
        else reqs.upper.classList.remove('valid');

        // Lowercase
        if(patterns.lower.test(val)) { reqs.lower.classList.add('valid'); score++; }
        else reqs.lower.classList.remove('valid');

        // Number
        if(patterns.num.test(val)) { reqs.num.classList.add('valid'); score++; }
        else reqs.num.classList.remove('valid');

        // Special
        if(patterns.special.test(val)) { reqs.special.classList.add('valid'); score++; }
        else reqs.special.classList.remove('valid');

        // Update Progress Bar UI
        const width = (score / 5) * 100;
        strengthFill.style.width = `${width}%`;

        // Color & Text Mapping
        if(score <= 2) {
            strengthFill.style.backgroundColor = 'var(--danger)';
            strengthText.innerText = 'Weak';
            strengthText.style.color = 'var(--danger)';
        } else if (score <= 4) {
            strengthFill.style.backgroundColor = 'var(--warning)';
            strengthText.innerText = 'Medium';
            strengthText.style.color = 'var(--warning)';
        } else {
            strengthFill.style.backgroundColor = 'var(--success)';
            strengthText.innerText = 'Strong';
            strengthText.style.color = 'var(--success)';
        }

        checkMatch(); // Re-check match if main password changes
    });


        // Automatically fade out toasts after 5 seconds
        setTimeout(() => {
        const toasts = document.querySelectorAll('.toast');
        toasts.forEach(toast => {
        toast.style.opacity = '0';
        setTimeout(() => toast.remove(), 500); // Remove from DOM after fade
    });
    }, 5000);


    // 2. Password Match Logic
    confirmInput.addEventListener('input', checkMatch);

    function checkMatch() {
        const p1 = passwordInput.value;
        const p2 = confirmInput.value;

        if (p2.length === 0) {
            matchIcon.className = 'fa-solid fa-check match-icon'; // Reset
            return;
        }

        if (p1 === p2 && p1 !== "") {
            matchIcon.classList.remove('fa-xmark', 'invalid');
            matchIcon.classList.add('fa-check', 'valid');
        } else {
            matchIcon.classList.remove('fa-check', 'valid');
            matchIcon.classList.add('fa-xmark', 'invalid');
        }
    }
});