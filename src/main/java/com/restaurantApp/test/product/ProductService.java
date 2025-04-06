package com.restaurantApp.test.product;
import com.restaurantApp.test.repository.CreateRepositoryRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public void deleteProduct(int productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produkt nie istnieje"));
        if (product != null){
            productRepository.deleteById(productId);
        }
    }
    public void updateProduct(CreateProductRequest createProductRequest) {
        var product = productRepository.findById(createProductRequest.getProductDto().getId())
                .orElseThrow(() -> new IllegalArgumentException("Repozytorium nie istnieje"));
        //czemu tutaj nie używam mappera??
        product.setNameProduct(createProductRequest.getProductDto().getNameProduct());
        product.setKgs(createProductRequest.getProductDto().getWeightProduct());
        productRepository.save(product);
    }
    public void createProduct(CreateProductRequest createProductRequest){
        System.out.println("jestem w putproduct");
        var product = productMapper.dtoToProduct(createProductRequest);
        productRepository.save(product);
    }
    public Product findProduct(String string){
        return productRepository.findByNameProduct(string);
    }
    public List<Product> availableProducts(){
        return productRepository.findByKgsGreaterThan(0);
    }
}
