package eobrazovanje.api;

import eobrazovanje.model.User;
import eobrazovanje.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import eobrazovanje.service.IUserService;

@RestController
@RequestMapping("users")
public class UserAPI {
    private IUserService userService;

    @Autowired
    public UserAPI(IUserService userDAO) {
        this.userService = userDAO;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public User FindUserById(@PathVariable("id") Long id){
        return userService.findById(id);
    }
}
