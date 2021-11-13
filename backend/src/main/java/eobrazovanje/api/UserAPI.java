package eobrazovanje.api;

import eobrazovanje.dto.UserDTO;
import eobrazovanje.model.Role;
import eobrazovanje.model.User;
import eobrazovanje.model.UserRequest;
import eobrazovanje.util.Converter;
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
    public UserDTO FindUserById(@PathVariable("id") Long id){
        return Converter.userToUserDTO(userService.findById(id));
    }

    @GetMapping("/noauth/{id}")
    public UserDTO FindUserById1(@PathVariable("id") Long id){
        return Converter.userToUserDTO(userService.findById(id));
    }

    @GetMapping("/teachers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDTO> FindAllTeachers(){
        return Converter.usersToUserDTOs(userService.findByRole(Role.ROLE_TEACHER));
    }

    @GetMapping("/students")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDTO> FindAllStudents(){
        return Converter.usersToUserDTOs(userService.findByRole(Role.ROLE_STUDENT));
    }
}
