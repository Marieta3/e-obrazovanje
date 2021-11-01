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
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User CreateUser(UserRequest userRequest){
        User u = new User();
        u.setUsername(userRequest.getUsername());
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        u.setFirstName(userRequest.getFirstName());
        u.setLastName(userRequest.getLastName());
        u.setEnabled(true);
        //TODO ishendlati role koje se koriste u sistemu
        List<Authority> auth = authService.findByName("ROLE_STUDENT");
        u.setAuthorities(auth);

        u = userRepository.save(u);
        return u;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
