package com.example.ecommerceweb.controllers;

import com.example.ecommerceweb.entities.Category;
import com.example.ecommerceweb.entities.Product;
import com.example.ecommerceweb.services.CategoryService;
import com.example.ecommerceweb.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.util.List;
import static com.example.ecommerceweb.utils.DivideList.divideList;
import static com.example.ecommerceweb.utils.Static.*;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class HomeController {
    private final CategoryService categoryService;
    private final ProductService productService;


    @GetMapping("")
    public String home(@RequestParam(defaultValue = "0") int page, Model model) throws IOException, InterruptedException {
        List<Category> categories = categoryService.getAllCategories();
        Page<Product> productPage = productService.getProductsByPage(page, PAGINATION_LIMIT);

        List<List<Product>> latestProducts = divideList(
                productService.getLatestProducts(LATEST_LIMIT),
                PAGE_SLIDE
        );
        List<List<Product>> topRatedProducts = divideList(
                productService.getTopRatedProducts(TOP_RATING_LIMIT),
                PAGE_SLIDE
        );

        model.addAttribute("categories", categories);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("latestProducts", latestProducts);
        model.addAttribute("topRatedProducts", topRatedProducts);

        return "index";
    }

}

