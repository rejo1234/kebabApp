package com.restaurantApp.test.user;

import com.restaurantApp.test.auth.CreateUserRequest;
import com.restaurantApp.test.product.CreateProductRequest;
import com.restaurantApp.test.product.ProductRepository;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.repository.RepositoryService;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.restaurant.RestaurantRepositoryRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final RepositoryRepository repositoryRepository;

    public void deleteUser(int userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User nie istnieje"));
        if (user != null) {
            userRepository.deleteById(userId);
        }
    }

    public void updateUser(CreateUserRequest createUserRequest) {
        var user = userRepository.findById(createUserRequest.getUserDto().getId())
                .orElseThrow(() -> new IllegalArgumentException("Repozytorium nie istnieje"));
        user.setEmail(createUserRequest.getUserDto().getEmail());
        user.setFirstname(createUserRequest.getUserDto().getFirstname());
        user.setLastname(createUserRequest.getUserDto().getLastname());
        user.setPassword(createUserRequest.getUserDto().getPassword());
        userRepository.save(user);
    }

    public void connectRestaurantToUser(UserRestaurantRequest userRestaurantRequest) {
        Restaurant restaurant = restaurantRepository.findById(userRestaurantRequest.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        User user = userRepository.findById(userRestaurantRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));

        user.getUserListRestaurant().add(restaurant);
        userRepository.save(user);
    }
    public void connectRepositoryToUser(UserRepositoryRequest userRepositoryRequest) {
        Repository repository = repositoryRepository.findById(userRepositoryRequest.getRepositoryId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        User user = userRepository.findById(userRepositoryRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));

        user.getUserListRepository().add(repository);
        userRepository.save(user);
    }
}
