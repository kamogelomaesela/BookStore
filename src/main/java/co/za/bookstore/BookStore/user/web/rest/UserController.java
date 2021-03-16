package co.za.bookstore.BookStore.user.web.rest;

import co.za.bookstore.BookStore.user.entity.Role;
import co.za.bookstore.BookStore.user.entity.User;
import co.za.bookstore.BookStore.user.service.interfaces.UserService;
import co.za.bookstore.BookStore.user.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Set<User>> getAllUsers() {
        Set<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody UserDto userDto) {
        Set<Role> roles = userService.getRoles(userDto.getRoles());
        User user = userDto.toUser();
        user.setRoles(roles);
        return new ResponseEntity(userService.createNewUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<User> getUser(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.getUser(email));
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<Role>> getRoles() {
        return ResponseEntity.ok(userService.getRoles());
    }

}
