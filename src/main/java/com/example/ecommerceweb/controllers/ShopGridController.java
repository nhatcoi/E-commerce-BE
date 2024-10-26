package com.example.ecommerceweb.controllers;

import com.example.ecommerceweb.entities.Category;
import com.example.ecommerceweb.entities.FlashSaleItem;
import com.example.ecommerceweb.entities.FlashSale;
import com.example.ecommerceweb.entities.Product;
import com.example.ecommerceweb.services.CategoryService;
import com.example.ecommerceweb.services.FlashSaleItemService;
import com.example.ecommerceweb.services.FlashSaleService;
import com.example.ecommerceweb.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import static com.example.ecommerceweb.utils.Static.*;
import static com.example.ecommerceweb.utils.DivideList.divideList;

@Slf4j
@Controller
@RequestMapping("/shop-grid")
@RequiredArgsConstructor
public class ShopGridController {
    private final CategoryService categoryService;
    private final FlashSaleItemService flashSaleItemService;
    private final FlashSaleService flashSaleService;
    private final ProductService productService;

    @GetMapping("")
    public String shopGrid(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        FlashSale currentFlashSale = flashSaleService.getCurrentFlashSale();
        List<FlashSaleItem> flashSaleItems = flashSaleItemService.getProductsInFlashSale(currentFlashSale.getId());

        List<List<Product>> latestProducts = divideList(
                productService.getLatestProducts(LATEST_LIMIT),
                PAGE_SLIDE
        );

        model.addAttribute("categories", categories);
        model.addAttribute("flashSaleItems", flashSaleItems);
        model.addAttribute("flashSale", currentFlashSale);
        model.addAttribute("latestProducts", latestProducts);
        return "shop-grid";
    }


    @GetMapping("/filterByPrice")
    @ResponseBody
    public List<Product> filterProducts(
            @RequestParam(value = "minamount", required = false) String minAmount,
            @RequestParam(value = "maxamount", required = false) String maxAmount) {
        int min, max;
        try {
            min = minAmount != null ? Integer.parseInt(minAmount) : 0;
            max = maxAmount != null ? Integer.parseInt(maxAmount) : Integer.MAX_VALUE;
        } catch (NumberFormatException e) {
            min = 0;
            max = Integer.MAX_VALUE;
        }
        return productService.getProductByPriceRange(min, max); // return Json
    }
}
