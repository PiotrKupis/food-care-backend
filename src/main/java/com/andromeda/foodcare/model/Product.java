package com.andromeda.foodcare.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity(name = "products")
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;


    @Column(name = "owner_id")
    private Long ownerId;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "regular_price")
    @DecimalMax("1000.0")
    @DecimalMin("0.0")
    private double regularPrice;

    @NotNull
    @Column(name = "discountedPrice")
    @DecimalMax("1000.0")
    @DecimalMin("0.0")
    private double discountedPrice;

    @NotBlank
    @Column(name = "expiration_date")
    private String expirationDate;

    @Column(name = "vegan")
    private boolean vegan;

    @Column(name = "link_to_resource")
    private String linkToResource;

    @Column(name = "public_id")
    private String publicId;

    @NotNull
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    public Product(Long ownerId, String name, double regularPrice, double discountedPrice,
        String expirationDate, boolean vegan, String linkToResource) {
        this.ownerId = ownerId;
        this.name = name;
        this.regularPrice = regularPrice;
        this.discountedPrice = discountedPrice;
        this.expirationDate = expirationDate;
        this.vegan = vegan;
        this.linkToResource = linkToResource;
    }
}
