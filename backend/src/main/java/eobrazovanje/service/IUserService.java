package eobrazovanje.service;

import eobrazovanje.model.Role;
import eobrazovanje.model.User;
import eobrazovanje.model.UserRequest;

import java.util.List;

public interface IUserService {
    User findByUsername(String username);
    //User CreateUser(UserRequest u);
    User findById(Long id);

    List<User> findByRole(Role role);
}
