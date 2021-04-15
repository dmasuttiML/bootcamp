package com.mercadolibre.desafio_spting.services;

import com.mercadolibre.desafio_spting.dtos.ArticleDTO;
import com.mercadolibre.desafio_spting.repositories.ArticleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ArticleServiceTest {
   /* @Mock
    ArticleRepository articleRepository;

    ArticleService articleService

    @BeforeEach
    void setUp(){

    }

    @Test
    @DisplayName("Hola")
    void getArticlesTest() {
        List<ArticleDTO> list = new ArrayList<>();
        list.add(new ArticleDTO());

        when(articleRepository.getArticles(any(), any())).thenReturn(list);

        List<ArticleDTO> resultList = articleService.getArticles(null);

        verify(articleRepository, atLeast(1));

        Assertions.assertEquals(list, resultList);
    }*/
}