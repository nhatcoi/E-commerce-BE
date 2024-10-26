package com.example.ecommerceweb.services.services_impl;

import com.example.ecommerceweb.dtos.ProductDTO;
import com.example.ecommerceweb.entities.Category;
import com.example.ecommerceweb.entities.Product;
import com.example.ecommerceweb.repository.CategoryRepository;
import com.example.ecommerceweb.repository.ProductRatingRepository;
import com.example.ecommerceweb.repository.ProductRepository;
import com.example.ecommerceweb.services.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRatingRepository productRatingRepository;

    @Value("${image.base.url}")
    private String imageBaseurl;

    private static final Logger logger = Logger.getLogger(ProductServiceImpl.class.getName());

    @Override
    public Product createProduct(ProductDTO productDTO) throws IOException, InterruptedException {
        Optional<Category> cate = categoryRepository
                .findById(productDTO.getCategoryId());
        if (cate.isEmpty()) {
            return null;
        }
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .categoryId(cate.get())
                .build();
        return productRepository.save(newProduct);
    }

    // get token if not access permission
//    public String getNameImage(String urlImage) throws MalformedURLException {
//        URL url = new URL(urlImage);
//        String fileName = url.getPath().substring(url.getPath().lastIndexOf('/') + 1);
//        if (fileName.contains("?")) {
//            fileName = fileName.substring(0, fileName.indexOf('?'));
//        }
//
//        return fileName;
//    }
//    public String getImageUrl(String fileName) {
//        String downloadToken = getDownloadToken("ecommerce-web-18f77.appspot.com", fileName);
//
//        return imageBaseurl
//                + URLEncoder.encode(fileName, StandardCharsets.UTF_8)
//                + "?alt=media&token=" + downloadToken;
//    }
//
//    public String getDownloadToken(String bucketName, String filePath) {
//        // Lấy Blob (file) từ Firebase Storage
//        Blob blob = StorageClient.getInstance().bucket(bucketName).get(filePath);
//
//        // Lấy metadata từ Blob và kiểm tra token
//        String downloadToken = Objects.requireNonNull(blob.getMetadata()).get("firebaseStorageDownloadTokens");
//
//        return downloadToken != null ? downloadToken : "";
//    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product productToUpdate = getProductById(id);
        assert productToUpdate != null;
        // copy data from productDTO to productToUpdate
        // co the su dung ModelMapper
        productToUpdate = Product.builder()
                .id(id)
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .categoryId(categoryRepository.findById(productDTO.getCategoryId())
                        .orElse(null))
                .build();
        return productRepository.save(productToUpdate);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getAllProducts(PageRequest pageRequest) {
        // Retrieve all products with pagination
        return productRepository.findAll(pageRequest);
    }

    @Override
    public boolean isProductExist(Long id) {
        return false;
    }

    @Override
    public Page<Product> getProductsByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> getLatestProducts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return productRepository.fetchLatestProducts(pageable);
    }

    @Override
    public List<Product> getTopRatedProducts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return productRatingRepository.fetchTopRatedProducts(pageable);
    }

    public List<Product> getProductByPriceRange(int minAmount, int maxAmount) {
        return productRepository.findByPriceBetween(minAmount, maxAmount);
    }

}
