
'use strict';

(function ($) {

    // Preloader
    $(window).on('load', function () {
        $(".loader").fadeOut();
        $("#preloaded").delay(200).fadeOut("slow");

        // List Filter
        $('.featured__controls li').on('click', function () {
            $('.featured__controls li').removeClass('active');
            $(this).addClass('active');
        });
        if ($('.featured__filter').length > 0) {
            var containerEl = document.querySelector('.featured__filter');
            var mixer = mixitup(containerEl);
        }
    });

    // Set Background Image
    $('.set-bg').each(function () {
        var bg = $(this).data('setbg');
        $(this).css('background-image', 'url(' + bg + ')');
    });

    // Menu
    $(".open").on('click', function () {
        $(".menu__wrapper").addClass("show__menu__wrapper");
        $(".menu__overlay").addClass("active");
        $("body").addClass("over_hid");
    });

    $(".menu__overlay").on('click', function () {
        $(".menu__wrapper").removeClass("show__menu__wrapper");
        $(".menu__overlay").removeClass("active");
        $("body").removeClass("over_hid");
    });

    // Navbar
    $(".mobile-menu").slicknav({
        prependTo: '#mobile-menu-wrap',
        allowParentLinks: true
    });

    // Categories Slider
    $(".categories__slider").owlCarousel({
        loop: true,
        margin: 0,
        items: 4,
        dots: false,
        nav: true,
        navText: ["<span class='fa fa-angle-left'><span/>", "<span class='fa fa-angle-right'><span/>"],
        animateOut: 'fadeOut',
        animateIn: 'fadeIn',
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true,
        responsive: {

            0: {
                items: 1,
            },

            480: {
                items: 2,
            },

            768: {
                items: 3,
            },

            992: {
                items: 4,
            }
        }
    });


    $('.hero__categories__all').on('click', function () {
        $('.hero__categories ul').slideToggle(400);
    });

    // Special Product Slider
    $(".special-product__slider").owlCarousel({
        loop: true,
        margin: 0,
        items: 1,
        dots: false,
        nav: true,
        navText: ["<span class='fa fa-angle-left'><span/>", "<span class='fa fa-angle-right'><span/>"],
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true
    });

    // Flash Sale Slider
    $(".product__discount__slider").owlCarousel({
        loop: true,
        margin: 0,
        items: 3,
        dots: true,
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true,
        responsive: {

            320: {
                items: 1,
            },

            480: {
                items: 2,
            },

            768: {
                items: 2,
            },

            992: {
                items: 3,
            }
        }
    });

    // Flash Sale Img Slider
    $(".product__details__pic__slider").owlCarousel({
        loop: true,
        margin: 20,
        items: 4,
        dots: true,
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true
    });

    // Flash Sale Price Slider
    var rangeSlider = $(".price-range"),
        minAmount = $("#min-amount"),
        maxAmount = $("#max-amount"),
        minPrice = rangeSlider.data('min'),
        maxPrice = rangeSlider.data('max');
    rangeSlider.slider({
        range: true,
        min: minPrice,
        max: maxPrice,
        values: [minPrice, maxPrice],
        slide: function (event, ui) {
            minamount.val('$' + ui.values[0]);
            maxamount.val('$' + ui.values[1]);
        }
    });
    minAmount.val('$' + rangeSlider.slider("values", 0));
    maxAmount.val('$' + rangeSlider.slider("values", 1));

    // nice select
    $("select").niceSelect();

    /*------------------
		Single Product
	--------------------*/
    $('.product__details__pic__slider img').on('click', function () {

        var imgurl = $(this).data('imgbigurl');
        var bigImg = $('.product__details__pic__item--large').attr('src');
        if (imgurl != bigImg) {
            $('.product__details__pic__item--large').attr({
                src: imgurl
            });
        }
    });

    /*-------------------
		Quantity change
	--------------------- */
    var proQty = $('.pro-qty');
    proQty.prepend('<span class="dec qtybtn">-</span>');
    proQty.append('<span class="inc qtybtn">+</span>');
    proQty.on('click', '.qtybtn', function () {
        var $button = $(this);
        var oldValue = $button.parent().find('input').val();
        if ($button.hasClass('inc')) {
            var newVal = parseFloat(oldValue) + 1;
        } else {
            // Don't allow decrementing below zero
            if (oldValue > 0) {
                var newVal = parseFloat(oldValue) - 1;
            } else {
                newVal = 0;
            }
        }
        $button.parent().find('input').val(newVal);
    });



    $(document).ready(function() {
        $('.product__discount__item__pic').each(function() {
            const salePrice = parseFloat($(this).attr('data-sale-price'));
            const productPrice = parseFloat($(this).attr('data-product-price'));
            const discountPercent = ((1 - salePrice / productPrice) * 100).toFixed(1);
            $(this).find('.product__discount__percent').text(`-${discountPercent}%`);
        });
    });

    $(document).ready(function() {
        $('.product__item__price').each(function() {
            var originalPrice = parseFloat($(this).find('.original-price').data('original-price'));
            var salePrice = parseFloat($(this).find('.sale-price').data('sale-price'));

            // Định dạng giá gốc
            $(this).find('.original-price').text(originalPrice.toFixed(2) + ' $');

            // Định dạng giá sale
            $(this).find('.sale-price').text(salePrice.toFixed(2) + ' $');
        });
    });

    $(document).ready(function() {
        var endTime = /*[[${flashSale.endTime}]]*/ '2023-12-31T23:59:59';
        var formattedEndTime = new Date(endTime).toLocaleString('vi-VN', { hour12: false });
        $('#flash-sale-end-time').text('Ends at: ' + formattedEndTime);
    });

})(jQuery);