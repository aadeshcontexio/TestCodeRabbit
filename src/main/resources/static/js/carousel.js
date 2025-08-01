/* <!-- ***  UI/CSS/JS Author Neville Doke  *** --> */
/* Carousel Section Start */
/* document.addEventListener("DOMContentLoaded", function () {
    // First Swiper instance
    const swiper = new Swiper(".swiper", {
        slidesPerView: 3, // Number of visible slides
        spaceBetween: 20, // Space between slides
        loop: true, // Enable looping

        // Navigation arrows
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },

        // Pagination
        pagination: {
            el: ".swiper-pagination",
            clickable: true,
        },

        // Responsive breakpoints
        breakpoints: {
            768: {
                slidesPerView: 3,
                spaceBetween: 10,
            },
            480: {
                slidesPerView: 1,
                spaceBetween: 10,
            },
        },
    });

    // Second Swiper instance (Thumbnails)
    var swiperThumbs = new Swiper(".mySwiper2", {
        spaceBetween: 10,
        slidesPerView: 4,
        freeMode: true,
        watchSlidesProgress: true,
    });

    // Third Swiper instance (Main)
    var swiperMain = new Swiper(".mySwiper", {
        spaceBetween: 10,
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },
        thumbs: {
            swiper: swiperThumbs,
        },
    });
}); */
document.addEventListener("DOMContentLoaded", function () {
    const swiperEl = document.querySelector(".swiper");
    const slideCount = swiperEl.querySelectorAll(".swiper-slide").length;

    // Enable loop only if there are enough slides
    const enableLoop = slideCount > 3; // Adjust based on slidesPerView

    // First Swiper instance
    const swiper = new Swiper(".swiper", {
        slidesPerView: 3,
        spaceBetween: 20,
        loop: enableLoop, // Enable loop only if enough slides exist
        loopAdditionalSlides: 3, // Helps with looping
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },
        pagination: {
            el: ".swiper-pagination",
            clickable: true,
        },
        breakpoints: {
            768: {
                slidesPerView: 3,
                spaceBetween: 10,
            },
            480: {
                slidesPerView: 1,
                spaceBetween: 10,
            },
        },
    });

    // Second Swiper instance (Thumbnails)
    var swiperThumbs = new Swiper(".mySwiper2", {
        spaceBetween: 10,
        slidesPerView: 4,
        freeMode: true,
        watchSlidesProgress: true,
    });

    // Third Swiper instance (Main)
    var swiperMain = new Swiper(".mySwiper", {
        spaceBetween: 10,
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },
        thumbs: {
            swiper: swiperThumbs,
        },
    });
});

	
/* Carousel Section End */	/* Carousel Section Start */
/* document.addEventListener("DOMContentLoaded", function () {
    // First Swiper instance
    const swiper = new Swiper(".swiper", {
        slidesPerView: 3, // Number of visible slides
        spaceBetween: 20, // Space between slides
        loop: true, // Enable looping

        // Navigation arrows
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },

        // Pagination
        pagination: {
            el: ".swiper-pagination",
            clickable: true,
        },

        // Responsive breakpoints
        breakpoints: {
            768: {
                slidesPerView: 3,
                spaceBetween: 10,
            },
            480: {
                slidesPerView: 1,
                spaceBetween: 10,
            },
        },
    });

    // Second Swiper instance (Thumbnails)
    var swiperThumbs = new Swiper(".mySwiper2", {
        spaceBetween: 10,
        slidesPerView: 4,
        freeMode: true,
        watchSlidesProgress: true,
    });

    // Third Swiper instance (Main)
    var swiperMain = new Swiper(".mySwiper", {
        spaceBetween: 10,
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },
        thumbs: {
            swiper: swiperThumbs,
        },
    });
}); */
document.addEventListener("DOMContentLoaded", function () {
    const swiperEl = document.querySelector(".swiper");
    const slideCount = swiperEl.querySelectorAll(".swiper-slide").length;

    // Enable loop only if there are enough slides
    const enableLoop = slideCount > 3; // Adjust based on slidesPerView

    // First Swiper instance
    const swiper = new Swiper(".swiper", {
        slidesPerView: 3,
        spaceBetween: 20,
        loop: enableLoop, // Enable loop only if enough slides exist
        loopAdditionalSlides: 3, // Helps with looping
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },
        pagination: {
            el: ".swiper-pagination",
            clickable: true,
        },
        breakpoints: {
            768: {
                slidesPerView: 3,
                spaceBetween: 10,
            },
            480: {
                slidesPerView: 1,
                spaceBetween: 10,
            },
        },
    });

    // Second Swiper instance (Thumbnails)
    var swiperThumbs = new Swiper(".mySwiper2", {
        spaceBetween: 10,
        slidesPerView: 4,
        freeMode: true,
        watchSlidesProgress: true,
    });

    // Third Swiper instance (Main)
    var swiperMain = new Swiper(".mySwiper", {
        spaceBetween: 10,
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },
        thumbs: {
            swiper: swiperThumbs,
        },
    });
});

	
/* Carousel Section End */	