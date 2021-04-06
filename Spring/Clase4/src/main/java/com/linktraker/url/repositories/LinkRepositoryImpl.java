package com.linktraker.url.repositories;

import com.linktraker.url.dto.LinkDTO;
import com.linktraker.url.exceptions.LinkNotFoundException;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class LinkRepositoryImpl implements LinkRepository {
    private AtomicInteger index = new AtomicInteger();

    private Map<Integer,LinkDTO> mapLinks = new HashMap<>();

    public int saveLink(LinkDTO linkDTO) throws LinkNotFoundException {
        if(!existsLink(linkDTO.getUrl()))
            throw new LinkNotFoundException("Error en formato del link: " + linkDTO.getUrl());

        int auxIndex = index.getAndIncrement();
        mapLinks.put(auxIndex,linkDTO);
        return auxIndex;
    }

    private boolean existsLink(String url){
        try {
            new URL(url).toURI();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public LinkDTO findById(int id) throws LinkNotFoundException {
        LinkDTO linkDTO = mapLinks.get(id);

        if(linkDTO == null)
            throw new LinkNotFoundException("No se encontro el link id: " + id);

        return linkDTO;
    }

    public void deleteById(int id) throws LinkNotFoundException {
        if(mapLinks.remove(id) == null)
            throw  new LinkNotFoundException(String.valueOf(id));
    }

}
