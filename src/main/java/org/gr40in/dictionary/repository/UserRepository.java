package org.gr40in.dictionary.repository;

import org.gr40in.dictionary.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByName(String name);

    Optional<User> getUserById(Long id);
}
