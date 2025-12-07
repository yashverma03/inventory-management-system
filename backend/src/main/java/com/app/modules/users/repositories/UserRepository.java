package com.app.modules.users.repositories;

import com.app.modules.users.entities.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Query("SELECT user FROM User user WHERE user.email = :email AND user.deletedAt IS NULL")
  Optional<User> findByEmail(@Param("email") String email);

  @Override
  @Query("SELECT user FROM User user WHERE user.id = :id AND user.deletedAt IS NULL")
  Optional<User> findById(@Param("id") Long id);
}
