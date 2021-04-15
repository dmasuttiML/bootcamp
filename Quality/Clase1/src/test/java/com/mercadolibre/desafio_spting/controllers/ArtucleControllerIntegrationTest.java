package com.mercadolibre.desafio_spting.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.desafio_spting.dtos.ArticleDTO;
import com.mercadolibre.desafio_spting.enums.ArticleFilterTypes;
import com.mercadolibre.desafio_spting.repositories.ArticleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ArtucleControllerIntegrationTest {

    private final String ALL_ARTICLES = "src/test/resources/allArticlesMock.json";
    private final String FILTERED_BY_CATEGORY = "src/test/resources/filterByCategoryMock.json";
    private final String FILTERED_BY_CATEGORY_AND_SHIPPING = "src/test/resources/filterByCategoryAndShipping.json";

    private static final String pathMocks = "src/test/resources/";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private ArticleRepository articleRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Ejercicio 1")
    public void getAllArticlesTest() throws Exception {
        List<ArticleDTO> mock =  objectMapper.readValue(new File(ALL_ARTICLES), new TypeReference<>() {});

        when(articleRepository.getArticles(any(), any())).thenReturn(mock);

        MvcResult mvcResult = mockMvc.perform(get("/articles"))
                                     .andDo(print())
                                     .andExpect(status().isOk())
                                     .andReturn();

        List<ArticleDTO> response = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<>() {});

        Assertions.assertEquals(mock, response);
    }

    @Test
    @DisplayName("Ejercicio 2")
    public void filterByCategoryTest() throws Exception {
        List<ArticleDTO> mock =  objectMapper.readValue(new File(FILTERED_BY_CATEGORY), new TypeReference<>() {});
        List<ArticleDTO> mockFiltered = mock.stream().filter(a -> a.getCategory().equals("Herramientas")).collect(Collectors.toList());

        when(articleRepository.getArticles(any(), any())).thenReturn(mockFiltered);

        MvcResult mvcResult = mockMvc.perform(get("/articles?category=Herramientas"))
                                     .andDo(print())
                                     .andExpect(status().isOk())
                                     .andReturn();

        List<ArticleDTO> response = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<>() {});

        Assertions.assertEquals(mockFiltered, response);
    }

    @Test
    @DisplayName("Ejercicio 3")
    public void filterByCategoryAndShipping() throws Exception {
        List<ArticleDTO> mock =  objectMapper.readValue(new File(FILTERED_BY_CATEGORY_AND_SHIPPING), new TypeReference<>() {});
        List<ArticleDTO> mockFiltered = mock.stream()
                                            .filter(a -> a.getCategory().equals("Herramientas") || a.getFreeShipping() == "SI")
                                            .collect(Collectors.toList());

        when(articleRepository.getArticles(any(), any())).thenReturn(mockFiltered);

        MvcResult mvcResult = mockMvc.perform(get("/articles?category=Herramientas&freeShipping=true"))
                                     .andDo(print())
                                     .andExpect(status().isOk())
                                     .andReturn();

        List<ArticleDTO> response = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<>() {});

        Assertions.assertEquals(mockFiltered, response);
    }

    @Test
    @DisplayName("Ejercicio 4")
    public void orderAlphabeticallyDesc() throws Exception {
        List<ArticleDTO> mock =  objectMapper.readValue(new File(ALL_ARTICLES), new TypeReference<>() {});
        List<ArticleDTO> mockFiltered = mock.stream()
                                            .collect(Collectors.toList());

        when(articleRepository.getArticles(any(), any())).thenReturn(mockFiltered);

        MvcResult mvcResult = mockMvc.perform(get("/articles?order=0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<ArticleDTO> response = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<>() {});

        Assertions.assertEquals(mockFiltered, response);
    }
}
