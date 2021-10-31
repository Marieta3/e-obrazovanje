package eobrazovanje.service.impl;

import eobrazovanje.model.Authority;
import eobrazovanje.model.User;
import eobrazovanje.model.UserRequest;
import eobrazovanje.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import eobrazovanje.repostiroy.IUserRepository;

import java.util.List;

@Service
public class UserService implements IUserService {

    private IUserRepository userRepo;

    @Autowired
    public UserService(IUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findUserByUsername(username);
    }

    //TODO: popuni
    public User CreateUser(UserRequest user){
        return null;
    }
}
