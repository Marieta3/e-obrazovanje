package eobrazovanje.service.impl;

import eobrazovanje.model.KnowledgeSpace;
import eobrazovanje.repostiroy.IKnowledgeSpaceRepository;
import eobrazovanje.service.IKnowledgeSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeSpaceService implements IKnowledgeSpaceService {

    @Autowired
    private IKnowledgeSpaceRepository knowledgeSpaceRepository;

    @Override
    public KnowledgeSpace findById(Long id) {
        return knowledgeSpaceRepository.findById(id).orElse(null);
    }

    @Override
    public KnowledgeSpace save(KnowledgeSpace knowledgeSpace) {
        return knowledgeSpaceRepository.save(knowledgeSpace);
    }

    @Override
    public List<KnowledgeSpace> findAll() {
        return knowledgeSpaceRepository.findAll();
    }

    @Override
    public KnowledgeSpace update(Long id, KnowledgeSpace knowledgeSpace) {
        KnowledgeSpace ks = findById(id);
        ks.setTitle(knowledgeSpace.getTitle());
        ks.setLinks(knowledgeSpace.getLinks());
        return knowledgeSpaceRepository.save(ks);
    }

    @Override
    public List<KnowledgeSpace> findByDomainId(Long domainId) {
        return knowledgeSpaceRepository.findAllByDomainId(domainId);
    }

    @Override
    public KnowledgeSpace getOne(Long ksID) {
        return knowledgeSpaceRepository.getOne(ksID);
    }
}
