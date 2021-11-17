package eobrazovanje.service;

import eobrazovanje.model.DomainProblem;

import java.util.List;

public interface IDomainProblemService {

    DomainProblem findById(Long id);

    DomainProblem save(DomainProblem domainProblem);

    List<DomainProblem> findAll();
}
