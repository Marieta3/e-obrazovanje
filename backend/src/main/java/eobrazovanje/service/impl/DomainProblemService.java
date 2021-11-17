package eobrazovanje.service.impl;

import eobrazovanje.model.DomainProblem;
import eobrazovanje.repostiroy.IDomainProblemRepository;
import eobrazovanje.service.IDomainProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainProblemService implements IDomainProblemService {

    @Autowired
    private IDomainProblemRepository domainProblemRepository;

    @Override
    public DomainProblem findById(Long id) {
        return domainProblemRepository.findById(id).orElse(null);
    }

    @Override
    public DomainProblem save(DomainProblem domainProblem) {
        return domainProblemRepository.save(domainProblem);
    }

    @Override
    public List<DomainProblem> findAll() {
        return domainProblemRepository.findAll();
    }
}
