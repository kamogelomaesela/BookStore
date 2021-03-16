package co.za.bookstore.BookStore.user.service.impl;

import co.za.bookstore.BookStore.user.entity.User;
import co.za.bookstore.BookStore.user.entity.UserPrinciple;
import co.za.bookstore.BookStore.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email : " + email));
        return UserPrinciple.build(user);
    }
}
