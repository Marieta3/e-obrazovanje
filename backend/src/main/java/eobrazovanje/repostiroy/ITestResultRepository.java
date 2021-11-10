package eobrazovanje.repostiroy;

import eobrazovanje.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITestResultRepository  extends JpaRepository<TestResult,Long> {
}
