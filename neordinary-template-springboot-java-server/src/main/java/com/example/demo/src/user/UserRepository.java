package com.example.demo.src.user;

import com.example.demo.src.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.common.entity.BaseEntity.*;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdAndState(Long id, State state);
    Optional<User> findByEmailAndState(String email, State state);
    List<User> findAllByEmailAndState(String email, State state);
    List<User> findAllByState(State state);

}
