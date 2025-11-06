 $(document).ready(function() {
    // Smooth scrolling para los enlaces de navegación
    $('a[href*="#"]:not([href="#"])').click(function() {
        if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
            var target = $(this.hash);
            target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
            if (target.length) {
                var navHeight = $('.navbar-fixed-top').outerHeight();
                $('html, body').animate({
                    scrollTop: target.offset().top - navHeight
                }, 1000);
                return false;
            }
        }
    });

    // Colapsar navbar en móvil al hacer clic en enlaces
    $('.navbar-nav>li>a').on('click', function() {
    $('.navbar-collapse').collapse('hide');
});

    // Animación de aparición al hacer scroll
    function checkFadeIn() {
    $('.fade-in-element').each(function() {
    var elementTop = $(this).offset().top;
    var elementBottom = elementTop + $(this).outerHeight();
    var viewportTop = $(window).scrollTop();
    var viewportBottom = viewportTop + $(window).height();

    if (elementBottom > viewportTop && elementTop < viewportBottom - 100) {
    $(this).addClass('visible');
}
});
}

    // Ejecutar al cargar la página y al hacer scroll
    checkFadeIn();
    $(window).scroll(function() {
    checkFadeIn();

    // Cambiar estilo del navbar al hacer scroll
    if ($(window).scrollTop() > 100) {
    $('.navbar-custom').css({
    'background': 'rgba(255, 255, 255, 0.98)',
    'box-shadow': '0 2px 30px rgba(0, 0, 0, 0.15)'
});
} else {
    $('.navbar-custom').css({
    'background': 'rgba(255, 255, 255, 0.95)',
    'box-shadow': '0 2px 20px rgba(0, 0, 0, 0.1)'
});
}
});
});