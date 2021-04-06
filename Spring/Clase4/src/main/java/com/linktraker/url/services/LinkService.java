package com.linktraker.url.services;

import com.linktraker.url.dto.LinkDTO;
import com.linktraker.url.exceptions.LinkNotFoundException;

public interface LinkService {
    LinkDTO createLink(LinkDTO linkDTO) throws LinkNotFoundException;
    String findLink(int id, String password) throws LinkNotFoundException;
    int viewStatisticLink(int id) throws LinkNotFoundException;
    void invalidateLink(int id) throws LinkNotFoundException;
}
