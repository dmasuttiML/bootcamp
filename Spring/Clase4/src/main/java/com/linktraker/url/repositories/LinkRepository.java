package com.linktraker.url.repositories;

import com.linktraker.url.dto.LinkDTO;
import com.linktraker.url.exceptions.LinkNotFoundException;

public interface LinkRepository {
    int saveLink(LinkDTO linkDTO) throws LinkNotFoundException;
    LinkDTO findById(int id) throws LinkNotFoundException;
    void deleteById(int id) throws LinkNotFoundException;
}
