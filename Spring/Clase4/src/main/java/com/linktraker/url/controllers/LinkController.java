package com.linktraker.url.controllers;

import com.linktraker.url.dto.ErrorDTO;
import com.linktraker.url.dto.LinkDTO;
import com.linktraker.url.exceptions.LinkNotFoundException;
import com.linktraker.url.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class LinkController {

    @Autowired
    private LinkService linkService;

    @PostMapping("/create")
    public ResponseEntity createLink(@RequestBody LinkDTO linkDTO) throws LinkNotFoundException {
        return ResponseEntity.ok(linkService.createLink(linkDTO));
    }

    @GetMapping("/link/{linkId}")
    public void redirect(@PathVariable int linkId, @RequestParam String password, HttpServletResponse response)
            throws LinkNotFoundException, IOException {
        response.sendRedirect(linkService.findLink(linkId, password));
    }

    @GetMapping("/metrics/{linkID}")
    public ResponseEntity statisticLink(@PathVariable int linkID) throws LinkNotFoundException {
        return ResponseEntity.ok(linkService.viewStatisticLink(linkID));
    }

    @PostMapping("/invalidate/{linkID}")
    public ResponseEntity invalidateLink(@PathVariable int linkID) throws LinkNotFoundException {
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(LinkNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(LinkNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO("Link not found", e.getMessage());
        return ResponseEntity.badRequest().body(errorDTO);
    }
}
