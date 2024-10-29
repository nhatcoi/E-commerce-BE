
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
        minamount = $("#minamount"),
        maxamount = $("#maxamount"),
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
    minamount.val('$' + rangeSlider.slider("values", 0));
    maxamount.val('$' + rangeSlider.slider("values", 1));

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



    // Set percent discount
    $(document).ready(function() {
        $('.product__discount__item__pic').each(function() {
            const salePrice = parseFloat($(this).attr('data-sale-price'));
            const productPrice = parseFloat($(this).attr('data-product-price'));
            const discountPercent = ((1 - salePrice / productPrice) * 100).toFixed(1);
            $(this).find('.product__discount__percent').text(`-${discountPercent}%`);
        });
    });

    // set price and sale price
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

    // Countdown timer
    $(document).ready(function() {
        var endTimeText = $("#flash-sale-end-time").text();
        if (endTimeText) {
            var endTime = new Date(endTimeText).getTime();
            var countdownInterval = setInterval(function() {
                var now = new Date().getTime();
                var timeRemaining = endTime - now;

                if (timeRemaining <= 0) {
                    $('#flash-sale-count-end-time').text('Flash sale has ended');
                    clearInterval(countdownInterval);
                } else {
                    var days = Math.floor(timeRemaining / (1000 * 60 * 60 * 24));
                    var hours = Math.floor((timeRemaining % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                    var minutes = Math.floor((timeRemaining % (1000 * 60 * 60)) / (1000 * 60));
                    var seconds = Math.floor((timeRemaining % (1000 * 60)) / 1000);

                    $('#flash-sale-count-end-time').text(`Countdown: ${days}d ${hours}h ${minutes}m ${seconds}s`);
                }
            }, 1000);
        } else {
            $('#flash-sale-count-end-time').text('Flash sale has ended!');
        }
    });


    // Filter by price
    $(document).ready(function() {
        // Set default min and max price
        var min = 1;
        var max = 5000;

        $(".price-range").slider({
            range: true,
            min: min,
            max: max,
            values: [min, max],
            slide: function(event, ui) {
                $("#minamount").val(ui.values[0]);
                $("#maxamount").val(ui.values[1]);
            }
        });

        $("#minamount").val($(".price-range").slider("values", 0));
        $("#maxamount").val($(".price-range").slider("values", 1));

        // Filter and set products
        $("#filterButton").click(function() {
            $.ajax({
                url: "/shop-grid/filterByPrice",
                type: "GET",
                data: {
                    minamount: $("#minamount").val(),
                    maxamount: $("#maxamount").val()
                },
                success: function(data) {
                    $("#products-found").text(data.length);
                    $("#filterProducts").empty();

                    $.each(data, function(index, product) {
                        $("#filterProducts").append(`
                        <div class="col-lg-4 col-md-6 col-sm-6">
                            <div class="product__item">
                                <div class="product__item__pic set-bg" style="background-image: url('${product.thumbnail}');">
                                    <ul class="product__item__pic__hover">
                                        <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                        <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                                        <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                                    </ul>
                                </div>
                                <div class="product__item__text">
                                    <h6><a href="/products/${product.id}">${product.name}</a></h6>
                                    <h5>${product.price} $</h5>
                                </div>
                            </div>
                        </div>
                    `);
                    });
                },
                error: function() {
                    alert("Error");
                }
            });
        });
    });

    // delete nice select with
    $('#sortOptions').niceSelect('destroy');

    // Sort options products
    $('#sortOptions').change(function() {
        const sortBy = $(this).val();
        let products = $('#filterProducts .col-lg-4').toArray();

        products.sort(function(a, b) {
            // Get prices from the product elements
            const priceA = parseFloat($(a).find('.product__item__text h5').text().replace('$', '').trim());
            const priceB = parseFloat($(b).find('.product__item__text h5').text().replace('$', '').trim());

            if (sortBy === 'priceHighToLow') {
                return priceB - priceA; // Sort descending
            } else if (sortBy === 'priceLowToHigh') {
                return priceA - priceB; // Sort ascending
            }
            return 0; // No sorting
        });

        // Clear and re-append sorted products
        $('#filterProducts').empty().append(products);
    });


    // home : switch page
    $(document).ready(function () {
        loadPageContent(0);

        // Event listener for pagination buttons
        $(".pagination").on("click", ".page-link", function (e) {
            e.preventDefault();
            let page = $(this).data("page");
            let currentPage = parseInt($("#currentPage").val());

            if (page === "next") {
                page = currentPage + 1;
            } else if (page === "prev") {
                page = currentPage - 1;
            }

            // Call the function to load content for the selected page
            loadPageContent(page);
        });
    });

    function loadPageContent(page) {
        $.ajax({
            url: `/switch?page=${page}`,
            type: "GET",
            success: function (response) {
                $("#product-list").html(""); // Clear current products
                response.products.forEach(product => {
                    $("#product-list").append(`
                         <div class="col-lg-3 col-md-4 col-sm-6 mix">
                            <div class="product__item">
                                <div class="product__item__pic set-bg" style="background-image: url('${product.thumbnail}');">
                                    <ul class="product__item__pic__hover">
                                        <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                        <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                                        <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                                    </ul>
                                </div>
                                <div class="product__item__text">
                                    <h6><a href="/products/${product.id}">${product.name}</a></h6>
                                    <h5>${product.price} $</h5>
                                </div>
                            </div>
                        </div>
                `);
                });

                // Update current page and total pages
                $("#currentPage").val(page);
                updatePagination(page, response.totalPages);
            },
            error: function () {
                alert("Could not load products.");
            }
        });
    }

    // Function to dynamically update pagination links
    function updatePagination(currentPage, totalPages) {
        let paginationHTML = `
        <li id="prevPage" class="page-item ${currentPage === 0 ? 'disabled' : ''}">
            <a class="page-link" href="#" data-page="prev" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
    `;

        for (let i = 0; i < totalPages; i++) {
            paginationHTML += `
            <li class="page-item ${i === currentPage ? 'active' : ''}">
                <a class="page-link" href="#" data-page="${i}">${i + 1}</a>
            </li>
        `;
        }

        paginationHTML += `
        <li id="nextPage" class="page-item ${currentPage === totalPages - 1 ? 'disabled' : ''}">
            <a class="page-link" href="#" data-page="next" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    `;

        $(".pagination").html(paginationHTML);
    }

    // Event listener for pagination links
    $(".pagination").on("click", ".page-link", function (e) {
        e.preventDefault();
        let page = $(this).data("page");
        let currentPage = parseInt($("#currentPage").val());

        if (page === "next") {
            page = currentPage + 1;
        } else if (page === "prev") {
            page = currentPage - 1;
        } else {
            page = parseInt(page);
        }

        loadPageContent(page);
    });



})(jQuery);