package com.example.elasticsearch.service;

import com.example.elasticsearch.model.MultilingualDocument;
import com.example.elasticsearch.repository.MultilingualDocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MultilingualDocumentServiceTest {

    @InjectMocks
    private MultilingualDocumentService service;

    @Mock
    private MultilingualDocumentRepository repository;

    @Mock
    private ElasticsearchOperations elasticsearchOperations;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void insert_shouldSaveWhenIdNotExists() {
        MultilingualDocument doc = new MultilingualDocument("id1", Map.of("en", "hello", "fa", "سلام"));

        when(repository.existsById("id1")).thenReturn(false);
        when(repository.save(any())).thenReturn(doc);

        MultilingualDocument saved = service.insert(doc);

        assertThat(saved).isEqualTo(doc);
        verify(repository).save(any(MultilingualDocument.class));
    }

    @Test
    void insert_shouldThrowWhenIdExists() {
        MultilingualDocument doc = new MultilingualDocument("id1", Map.of("en", "hello"));

        when(repository.existsById("id1")).thenReturn(true);

        assertThatThrownBy(() -> service.insert(doc))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("already exists");
    }
}
