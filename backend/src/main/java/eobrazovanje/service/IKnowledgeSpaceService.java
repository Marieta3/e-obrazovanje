package eobrazovanje.service;

import eobrazovanje.model.KnowledgeSpace;
import java.util.List;

public interface IKnowledgeSpaceService {

    KnowledgeSpace findById(Long id);

    KnowledgeSpace save(KnowledgeSpace knowledgeSpace);

    List<KnowledgeSpace> findAll();
}
