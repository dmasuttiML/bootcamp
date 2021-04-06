package com.linktraker.url.services;

import com.linktraker.url.dto.LinkDTO;
import com.linktraker.url.exceptions.LinkNotFoundException;
import com.linktraker.url.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    private LinkRepository linkRepository;

    @Override
    public LinkDTO createLink(LinkDTO linkDTO) throws LinkNotFoundException {
        linkDTO.setId(linkRepository.saveLink(linkDTO));
        return linkDTO;
    }

    @Override
    public String findLink(int id, String password) throws LinkNotFoundException {
       LinkDTO linkDTO = linkRepository.findById(id);
       var a = password;
       if(!linkDTO.getPassword().equals(password))
           throw new LinkNotFoundException("Password incorrecta");

       linkRepository.findById(id).increase();
       return linkRepository.findById(id).getUrl();
    }

    @Override
    public int viewStatisticLink(int id) throws LinkNotFoundException {
        return linkRepository.findById(id).getCount();
    }

    @Override
    public void invalidateLink(int id) throws LinkNotFoundException {
        linkRepository.deleteById(id);
    }
}
