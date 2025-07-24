package com.example.elasticsearch.controller;

import com.example.elasticsearch.model.MultilingualDocument;
import com.example.elasticsearch.service.MultilingualDocumentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(MultilingualDocumentController.class)
class MultilingualDocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MultilingualDocumentService service;

    @Test
    void testInsert() throws Exception {
        MultilingualDocument doc = new MultilingualDocument("1", Map.of("en", "hello", "fa", "سلام"));
        when(service.insert(any())).thenReturn(doc);

        mockMvc.perform(post("/api/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"identifier\": \"1\", \"body\": {\"en\": \"hello\", \"fa\": \"سلام\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identifier").value("1"));
    }

    @Test
    void testSearch() throws Exception {
        List<MultilingualDocument> results = List.of(
                new MultilingualDocument("1", Map.of("en", "hello")),
                new MultilingualDocument("2", Map.of("fa", "سلام"))
        );

        when(service.search("سلام", 2)).thenReturn(results);

        mockMvc.perform(get("/api/documents/search")
                        .param("text", "سلام")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].identifier").value("1"));
    }
}
