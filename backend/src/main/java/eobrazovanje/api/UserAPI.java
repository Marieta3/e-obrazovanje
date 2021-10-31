package eobrazovanje.api;

import eobrazovanje.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import eobrazovanje.service.IUserService;

@RestController
@RequestMapping("api/users")
public class UserAPI {
    private IUserService userService;

    @Autowired
    public UserAPI(IUserService userDAO) {
        this.userService = userDAO;
    }

    @PostMapping
    public void CreateUser(){
        User u = new User();
        u.setFirstName("Name1");
        u.setLastName("Surname1");
        userService.CreateUser(u);
    }
}
