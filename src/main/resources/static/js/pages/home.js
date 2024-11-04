'use restrict';

(function ($) {
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
            url: `/switch-page?page=${page}`, // call api switch page
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






}(jQuery));