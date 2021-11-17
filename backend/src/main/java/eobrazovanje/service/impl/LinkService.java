package eobrazovanje.service.impl;

import eobrazovanje.model.Link;
import eobrazovanje.repostiroy.ILinkRepository;
import eobrazovanje.service.ILinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService implements ILinkService {

    @Autowired
    private ILinkRepository linkRepository;

    @Override
    public Link findById(Long id) {
        return linkRepository.findById(id).orElse(null);
    }

    @Override
    public Link save(Link link) {
        return linkRepository.save(link);
    }

    @Override
    public List<Link> findAll() {
        return linkRepository.findAll();
    }
}
