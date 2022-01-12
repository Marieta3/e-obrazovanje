package eobrazovanje.service;

import eobrazovanje.model.Test;

import java.io.IOException;
import java.util.List;

public interface ITestService {

    Test findById(Long id);

    Test save(Test test);

    List<Test> findAll();

    List<Test> findByCourseId(Long courseId);

    void export(Long testId) throws IOException;

}
