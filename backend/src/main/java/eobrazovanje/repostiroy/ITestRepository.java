package eobrazovanje.repostiroy;

import eobrazovanje.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITestRepository extends JpaRepository<Test,Long> {
}
