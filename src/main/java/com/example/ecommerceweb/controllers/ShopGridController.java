package com.example.ecommerceweb.controllers;

import com.example.ecommerceweb.entities.Category;
import com.example.ecommerceweb.entities.FlashSaleItem;
import com.example.ecommerceweb.entities.FlashSale;
import com.example.ecommerceweb.services.CategoryService;
import com.example.ecommerceweb.services.FlashSaleItemService;
import com.example.ecommerceweb.services.FlashSaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/shop-grid")
@RequiredArgsConstructor
public class ShopGridController {
    private final CategoryService categoryService;
    private final FlashSaleItemService flashSaleItemService;
    private final FlashSaleService flashSaleService;

    @GetMapping("")
    public String shopGrid(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        FlashSale currentFlashSale = flashSaleService.getCurrentFlashSale(); // Lấy Flash Sale hiện tại
        List<FlashSaleItem> flashSaleItems = flashSaleItemService.getProductsInFlashSale(currentFlashSale.getId());

        model.addAttribute("categories", categories);
        model.addAttribute("flashSaleItems", flashSaleItems);
        model.addAttribute("flashSale", currentFlashSale);
        return "shop-grid";
    }
}
