package eobrazovanje.service;

import eobrazovanje.model.Question;

public interface IQuestionService {
    Question getRandomQuestionForDomainProblemId(Long domainProblemId);
}
