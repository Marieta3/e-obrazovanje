package eobrazovanje.service;

import eobrazovanje.model.Test;

import java.util.List;

public interface ITestService {

    Test findById(Long id);

    Test save(Test test);

    List<Test> findAll();
}
