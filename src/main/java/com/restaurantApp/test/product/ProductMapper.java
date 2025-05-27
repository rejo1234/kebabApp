package com.restaurantApp.test.product;

import com.restaurantApp.test.repository.Repository;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class ProductMapper {


    public Product dtoToProduct(ProductDto productDto, Repository repository) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .weight(productDto.getWeight())
                .repository(repository)
                .build();
    }

    public ProductDto productToDto(Product product) {
        Integer repositoryId = product.getRepository().getId();

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .repositoryId(repositoryId)
                .build();
    }
}
