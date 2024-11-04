package com.example.ecommerceweb.controllers;

import com.example.ecommerceweb.dtos.CategoryDTO;
import com.example.ecommerceweb.dtos.ProductDTO;
import com.example.ecommerceweb.entities.Category;
import com.example.ecommerceweb.entities.Product;
import com.example.ecommerceweb.services.CategoryService;
import com.example.ecommerceweb.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
import java.util.stream.Collectors;

import static com.example.ecommerceweb.utils.DivideList.divideList;
import static com.example.ecommerceweb.utils.Static.*;

@Slf4j
@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class HomeController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public String home(Model model) throws IOException, InterruptedException {
        List<CategoryDTO> categories = categoryService.getAllCategories().stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());

        List<List<ProductDTO>> latestProducts = divideList(
                productService.getLatestProducts(LATEST_LIMIT).stream()
                        .map(product -> modelMapper.map(product, ProductDTO.class))
                        .collect(Collectors.toList()),
                PAGE_SLIDE
        );
        List<List<ProductDTO>> topRatedProducts = divideList(
                productService.getTopRatedProducts(TOP_RATING_LIMIT).stream()
                        .map(product -> modelMapper.map(product, ProductDTO.class))
                        .collect(Collectors.toList()),
                PAGE_SLIDE
        );

        model.addAttribute("categories", categories);
        model.addAttribute("latestProducts", latestProducts);
        model.addAttribute("topRatedProducts", topRatedProducts);
        return "index";
    }

    @GetMapping("/switch-page")
    @ResponseBody
    public Map<String, Object> getProducts(@RequestParam int page) {
        Page<Product> productPage = productService.getProductsByPage(page, PAGINATION_LIMIT);
        Map<String, Object> response = new HashMap<>();
        response.put("products", productPage.getContent());
        response.put("currentPage", page);
        response.put("totalPages", productPage.getTotalPages());
        return response;
    }


}

