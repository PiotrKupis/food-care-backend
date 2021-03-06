package com.andromeda.foodcare.model;

import com.andromeda.foodcare.enums.UserRole;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class responsible for keeping information about single user.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "business")
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @NotBlank
    @Size(min = 5, max = 200)
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Size(min = 2, max = 60)
    @Email(regexp = ".+@.+\\..+")
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Size(min = 6, max = 12)
    @Column(nullable = false, name = "phone_number", length = 12)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
        name = "business_id",
        referencedColumnName = "id")
    private Business business;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "favorite",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "business_id", referencedColumnName = "id")})
    private Set<Business> favorites = new HashSet<>();

    public User(String name, UserRole role, String password, String email, String phoneNumber) {
        this.name = name;
        this.role = role;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
