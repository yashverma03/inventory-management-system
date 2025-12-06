package com.app.modules.users;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

  @Autowired
  private ModelMapper modelMapper;

  private String hashPassword(String password) {
    return passwordEncoder.encode(password);
  }

  public User createUser(CreateUserDTO dto) {
    Optional<User> existingUser = userRepository.findByEmail(dto.getEmail());
    if (existingUser.isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
    }
    User user = modelMapper.map(dto, User.class);
    user.setPassword(hashPassword(dto.getPassword()));
    return userRepository.save(user);
  }
}
