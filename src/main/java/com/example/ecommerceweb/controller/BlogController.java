package com.example.ecommerceweb.controller;

import com.example.ecommerceweb.configuration.Translator;
import com.example.ecommerceweb.dto.blog.BlogDTO;
import com.example.ecommerceweb.dto.response_data.Pagination;
import com.example.ecommerceweb.dto.response_data.ResponseData;
import com.example.ecommerceweb.filter.BlogFilter;
import com.example.ecommerceweb.service.BlogCategoryService;
import com.example.ecommerceweb.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogCategoryService blogCategoryService;
    private final BlogService blogService;
    private final Translator translator;

    @GetMapping("/categories")
    public ResponseData<List<?>> getCategories() {
        List<?> categories = blogCategoryService.getAllBlogCategories();
        return new ResponseData<>(HttpStatus.OK.value(), translator.toLocated("response success"), categories);
    }

    @GetMapping("/categories/count")
    public ResponseData<?> getCountBlogs() {
        return new ResponseData<>(HttpStatus.OK.value(), translator.toLocated("blogs.count.success"), blogCategoryService.getTopBlogCategories());
    }

    @GetMapping("/blogs")
    public ResponseData<?> getBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @ModelAttribute BlogFilter filter
    ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<BlogDTO> blogPage = blogService.getAllBlogs(pageable, filter);
        return new ResponseData<>(HttpStatus.OK.value(), translator.toLocated("blogs.get.success"), blogPage.getContent(), new Pagination(blogPage));
    }

    @GetMapping("/categories/{id}")
    public ResponseData<?> getBlogsByCategory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<BlogDTO> blogPage = blogService.getBlogByCategory(pageable, id);
        return new ResponseData<>(HttpStatus.OK.value(), translator.toLocated("blogs.category.get.success"), blogPage.getContent(), new Pagination(blogPage));
    }

    @GetMapping("/recent-news")
    public ResponseData<?> getRecentNews(@RequestParam(defaultValue = "3") int size) {
        List<BlogDTO> blogs = blogService.getRecentBlogs(size);
        return new ResponseData<>(HttpStatus.OK.value(), translator.toLocated("blogs.get.success"), blogs);
    }

}
