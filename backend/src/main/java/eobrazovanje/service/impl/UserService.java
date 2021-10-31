package eobrazovanje.service.impl;

import eobrazovanje.model.Authority;
import eobrazovanje.model.User;
import eobrazovanje.model.UserRequest;
import eobrazovanje.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import eobrazovanje.repostiroy.IUserRepository;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authService;


    private IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepo) {
        this.userRepository = userRepo;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    //TODO: popuni
    public User CreateUser(UserRequest userRequest){
        User u = new User();
        u.setUsername(userRequest.getUsername());
        // pre nego sto postavimo lozinku u atribut hesiramo je
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        u.setFirstName(userRequest.getFirstName());
        u.setLastName(userRequest.getLastName());
        u.setEnabled(true);

        List<Authority> auth = authService.findByname("ROLE_USER");
        // u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
        u.setAuthorities(auth);

        u = userRepository.save(u);
        return u;
    }
}
