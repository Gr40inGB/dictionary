package org.gr40in.dictionary.service;

import lombok.RequiredArgsConstructor;
import org.gr40in.dictionary.dao.Role;
import org.gr40in.dictionary.dao.User;
import org.gr40in.dictionary.repository.UserRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(long id, User user) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isEmpty()) throw new UsernameNotFoundException("no user id" + id);
        User userForEdit = userById.get();
        userForEdit.setName(user.getName());
        userForEdit.setEmail(user.getEmail());
        userForEdit.setPassword(passwordEncoder.encode(user.getPassword()));
//        userForEdit.setEnabled(user.isEnabled());
        userForEdit.setRoles(user.getRoles());
        userRepository.save(userForEdit);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @EventListener(ContextRefreshedEvent.class)
    private void createUsers() {

        if (userRepository.getUserByName("user").isEmpty()) {
            var user = User.builder()
                    .name("user")
                    .email("User@mail.com")
                    .password(passwordEncoder.encode("12345"))
                    .roles(Collections.singleton(Role.USER))
                    .build();
            userRepository.save(user);
        }
        if (userRepository.getUserByName("root").isEmpty()) {
            var root = User.builder()
                    .name("root")
                    .email("Root@mail.com")
                    .password(passwordEncoder.encode("godMode"))
                    .roles(Collections.singleton(Role.ADMIN))
                    .build();
            userRepository.save(root);
        }

    }
}
