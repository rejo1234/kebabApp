package com.restaurantApp.test.DatesAtStart;

import com.restaurantApp.test.product.Product;
import com.restaurantApp.test.product.ProductRepository;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.user.Role;
import com.restaurantApp.test.user.User;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class CreateDatesAtStartApplication implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final RepositoryRepository repositoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        String password = passwordEncoder.encode("string123");
        String password2 = passwordEncoder.encode("string456");

        User user = new User(null,
                "Kamil",
                "Franczak",
                "kamilfranczak@gmail.com",
                "idk",
                Role.USER,
                null,
                null,
                null
        );
        User user2 = new User(null,
                "Maciek",
                "Franczak",
                "maciekfranczak@gmail.com",
                "idk2",
                Role.USER,
                null,
                null,
                null
        );
        user.setPassword(password);
        user2.setPassword(password2);
        userRepository.save(user);
        userRepository.save(user2);
        Restaurant restaurant = new Restaurant(
                null,
                "pajda",
                "katowice",
                "odrodzenia 11",
                null,
                null,
                null
        );
        Restaurant restaurant2 = new Restaurant(
                null,
                "bula",
                "czestochowa",
                "pokoju 11",
                null,
                null,
                null
        );
        restaurantRepository.save(restaurant);
        restaurantRepository.save(restaurant2);
        Repository repository = new Repository(
                null,
                "kielce 31",
                "kielce 12",
                null,
                null,
                null,
                null
        );
        Repository repository2 = new Repository(
                null,
                "bedzin 31",
                "bedzin 12",
                null,
                null,
                null,
                null
        );
        repositoryRepository.save(repository);
       repositoryRepository.save(repository2);
        Product product = new Product(
                null,
                "meat",
                11.0,
                null
        );
        Product product2 = new Product(
                null,
                "salad",
                16.0,
                null
        );
        productRepository.save(product);
        productRepository.save(product2);
    }
}
