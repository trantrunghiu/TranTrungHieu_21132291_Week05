const slides = document.querySelector('.slides');
const slideCount = document.querySelectorAll('.slide').length;
const leftArrow = document.querySelector('.arrow.left');
const rightArrow = document.querySelector('.arrow.right');
let currentIndex = 0;

// Function to go to the next slide
function showSlide(index) {
    slides.style.transform = `translateX(-${index * 100}%)`;
}

// Automatic slide change
function autoSlide() {
    currentIndex = (currentIndex + 1) % slideCount; // Use modulo to create a circular effect
    showSlide(currentIndex);
}

// Event listeners for arrows
leftArrow.addEventListener('click', () => {
    currentIndex = (currentIndex - 1 + slideCount) % slideCount; // Handle circular array for left arrow
    showSlide(currentIndex);
});

rightArrow.addEventListener('click', () => {
    currentIndex = (currentIndex + 1) % slideCount; // Handle circular array for right arrow
    showSlide(currentIndex);
});

// Auto-slide every 5 seconds
setInterval(autoSlide, 5000);
