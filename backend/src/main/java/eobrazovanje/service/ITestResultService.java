package eobrazovanje.service;

import eobrazovanje.model.TestResult;

import java.util.List;

public interface ITestResultService {

    TestResult findById(Long id);

    TestResult save(TestResult course);

    List<TestResult> findAll();

}
