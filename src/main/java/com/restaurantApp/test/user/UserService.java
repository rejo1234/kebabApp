package com.restaurantApp.test.user;

import com.restaurantApp.test.auth.CreateUserRequest;
import com.restaurantApp.test.auth.UserDto;
import com.restaurantApp.test.auth.UserMapper;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.repository.RepositoryValidator;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.restaurant.RestaurantValidator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final RepositoryRepository repositoryRepository;
    private final UserMapper userMapper;
    private final RestaurantValidator restaurantValidator;
    private final UserValidator userValidator;
    private final RepositoryValidator repositoryValidator;

    public void deleteConnectionUserAndRestaurant(UserRestaurantRequest userRestaurantRequest, Integer userId) {
        userValidator.validateUserId(userId);
        restaurantValidator.validateRestaurantId(userRestaurantRequest.getRestaurantId());
        User user = userRepository.findById(userRestaurantRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User nie znaleziony"));

        Restaurant restaurant = restaurantRepository.findById(userRestaurantRequest.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restauracji nie znaleziono"));

        restaurant.getUserList().remove(user);
        user.getRestaurantList().remove(restaurant);
        userRepository.save(user);
    }

    public void deleteConnectionUserAndRepository(UserRepositoryRequest userRepositoryRequest, Integer userId) {
        userValidator.validateUserId(userId);
        repositoryValidator.validateRepositoryId(userRepositoryRequest.getRepositoryId());
        User user = userRepository.findById(userRepositoryRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User nie znaleziony"));

        Repository repository = repositoryRepository.findById(userRepositoryRequest.getRepositoryId())
                .orElseThrow(() -> new RuntimeException("Repository nie znaleziono"));

        repository.getUserList().remove(user);
        user.getRepositoryList().remove(repository);
        userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        userValidator.validateUserId(userId);
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("user nie istnieje");
        }
        userRepository.deleteById(userId);
    }

    public void modifyUser(UserDto userDto, Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("user nie istnieje");
        }
        userValidator.validateUserId(userId);
        var updatedUser = userMapper.dtoToUser(userDto);
        userRepository.save(updatedUser);
    }

    public void connectRestaurantToUser(UserRestaurantRequest userRestaurantRequest, Integer userId) {
        userValidator.validateUserId(userId);
        Restaurant restaurant = restaurantRepository.findById(userRestaurantRequest.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        User user = userRepository.findById(userRestaurantRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        restaurant.getUserList().add(user);
        user.getRestaurantList().add(restaurant);
        userRepository.save(user);
    }

    public void connectRepositoryToUser(UserRepositoryRequest userRepositoryRequest, Integer userId) {
        userValidator.validateUserId(userId);
        Repository repository = repositoryRepository.findById(userRepositoryRequest.getRepositoryId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        User user = userRepository.findById(userRepositoryRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        repository.getUserList().add(user);
        user.getRepositoryList().add(repository);
        userRepository.save(user);
    }
}
