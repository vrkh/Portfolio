package ru.isu.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.isu.portfolio.model.db.Board;
import ru.isu.portfolio.model.db.User;
import ru.isu.portfolio.model.request.LoginRequest;
import ru.isu.portfolio.model.request.SignupRequest;
import ru.isu.portfolio.model.response.JwtResponse;
import ru.isu.portfolio.model.response.MessageResponse;
import ru.isu.portfolio.repository.UserRepository;
import ru.isu.portfolio.security.JwtUtils;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        User user = userRepository.findByUsername(loginRequest.getUsername());

        if (user == null || !encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Неверно введенный логин и/или пароль"));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );


        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                new JwtResponse(
                        jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        roles,
                        userDetails.getTotal()
                )
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        System.out.println("--->SignUp" + signUpRequest);
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Пользователь с данным логином существует"));
        }

        // Create new user's account
        User user = new User(
                signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword())
        );

        user.setRole("ROLE_ADMIN");
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Пользователь успешно зарегистрирован"));
    }
}