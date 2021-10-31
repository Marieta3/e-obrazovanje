package eobrazovanje.service;

import eobrazovanje.model.User;
import eobrazovanje.model.UserRequest;

public interface IUserService {
    User findByUsername(String username);
    User CreateUser(UserRequest u);
}
