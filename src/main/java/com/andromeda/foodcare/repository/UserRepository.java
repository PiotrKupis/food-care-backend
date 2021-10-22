package com.andromeda.foodcare.repository;

import com.andromeda.foodcare.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository responsible for managing object of type {@link User}.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Method responsible for finding user by its email.
     *
     * @param email email of a specific user
     * @return optional of type {@link User}
     */
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
