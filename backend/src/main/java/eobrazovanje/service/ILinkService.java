package eobrazovanje.service;

import eobrazovanje.model.Link;

import java.util.List;

public interface ILinkService {

    Link findById(Long id);

    Link save(Link link);

    List<Link> findAll();
}
