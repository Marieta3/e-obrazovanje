package eobrazovanje.service.impl;

import eobrazovanje.model.Authority;
import eobrazovanje.repostiroy.IAuthorityRepository;
import eobrazovanje.service.IAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityService implements IAuthorityService {
    @Autowired
    private IAuthorityRepository authorityRepository;

    @Override
    public List<Authority> findById(Long id) {
        Authority auth = authorityRepository.getOne(id);
        List<Authority> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }

    @Override
    public List<Authority> findByName(String name) {
        Authority auth = authorityRepository.findByName(name);
        List<Authority> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }
}
