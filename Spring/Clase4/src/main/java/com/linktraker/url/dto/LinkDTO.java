package com.linktraker.url.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkDTO {
    private int id;
    private String url;
    private String password;
    private int count;

    public LinkDTO(String url, String password){
        this.url = url;
        this.count = 0;
    }

    public void increase(){
        this.count++;
    }

}
