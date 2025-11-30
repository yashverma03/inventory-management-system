package com.app.modules.users;

import com.app.modules.users.dto.CreateUserDTO;
import com.app.modules.users.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User management APIs")
public class UserController {

  @Autowired
  private UserService userService;

  @Operation(summary = "Create a new user", description = "Creates a new user account with the provided information. Email must be unique.")
  @PostMapping
  public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserDTO dto) {
    User user = userService.createUser(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }
}
