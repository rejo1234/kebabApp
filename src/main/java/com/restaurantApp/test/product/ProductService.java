package com.restaurantApp.test.product;

import com.restaurantApp.test.auth.ContextService;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final RepositoryRepository repositoryRepository;
    private final ContextService authenticationContextService;


    public void deleteProduct(Integer productId, Integer userId) {
        authenticationContextService.validateUserId(userId);
        authenticationContextService.validateProductIdBelongsToRepository(productId);
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("Repository nie istnieje");
        }
        productRepository.deleteById(productId);
    }
    public void updateProduct(ProductDto productDto,Integer productId, Integer repositoryId, Integer userId) {
        Repository repository = repositoryRepository.findById(repositoryId)
                .orElseThrow(() -> new IllegalArgumentException("repository nie istnieje"));
        authenticationContextService.validateUserId(userId);
        authenticationContextService.validateRepositoryId(repository.getId());
        authenticationContextService.validateProductIdBelongsToRepository(productId);
        var prod = productMapper.dtoToProduct(productDto);
        productRepository.save(prod);
//                authenticationContextService.validateUserId(userId);
//        authenticationContextService.validateRepositoryId(repositoryDto.getId());
//        var repository = repositoryMapper.dtoToRepository(repositoryDto);
//        repositoryRepository.save(repository);
    }

    public Product findProduct(String name, Integer userId) {
        authenticationContextService.validateUserId(userId);
        return productRepository.findByName(name);
    }

    public void createProduct(ProductDto productDto, Integer userId) {
        authenticationContextService.validateUserId(userId);
        authenticationContextService.validateRepositoryId(productDto.getRepositoryId());
        Repository repository = repositoryRepository.findById(productDto.getRepositoryId())
                .orElseThrow(() -> new IllegalArgumentException("Repository nie istnieje"));
        var product = productMapper.dtoToProduct(productDto);
        repository.getProductList().add(product);
        productRepository.save(product);
    }

    public List<Product> availableProducts(Integer userId) {
        authenticationContextService.validateUserId(userId);
        return productRepository.findByWeightGreaterThan(0);
    }
}
