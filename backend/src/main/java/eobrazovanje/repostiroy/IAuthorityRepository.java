package eobrazovanje.repostiroy;

import eobrazovanje.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthorityRepository extends JpaRepository<Authority,Long> {
    Authority findByName(String name);
}
