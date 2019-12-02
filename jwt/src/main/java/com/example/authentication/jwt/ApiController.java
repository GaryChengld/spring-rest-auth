package com.example.authentication.jwt;

import com.example.authentication.jwt.domain.User;
import com.example.authentication.jwt.dto.ApiResponse;
import com.example.authentication.jwt.dto.JwtToken;
import com.example.authentication.jwt.dto.Signin;
import com.example.authentication.jwt.repostory.UserRepository;
import com.example.authentication.jwt.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Gary Cheng
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/welcome")
    public ApiResponse home() {
        return new ApiResponse("Public Api");
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtToken> authorize(@Valid @RequestBody Signin signin) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signin.getUsername(), signin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        return new ResponseEntity<>(new JwtToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ApiResponse admin() {
        return new ApiResponse("Admin Api");
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ApiResponse user() {
        return new ApiResponse("User Api");
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("@customUserDetailsService.canAccessUser(principal, #username)")
    public ResponseEntity<User> findUser(@PathVariable("username") String username) {
        User user = this.userRepository.findByUsername(username);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
}
