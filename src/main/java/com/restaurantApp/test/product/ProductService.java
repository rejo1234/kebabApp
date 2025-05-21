package com.restaurantApp.test.product;

import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.repository.RepositoryValidator;
import com.restaurantApp.test.user.UserValidator;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final RepositoryRepository repositoryRepository;
    private final UserValidator userValidator;
    private final RepositoryValidator repositoryValidator;
    private final ProductValidator productValidator;


    public void deleteProduct(Integer productId, Integer userId) {
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("Repository nie istnieje");
        }
        userValidator.validateUserId(userId);
        productValidator.validateProductIdBelongsToRepository(productId);
        productRepository.deleteById(productId);
    }
    public void modifyProduct(ProductDto productDto,Integer productId, Integer repositoryId, Integer userId) {
        productValidator.validateModifyProduct(productDto, productId, repositoryId, userId);
        var prod = productMapper.dtoToProduct(productDto);
        productRepository.save(prod);
    }

    public Product findProduct(String name, Integer userId) {
        userValidator.validateUserId(userId);
        return productRepository.findByName(name);
    }

    public void createProduct(ProductDto productDto, Integer userId) {
        userValidator.validateUserId(userId);
        repositoryValidator.validateRepositoryId(productDto.getRepositoryId());
        Repository repository = repositoryRepository.findById(productDto.getRepositoryId())
                .orElseThrow(() -> new IllegalArgumentException("Repository nie istnieje"));
        var product = productMapper.dtoToProduct(productDto);
        repository.getProductList().add(product);
        productRepository.save(product);
    }

    public List<Product> availableProducts(Integer userId) {
        userValidator.validateUserId(userId);
        return productRepository.findByWeightGreaterThan(0);
    }
}
