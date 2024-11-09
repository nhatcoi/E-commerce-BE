'use restrict';

(function ($) {

    $(document).ready(function () {
        $.ajax({
            url: `categories`,
            type: "GET",
            success: function (response) {
                response.forEach(category => {
                    $("#categories").append(`
                    <li><a href="categories/${category.id}">${category.name}</a></li>
                `);
                });
            },
            error: function () {
                alert("Could not load categories.");
            }
        });
    });


    // call api blog categories
    $(document).ready(function () {
        loadCategories();
        loadBlogs(`/blog/blogs`, 0); // Initial load of all blogs

        function loadCategories() {
            $.ajax({
                url: `blog/categories`,
                type: "GET",
                success: function (response) {
                    response.forEach(category => {
                        $("#blog-categories").append(`
                        <li><a href="#" class="category-link" data-category-id="${category.id}">${category.name}</a></li>
                    `);
                        $("#search-by").append(`
                        <a href="#" class="category-link" data-category-id="${category.id}">${category.name}</a>
                    `);
                    });

                    // Category click event to load blogs by category
                    $(".category-link").click(function(e) {
                        e.preventDefault();
                        let categoryId = $(this).data("category-id");
                        loadBlogs(`/blog/category/${categoryId}`, 0);  // Load blogs by selected category
                    });
                },
                error: function () {
                    alert("Could not load blog categories.");
                }
            });
        }

        function loadBlogs(url, page) {
            $.ajax({
                url: url,
                method: "GET",
                data: { page: page, size: 4 },
                success: function(data) {
                    renderBlogs(data.content);
                    renderPagination(data.totalPages, data.number, url); // Pass the URL for category-specific or all blogs
                },
                error: function(error) {
                    console.log("Error fetching blog data:", error);
                }
            });
        }

        function renderBlogs(blogs) {
            let blogContainer = $('#blog-container');
            blogContainer.empty();

            blogs.forEach(blog => {
                let date = new Date(blog.createdAt);
                let options = { year: 'numeric', month: 'long', day: 'numeric' };
                let formattedDate = date.toLocaleDateString('en-US', options);

                let blogItem = `
                <div class="col-lg-6 col-md-6 col-sm-6">
                    <div class="blog__item">
                        <div class="blog__item__pic">
                            <img src="${blog.thumbnail}" alt="image blog">
                        </div>
                        <div class="blog__item__text">
                            <ul>
                                <li><i class="fa fa-calendar-o"></i> ${formattedDate}</li>
                            </ul>
                            <h5><a href="/blog/${blog.id}">${blog.title}</a></h5>
                            <p>${blog.content.substring(0, 80)}...</p>
                            <a href="/blog/${blog.id}" class="blog__btn">READ MORE <span class="arrow_right"></span></a>
                        </div>
                    </div>
                </div>
            `;
                blogContainer.append(blogItem);
            });
        }

        function renderPagination(totalPages, currentPage, url) {
            let pagination = $('.product__pagination.blog__pagination');
            pagination.empty(); // Clear current pagination

            for (let i = 0; i < totalPages; i++) {
                let activeClass = i === currentPage ? 'active' : '';
                pagination.append(`
                <a href="#" class="pagination-link ${activeClass}" data-page="${i}" data-url="${url}">${i + 1}</a>
            `);
            }
        }

        // Event delegation for pagination
        $(document).on('click', '.pagination-link', function(e) {
            e.preventDefault();
            let page = $(this).data('page');
            let url = $(this).data('url');
            loadBlogs(url, page);  // Load blogs for the selected page
        });
    });



    // recent blogs
    $(document).ready(function() {

        $.ajax({
            url: "/blog/recent-news",
            method: "GET",
            success: function(data) {
                let recentBlogContainer = $('#recent-news');
                recentBlogContainer.empty();

                data.forEach(blog => {
                    let date = new Date(blog.createdAt);
                    let options = { year: 'numeric', month: 'long', day: 'numeric' };
                    let formattedDate = date.toLocaleDateString('en-US', options);
                    let recentBlogItem = `
                        <a href="/blog/${blog.id}" class="blog__sidebar__recent__item">
                            <div class="blog__sidebar__recent__item__pic">
                                
                            </div>
                            <div class="blog__sidebar__recent__item__text">
                                <h6>${blog.title}</h6>
                                <span>${formattedDate}</span>
                            </div>
                        </a>
                    `;
                    recentBlogContainer.append(recentBlogItem);
                });
            },
            error: function(error) {
                console.log("Error fetching recent blog data:", error);
            }
        });
    });


} (jQuery));