package com.andromeda.foodcare.model;

import com.andromeda.foodcare.enums.BusinessType;
import java.time.LocalTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@Entity(name = "business")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 60)
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Size(min = 5, max = 200)
    @Email(regexp = ".+@.+\\..+")
    @Column(nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
        name = "address_id",
        referencedColumnName = "id")
    private Address address;

    @NotBlank
    @Size(min = 6, max = 12)
    @Column(nullable = false, name = "phone_number", length = 12)
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BusinessType businessType;

    @NotNull
    @Column(name = "open_hour", columnDefinition = "TIME", nullable = false)
    private LocalTime openHour;

    @NotNull
    @Column(name = "close_hour", columnDefinition = "TIME", nullable = false)
    private LocalTime closeHour;
}
