package eobrazovanje.service.impl;

import eobrazovanje.model.Question;
import eobrazovanje.repostiroy.IQuestionRepository;
import eobrazovanje.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService implements IQuestionService {

    @Autowired
    private IQuestionRepository questionRepository;
    @Override
    public Question getRandomQuestionForDomainProblemId(Long domainProblemId) {
        return questionRepository.getRandomQuestionForDomainProblemId(domainProblemId);
    }
}
