package com.restaurantApp.test.product;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.restaurantApp.test.repository.Repository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "repository_id")
    @JsonManagedReference(value = "repository-product")
    private Repository repository;
}
