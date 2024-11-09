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
        // Load categories
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

                // Add click event for categories
                $(".category-link").click(function(e) {
                    e.preventDefault();
                    let categoryId = $(this).data("category-id");
                    loadBlogsByCategory(0, categoryId);  // Load blogs for the selected category
                });
            },
            error: function () {
                alert("Could not load blog categories.");
            }
        });

        loadBlogsByCategory(0, 0);

        function loadBlogsByCategory(page, categoryId) {
            $.ajax({
                url: `/blog/category/${categoryId}`,
                method: "GET",
                data: { page: page, size: 6 },
                success: function(data) {
                    let blogContainer = $('#blog-container');
                    blogContainer.empty();

                    data.content.forEach(blog => {
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

                    // Pagination
                    let pagination = $('.product__pagination.blog__pagination');
                    pagination.empty(); // Clear current pagination

                    for (let i = 0; i < data.totalPages; i++) {
                        let activeClass = i === data.number ? 'active' : '';
                        pagination.append(`
                        <a href="#" class="pagination-link ${activeClass}" data-page="${i}" data-category-id="${categoryId}">${i + 1}</a>
                    `);
                    }

                    // Click page
                    $('.pagination-link').click(function(e) {
                        e.preventDefault();
                        let page = $(this).data('page');
                        let categoryId = $(this).data('category-id');
                        loadBlogsByCategory(page, categoryId);  // Pass categoryId to keep the category filter
                    });
                },
                error: function(error) {
                    console.log("Error fetching blog data:", error);
                }
            });
        }
    });


    // blogs
    $(document).ready(function() {
        loadBlogs(0);

        function loadBlogs(page) {
            $.ajax({
                url: "/blog/blogs",
                method: "GET",
                data: { page: page, size: 6 },
                success: function(data) {
                    let blogContainer = $('#blog-container');
                    blogContainer.empty();

                    data.content.forEach(blog => {
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

                    // Pagination
                    let pagination = $('.product__pagination.blog__pagination');
                    pagination.empty(); // Clear current pagination

                    for (let i = 0; i < data.totalPages; i++) {
                        let activeClass = i === data.number ? 'active' : '';
                        pagination.append(`
                        <a href="#" class="${activeClass}" data-page="${i}">${i + 1}</a>
                    `);
                    }

                    // Click page
                    $('.product__pagination.blog__pagination a').click(function(e) {
                        e.preventDefault();
                        let page = $(this).data('page');
                        loadBlogs(page);
                    });
                },
                error: function(error) {
                    console.log("Error fetching blog data:", error);
                }
            });
        }
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


    // category id






} (jQuery));