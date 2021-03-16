package co.za.bookstore.BookStore.user.service.impl;

import co.za.bookstore.BookStore.common.exception.EntityConstraintViolationException;
import co.za.bookstore.BookStore.common.exception.EntityNotFoundException;
import co.za.bookstore.BookStore.user.entity.EnumRole;
import co.za.bookstore.BookStore.user.entity.Role;
import co.za.bookstore.BookStore.user.entity.User;
import co.za.bookstore.BookStore.user.repo.RoleRepository;
import co.za.bookstore.BookStore.user.repo.UserRepository;
import co.za.bookstore.BookStore.user.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    @Override
    public User createNewUser(@Valid User user) {
        User existingUser = userRepository.findByEmail(user.getEmail()).get();
        if (existingUser != null) {
            throw new EntityConstraintViolationException("Email", user.getEmail(), User.class);
        }
        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public Set<User> getAllUsers() {
        Sort sort = Sort.by("name").ascending();
        return new HashSet<>(userRepository.findAll(sort));
    }

    @Override
    public Set<Role> getRoles() {
        return new HashSet<>(roleRepository.findAll());
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(User.class));
    }

    @Override
    public Role getRole(EnumRole enumRole) {
        return roleRepository.findByName(enumRole).orElseThrow(() -> new EntityNotFoundException(Role.class));
    }

    @Override
    public Set<Role> getRoles(Set<EnumRole> enumRoles) {
        Set<Role> roles = enumRoles.stream()
                .map(enumRole -> getRole(enumRole))
                .collect(Collectors.toSet());
        return roles;
    }

}
