package com.restaurantApp.test.product;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
public class ProductController {
    private ProductService productService;
    private ShowProductResponse showProductResponse;

    @PatchMapping("/update/{repositoryId}")
    public ResponseEntity<Void> updateProduct(
            @RequestBody CreateProductRequest createProductRequest, @PathVariable Integer repositoryId
            ,@RequestParam Integer userId
    ) {
        productService.updateProduct(createProductRequest.getProductDto(), repositoryId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createProductProduct(
            @RequestBody @Valid CreateProductRequest createProductRequest, @RequestParam Integer userId
    ) {
        productService.createProduct(createProductRequest.getProductDto(), userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get")
    public ResponseEntity<Product> getProduct(
            @RequestParam String nameProduct, @RequestParam Integer userId
    ) {
        return ResponseEntity.ok(showProductResponse.getProduct(nameProduct, userId));
    }

    @GetMapping("/get-available")
    public ResponseEntity<List<Product>> showAvailableProducts(@RequestParam Integer userId) {
        return ResponseEntity.ok(showProductResponse.getListProducts(userId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRProduct(
            @RequestParam Integer productId, @RequestParam Integer userId
    ) {
        productService.deleteProduct(productId, userId);
        return ResponseEntity.ok().build();
    }
}
