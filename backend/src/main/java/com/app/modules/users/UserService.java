package com.app.modules.users;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.modules.users.entities.User;
import com.app.modules.users.dto.CreateUserDTO;
import com.app.modules.users.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public User createUser(CreateUserDTO dto) {
    Optional<User> existingUser = userRepository.findByEmail(dto.getEmail());
    if (existingUser.isPresent()) {
      throw new RuntimeException("User already exists");
    }
    User user = new User();
    System.out.println("Creating user: " + dto);
    // user.setEmail(dto.getEmail());
    // user.setFirstName(dto.getFirstName());
    // user.setLastName(dto.getLastName());
    // user.setPassword(dto.getPassword());
    // user.setRole(dto.getRole());
    // userRepository.save(user);
    return user;
  }
}
