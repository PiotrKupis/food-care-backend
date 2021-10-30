package com.andromeda.foodcare.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank
    @Size(min = 4, max = 80)
    @Column(nullable = false)
    private String country;

    @NotBlank
    @Size(min = 3, max = 80)
    @Column(nullable = false)
    private String city;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @NotBlank
    @Size(min = 3, max = 80)
    @Column(nullable = false)
    private String street;

    @NotBlank
    @Size(min = 1, max = 50)
    @Column(name = "street_number", nullable = false)
    private String streetNumber;
}
