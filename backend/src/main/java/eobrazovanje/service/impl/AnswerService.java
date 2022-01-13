package eobrazovanje.service.impl;

import eobrazovanje.model.Answer;
import eobrazovanje.model.DomainProblem;
import eobrazovanje.repostiroy.IAnswerRepository;
import eobrazovanje.service.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AnswerService implements IAnswerService {

    @Autowired
    private IAnswerRepository answerRepository;

    @Override
    public Answer findById(Long id) {
        return answerRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Answer> findByIds(ArrayList<Long> ids) {
        return answerRepository.findByIds(ids);
    }

    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    @Override
    public DomainProblem getDomainProblemByAnswerId(Long answerId) {
        return answerRepository.getDomainProblemByAnswerId(answerId);
    }
}
