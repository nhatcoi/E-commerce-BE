package com.example.ecommerceweb.controllers;

import com.example.ecommerceweb.dtos.ProductDTO;
import com.example.ecommerceweb.entities.Product;
import com.example.ecommerceweb.services.CategoryService;
import com.example.ecommerceweb.services.services_impl.FirebaseStorageService;
import com.example.ecommerceweb.services.ProductService;
import com.example.ecommerceweb.services.services_impl.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@RequestMapping("products")
@Controller
@RequiredArgsConstructor
public class ManageProductController {
    private final FirebaseStorageService firebaseStorageService;
    private final ProductImageService productImageService;
    private final ProductService productService;
    private final CategoryService categoryService;


    @GetMapping("/show")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add-product"; // Return the view name directly
    }

    @PostMapping("/add")
    @ResponseBody
    public String addProduct(ProductDTO productDTO,
                             @RequestParam("file") MultipartFile file,
                             RedirectAttributes ra) throws IOException, InterruptedException {
        String imageUrl = firebaseStorageService.uploadImage(file);
        productDTO.setThumbnail(imageUrl);
        Product newProduct = productService.createProduct(productDTO);

        // Save image information to product_images table
        productImageService.saveProductImage(newProduct, imageUrl);

        ra.addFlashAttribute("message_add", "The staff has been saved successfully.");
        return "redirect:/products/show";
    }

}
