package com.example.ecommerceweb.controllers;

import com.example.ecommerceweb.entities.Category;
import com.example.ecommerceweb.entities.Product;
import com.example.ecommerceweb.services.CategoryService;
import com.example.ecommerceweb.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ecommerceweb.utils.DivideList.divideList;
import static com.example.ecommerceweb.utils.Static.*;

@Slf4j
@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class HomeController {
    private final CategoryService categoryService;
    private final ProductService productService;


    @GetMapping("")
    public String home(Model model) throws IOException, InterruptedException {
        List<Category> categories = categoryService.getAllCategories();
        List<List<Product>> latestProducts = divideList(
                productService.getLatestProducts(LATEST_LIMIT),
                PAGE_SLIDE
        );
        List<List<Product>> topRatedProducts = divideList(
                productService.getTopRatedProducts(TOP_RATING_LIMIT),
                PAGE_SLIDE
        );

        model.addAttribute("categories", categories);
        model.addAttribute("latestProducts", latestProducts);
        model.addAttribute("topRatedProducts", topRatedProducts);
        return "index";
    }

    @GetMapping("/switch")
    @ResponseBody
    public Map<String, Object> getProducts(@RequestParam(defaultValue = "0") int page) {
        Page<Product> productPage = productService.getProductsByPage(page, PAGINATION_LIMIT);
        Map<String, Object> response = new HashMap<>();
        response.put("products", productPage.getContent());
        response.put("currentPage", page);
        response.put("totalPages", productPage.getTotalPages());
        return response;
    }


}

