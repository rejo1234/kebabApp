package com.restaurantApp.test.product;

import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryValidator;
import com.restaurantApp.test.user.UserValidator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductValidator {
    private final UserValidator userValidator;
    private final RepositoryValidator repositoryValidator;
    private final ProductRepository productRepository;

    public void validateProductIdBelongsToRepository(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("restaurant nie istnieje"));
        Repository repository = product.getRepository();
        repositoryValidator.validateRepositoryId(repository.getId());
    }

    public void validateModifyProduct(ProductDto productDto, Integer productId, Integer repositoryId, Integer userId) {
        userValidator.validateUserId(userId);
        repositoryValidator.validateRepositoryId(repositoryId);
        validateProductIdBelongsToRepository(productId);
        if (!productDto.getRepositoryId().equals(repositoryId)) {
            throw new SecurityException("Repo Ids are not equeals");
        }
    }
}
