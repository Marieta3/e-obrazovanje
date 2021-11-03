package eobrazovanje.repostiroy;

import eobrazovanje.model.Role;
import eobrazovanje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUserRepository extends JpaRepository<User,Long> {

    User findUserByUsername(String username);

    @Query("select u from User as u INNER JOIN u.authorities as a WHERE a.role LIKE ?1")
    List<User> findAllByRole(Role role);
}
