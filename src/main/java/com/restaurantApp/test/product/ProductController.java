package com.restaurantApp.test.product;
import com.restaurantApp.test.repository.CreateRepositoryRequest;
import com.restaurantApp.test.repository.RepositoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
public class ProductController {
    private RepositoryService repositoryService;
    private ProductService productService;
    @PatchMapping("/updateProduct")
    public ResponseEntity<Void> updateProduct(
            @RequestBody CreateProductRequest createProductRequest
    ) {
        productService.updateProduct(createProductRequest);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/createProduct")
    public ResponseEntity<Void> createProductProduct(
            @RequestBody CreateProductRequest createProductRequest
    ) {
        productService.createProduct(createProductRequest);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/getProduct")
    public ResponseEntity<Product> showProduct(
            @RequestBody String product
    ) {
        return ResponseEntity.ok(productService.findProduct(product));
    }
    @GetMapping("/getAvailableProducts")
    public ResponseEntity<List<Product>> showAvailableProducts() {
        return ResponseEntity.ok(productService.availableProducts());
    }
    @DeleteMapping("/deleteProduct")
    public ResponseEntity<Void> deleteRProduct(
            @RequestParam int productId
    ) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }
}
