package com.app.modules.users;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.app.modules.users.entities.User;
import com.app.modules.jwt.JwtService;
import com.app.modules.jwt.classes.JwtPayload;
import com.app.modules.users.dto.CreateUserDto;
import com.app.modules.users.dto.GetUserResponseDto;
import com.app.modules.users.dto.LoginResponseDto;
import com.app.modules.users.dto.LoginUserDto;
import com.app.modules.users.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private JwtService jwtService;

  private String hashPassword(String password) {
    return passwordEncoder.encode(password);
  }

  private boolean isPasswordValid(String password, String hashedPassword) {
    return passwordEncoder.matches(password, hashedPassword);
  }

  private Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  private User getUserByEmailOrThrow(String email) {
    Optional<User> existingUser = getUserByEmail(email);
    if (existingUser.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
    return existingUser.get();
  }

  private User getUserById(Long id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
    return user.get();
  }

  public User createUser(CreateUserDto dto) {
    Optional<User> existingUser = getUserByEmail(dto.getEmail());
    if (existingUser.isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
    }
    User user = modelMapper.map(dto, User.class);
    user.setPassword(hashPassword(dto.getPassword()));
    return userRepository.save(user);
  }

  public LoginResponseDto loginUser(LoginUserDto dto) {
    String email = dto.getEmail();
    String password = dto.getPassword();
    User user = getUserByEmailOrThrow(email);
    boolean isPasswordValid = isPasswordValid(password, user.getPassword());
    if (!isPasswordValid) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
    }
    JwtPayload jwtPayload = new JwtPayload(user.getId(), user.getRole());
    String token = jwtService.generateToken(jwtPayload);
    return new LoginResponseDto(token, user);
  }

  public GetUserResponseDto getUser(JwtPayload jwtPayload) {
    User user = getUserById(jwtPayload.getUserId());
    return new GetUserResponseDto(jwtPayload, user);
  }
}
