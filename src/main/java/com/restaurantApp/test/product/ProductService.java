package com.restaurantApp.test.product;

import com.restaurantApp.test.auth.AuthenticateContextService;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final RepositoryRepository repositoryRepository;
    private final AuthenticateContextService authenticationContextService;

    public void deleteProduct(Integer productId, Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produkt nie istnieje"));
        productRepository.delete(product);
    }

    public void updateProduct(ProductDto productDto, Integer repositoryId, Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        var product = productRepository.findById(productDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Product nie istnieje"));

        authenticationContextService.authenticateRepositoryList(repositoryId);
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setWeight(productDto.getWeight());
        productRepository.save(product);
    }

    public Product findProduct(String name, Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        return productRepository.findByName(name);
    }

    public void createProduct(ProductDto productDto, Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        Repository repository = repositoryRepository.findById(productDto.getRepositoryId())
                .orElseThrow(() -> new IllegalArgumentException("Repository nie istnieje"));
        var product = productMapper.dtoToProduct(productDto);
        repository.getProductList().add(product);
        productRepository.save(product);
    }

    public List<Product> availableProducts(Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        return productRepository.findByWeightGreaterThan(0);
    }
}
