package eobrazovanje.api;

import eobrazovanje.exception.ResourceConflictException;
import eobrazovanje.model.*;
import eobrazovanje.security.TokenUtils;
import eobrazovanje.security.auth.JwtAuthenticationRequest;
import eobrazovanje.security.auth.UserTokenState;
import eobrazovanje.service.IUserService;
import eobrazovanje.service.impl.CustomUserDetailsService;
import eobrazovanje.service.impl.UserService;
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
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationAPI{
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                                    HttpServletResponse response) {
        System.out.println("\n\nLOGIN       \n\n");
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));

        // Ubaci korisnika u trenutni security kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();
        Role role = null;

        if (user instanceof Admin) {
            role = Role.ROLE_ADMIN;
        }
        else if (user instanceof Teacher) {
            role = Role.ROLE_TEACHER;
        }
        else if (user instanceof Student){
            role = Role.ROLE_STUDENT;
        }
        System.out.println(role.toString());
        String jwt = tokenUtils.generateToken(user.getUsername(), role.toString());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    // Endpoint za registraciju novog korisnika
//    @PostMapping("/register")
//    public ResponseEntity<User> addUser(@RequestBody UserRequest userRequest, UriComponentsBuilder ucBuilder) {
//
//        User existUser = userService.findByUsername(userRequest.getUsername());
//        if (existUser != null) {
//            throw new ResourceConflictException(userRequest.getId(), "Username already exists");
//        }
//
//        User user = userService.CreateUser(userRequest);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/users/{userId}").buildAndExpand(user.getId()).toUri());
//        return new ResponseEntity<>(user, HttpStatus.CREATED);
//    }

}
