package eobrazovanje.service;

import eobrazovanje.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import eobrazovanje.repostiroy.IUserRepository;

@Service
public class UserService implements IUserService{

    private IUserRepository userRepo;

    @Autowired
    public UserService(IUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void CreateUser(User u){
        userRepo.save(u);
    }
}
