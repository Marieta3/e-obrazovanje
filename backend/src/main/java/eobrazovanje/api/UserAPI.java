package eobrazovanje.api;

import eobrazovanje.model.Role;
import eobrazovanje.model.User;
import eobrazovanje.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import eobrazovanje.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/users")
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

    @GetMapping("/noauth/{id}")
    public User FindUserById1(@PathVariable("id") Long id){
        return userService.findById(id);
    }

    @GetMapping("/teachers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> FindAllTeachers(){
        return userService.findByRole(Role.ROLE_TEACHER);
    }

    @GetMapping("/students")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> FindAllStudents(){
        return userService.findByRole(Role.ROLE_STUDENT);
    }
}
