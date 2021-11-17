package eobrazovanje.repostiroy;

import eobrazovanje.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILinkRepository extends JpaRepository<Link, Long> {
}
