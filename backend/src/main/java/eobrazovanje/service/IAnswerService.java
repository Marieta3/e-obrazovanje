package eobrazovanje.service;

import eobrazovanje.model.Answer;
import eobrazovanje.model.DomainProblem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface IAnswerService {

    Answer findById(Long id);

    Set<Answer> findByIds(ArrayList<Long> ids);

    Answer save(Answer answer);

    List<Answer> findAll();

    DomainProblem getDomainProblemByAnswerId(Long answerId);
}
