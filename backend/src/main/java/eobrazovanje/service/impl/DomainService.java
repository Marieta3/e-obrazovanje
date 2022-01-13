package eobrazovanje.service.impl;

import eobrazovanje.model.Domain;
import eobrazovanje.repostiroy.IDomainRepository;
import eobrazovanje.service.IDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainService implements IDomainService {

    @Autowired
    private IDomainRepository domainRepository;

    @Override
    public Domain findByCourseId(Long course_id) {
        return domainRepository.findByCourseId(course_id);
    }
}
