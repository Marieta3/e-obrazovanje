package eobrazovanje.service.impl;

import eobrazovanje.model.Test;
import eobrazovanje.repostiroy.ITestRepository;
import eobrazovanje.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService implements ITestService {

    @Autowired
    private ITestRepository testRepository;


    @Override
    public Test findById(Long id) {
        return testRepository.findById(id).orElse(null);
    }

    @Override
    public Test save(Test test) {
        return testRepository.save(test);
    }

    @Override
    public List<Test> findByCourseId(Long courseId) {
        return testRepository.findByCourseId(courseId);
    }


}
