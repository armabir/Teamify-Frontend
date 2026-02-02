document.addEventListener('DOMContentLoaded', () => {
    // --- File Upload Logic ---
    const dropZone = document.getElementById('dropZone');
    const fileInput = document.getElementById('logoFile');
    const fileNameSpan = document.getElementById('fileName');
    const previewImg = document.getElementById('logoPreview');
    const uploadContent = document.querySelector('.upload-content');

    // 1. Click to trigger input
    dropZone.addEventListener('click', () => fileInput.click());

    // 2. Drag & Drop Visuals
    dropZone.addEventListener('dragover', (e) => {
        e.preventDefault();
        dropZone.classList.add('dragover');
    });

    dropZone.addEventListener('dragleave', () => {
        dropZone.classList.remove('dragover');
    });

    dropZone.addEventListener('drop', (e) => {
        e.preventDefault();
        dropZone.classList.remove('dragover');

        if (e.dataTransfer.files.length) {
            fileInput.files = e.dataTransfer.files;
            updatePreview(fileInput.files[0]);
        }
    });

    // 3. Handle File Selection
    fileInput.addEventListener('change', () => {
        if (fileInput.files.length) {
            updatePreview(fileInput.files[0]);
        }
    });

    function updatePreview(file) {
        if (file.type.startsWith('image/')) {
            const reader = new FileReader();

            reader.onload = (e) => {
                previewImg.src = e.target.result;
                previewImg.classList.remove('hidden');
                previewImg.classList.add('loaded');

                // Optional: Hide icon text or change it
                // uploadContent.style.opacity = '0'; 
                fileNameSpan.innerText = file.name; // Show name
                fileNameSpan.style.color = "white";
                fileNameSpan.style.textShadow = "0 2px 4px rgba(0,0,0,0.8)";
                uploadContent.style.zIndex = "3"; // Keep text above image
            };

            reader.readAsDataURL(file);
        } else {
            fileNameSpan.innerText = "Invalid file type. Please upload an image.";
            fileNameSpan.style.color = "var(--danger)";
        }
    }
});