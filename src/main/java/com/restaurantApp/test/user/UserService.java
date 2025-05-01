package com.restaurantApp.test.user;

import com.restaurantApp.test.auth.AuthenticateContextService;
import com.restaurantApp.test.auth.CreateUserRequest;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final RepositoryRepository repositoryRepository;
    private final AuthenticateContextService authenticationContextService;

    public void deleteConnectionUserAndRestaurant(UserRestaurantRequest userRestaurantRequest, Integer userId) {
        authenticationContextService.validateUserId(userId);
        //sprawdzić czy id restarunt jest dobre czy zrobic osobny requestparam na restaurantId
        authenticationContextService.validateRestaurantList(userRestaurantRequest.getRestaurantId());
        User user = userRepository.findById(userRestaurantRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User nie znaleziony"));

        Restaurant restaurant = restaurantRepository.findById(userRestaurantRequest.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restauracji nie znaleziono"));
        restaurant.getUserList().remove(user);
        user.getRestaurantList().remove(restaurant);
        userRepository.save(user);
    }

    public void deleteConnectionUserAndRepository(UserRepositoryRequest userRepositoryRequest, Integer userId) {
        authenticationContextService.validateUserId(userId);
        //sprawdzić czy id repository jest dobre czy zrobic osobny requestparam na repositoryId
        authenticationContextService.validateRepositoryList(userRepositoryRequest.getRepositoryId());
        User user = userRepository.findById(userRepositoryRequest.getRepositoryId())
                .orElseThrow(() -> new RuntimeException("User nie znaleziony"));

        Repository repository = repositoryRepository.findById(userRepositoryRequest.getRepositoryId())
                .orElseThrow(() -> new RuntimeException("Repository nie znaleziono"));
        repository.getUserList().remove(user);
        user.getRepositoryList().remove(repository);
        userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        authenticationContextService.validateUserId(userId);
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("user nie istnieje");
        }
        userRepository.deleteById(userId);
    }

    public void updateUser(CreateUserRequest createUserRequest, Integer userId) {
        authenticationContextService.validateUserId(userId);
        var user = userRepository.findById(createUserRequest.getUserDto().getId())
                .orElseThrow(() -> new IllegalArgumentException("User nie istnieje"));
        user.setEmail(createUserRequest.getUserDto().getEmail());
        user.setFirstname(createUserRequest.getUserDto().getFirstname());
        user.setLastname(createUserRequest.getUserDto().getLastname());
        user.setPassword(createUserRequest.getUserDto().getPassword());
        userRepository.save(user);
    }

    public void connectRestaurantToUser(UserRestaurantRequest userRestaurantRequest, Integer userId) {
        authenticationContextService.validateUserId(userId);
        Restaurant restaurant = restaurantRepository.findById(userRestaurantRequest.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        User user = userRepository.findById(userRestaurantRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        restaurant.getUserList().add(user);
        user.getRestaurantList().add(restaurant);
        userRepository.save(user);
    }

    public void connectRepositoryToUser(UserRepositoryRequest userRepositoryRequest, Integer userId) {
        authenticationContextService.validateUserId(userId);
        Repository repository = repositoryRepository.findById(userRepositoryRequest.getRepositoryId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        User user = userRepository.findById(userRepositoryRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        repository.getUserList().add(user);
        user.getRepositoryList().add(repository);
        userRepository.save(user);
    }
}
