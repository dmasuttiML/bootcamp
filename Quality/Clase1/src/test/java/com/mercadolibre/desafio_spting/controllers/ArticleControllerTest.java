package com.mercadolibre.desafio_spting.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.desafio_spting.dtos.*;
import com.mercadolibre.desafio_spting.services.ArticleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArticleService articleService;

    @BeforeAll
    static void setUp(){
        //objectMapper = objectMapper.readValue(new File(""), new TypeReference<>(){});
    }

    @Test
    public void getArticlesTest_OK() throws Exception {
        List<ArticleDTO> mockList = new ArrayList<>();
        mockList.add(new ArticleDTO());

        when(articleService.getArticles(any())).thenReturn(mockList);

        MvcResult mvcResult = this.mockMvc.perform(get("/articles"))
                                          .andDo(print())
                                          .andExpect(status().isOk())
                                          .andReturn();
        List<ArticleDTO> responseList = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<>(){});

        Assertions.assertEquals(responseList, mockList);
    }

    @Test
    public void generatePurchase_OK() throws Exception {
        List<ArticleResumeDTO> mockList = new ArrayList<>();
        mockList.add(new ArticleResumeDTO());

        PurchaseDTO purchaseDTO = new PurchaseDTO(mockList);
        TicketDTO ticketDTO = new TicketDTO(1, mockList,2000.0);
        StatusCodeDTO statusCodeDTO = new StatusCodeDTO(200, "");
        PurchaseResponseDTO purchaseResponseDTO = new PurchaseResponseDTO(ticketDTO, statusCodeDTO);

        when(articleService.generatePurchase(any())).thenReturn(purchaseResponseDTO);

        MvcResult mvcResult = this.mockMvc.perform(post("/purchase-request")
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .content(objectMapper.writeValueAsString(purchaseDTO)))
                                          .andDo(print())
                                          .andExpect(status().isOk())
                                          .andReturn();
        var response = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                                                                  new TypeReference<PurchaseResponseDTO>(){});

        Assertions.assertEquals(response, purchaseResponseDTO);
    }
}
