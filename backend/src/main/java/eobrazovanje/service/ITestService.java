package eobrazovanje.service;

import eobrazovanje.model.Test;

public interface ITestService {

    Test findById(Long id);

    Test save(Test test);
}
