package com.restaurantApp.test.restaurant;

import com.restaurantApp.test.product.Product;
import com.restaurantApp.test.product.ProductRepository;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final ProductRepository productRepository;
    private final RepositoryRepository repositoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    public void connectRepositoryToRestaurant(RestaurantRepositoryRequest restaurantRepositoryRequest) {
        Restaurant restaurant = restaurantRepository.findById(restaurantRepositoryRequest.getIdRestaurant())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        Repository repository = repositoryRepository.findById(restaurantRepositoryRequest.getIdRepository())
                .orElseThrow(() -> new IllegalArgumentException("Repozytorium nie istnieje"));

        restaurant.getRepositoryList().add(repository);
        restaurantRepository.save(restaurant);
    }

    public void connectProductToRestaurant(RestaurantProductRequest restaurantProductRequest) {
        Restaurant restaurant = restaurantRepository.findById(restaurantProductRequest.getIdRestaurant())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        Product product = productRepository.findById(restaurantProductRequest.getIdProduct())
                .orElseThrow(() -> new IllegalArgumentException("Produkt nie istnieje"));
        restaurant.getProductListRestaurant().add(product);
        restaurantRepository.save(restaurant);
    }

    public List<Restaurant> showRestaurantsConnectedToOneRepository(@RequestParam int id) {
        Repository repository = repositoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Repozytorium nie istnieje"));
        return repository.getRestaurantsList();
    }

    public List<Restaurant> showAllRestaurants() {
        return restaurantRepository.findAll();
    }
public Restaurant getRestaurant(int id) {
    return restaurantRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
}
    public void createRestaurant(CreateRestaurantRequest createRestaurantRequest) {
        //co trzyma tutaj createRestaurantRequest, trzyma wszystkie zmienne z table(w tym ID ale ID jest NULL)

        //restaurant przyjumje zminen z funckji bez ID z funckji
        //ogolnie jak nie podam nic to jest pusta kolumna
        var restaurant = restaurantMapper.dtoToRestaurant(createRestaurantRequest);
        // natomiast jeśli zrobie uzyje tej funckji to wstawia się null (nie wyciagam zmiennej adress w funckji)
        // Jak skontrolowac requet zeby chcial tylko funkcje jakie chce, zamiast forcowac wszystkie zmienne zawsze?
        var restaurant2 = restaurantMapper.dtoToRestaurantWithOutAddress(createRestaurantRequest);
        restaurantRepository.save(restaurant);
    }

    public void updateRestaurant(CreateRestaurantRequest createRestaurantRequest) {
        var restaurant = restaurantRepository.findById(createRestaurantRequest.getRestaurantDto().getId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        //czemu tutaj nie używam mappera??
        restaurant.setName(createRestaurantRequest.getRestaurantDto().getName());
        restaurant.setCity(createRestaurantRequest.getRestaurantDto().getCity());
        restaurant.setAddress(createRestaurantRequest.getRestaurantDto().getAddress());
        restaurantRepository.save(restaurant);
    }
    public void deleteRestaurant(int restaurantId) {
        var restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        if (restaurant != null){
            restaurantRepository.deleteById(restaurantId);
        }
    }
}
