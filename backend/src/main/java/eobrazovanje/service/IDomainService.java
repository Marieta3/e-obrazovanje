package eobrazovanje.service;

import eobrazovanje.model.Domain;

public interface IDomainService {

    Domain findByCourseId(Long course_id);
}
