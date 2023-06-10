package com.example.demo.src.user;


import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/app/users")
public class UserController {


    private final UserService userService;

    private final JwtService jwtService;

}
