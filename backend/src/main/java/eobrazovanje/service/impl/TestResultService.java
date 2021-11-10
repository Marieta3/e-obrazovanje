package eobrazovanje.service.impl;

import eobrazovanje.model.TestResult;
import eobrazovanje.repostiroy.ITestResultRepository;
import eobrazovanje.service.ITestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestResultService implements ITestResultService {

    @Autowired
    private ITestResultRepository testResultRepository;

    @Override
    public TestResult findById(Long id) {
        return testResultRepository.findById(id).orElse(null);
    }

    @Override
    public TestResult save(TestResult testResult) {
        return testResultRepository.save(testResult);
    }

    @Override
    public List<TestResult> findAll() {
        return testResultRepository.findAll();
    }
}
