package com.app.modules.users;

import com.app.common.annotations.IsPublic;
import com.app.common.annotations.LoggedInUser;
import com.app.modules.jwt.classes.JwtPayload;
import com.app.modules.users.dto.CreateUserDto;
import com.app.modules.users.dto.GetUserResponseDto;
import com.app.modules.users.dto.LoginResponseDto;
import com.app.modules.users.dto.LoginUserDto;
import com.app.modules.users.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  @IsPublic
  @Operation(summary = "Create a new user", description = "Creates a new user account with the provided information. Email must be unique.")
  @PostMapping
  public ResponseEntity<Object> createUser(@Valid @RequestBody CreateUserDto dto) {
    User user = userService.createUser(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }

  @IsPublic
  @Operation(summary = "Login a user")
  @PostMapping("/login")
  public ResponseEntity<Object> loginUser(@Valid @RequestBody LoginUserDto dto) {
    LoginResponseDto data = userService.loginUser(dto);
    return ResponseEntity.status(HttpStatus.OK).body(data);
  }

  @Operation(summary = "Get the current user")
  @GetMapping("/me")
  public ResponseEntity<Object> getUser(
    @Parameter(hidden = true) @LoggedInUser JwtPayload jwtPayload
  ) {
    GetUserResponseDto data = userService.getUser(jwtPayload);
    return ResponseEntity.status(HttpStatus.OK).body(data);
  }
}
