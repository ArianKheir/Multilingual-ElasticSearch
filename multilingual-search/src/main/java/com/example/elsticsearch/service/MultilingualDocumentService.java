package com.example.elasticsearch.service;

import com.example.elasticsearch.model.MultilingualDocument;
import com.example.elasticsearch.repository.MultilingualDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing multilingual documents in Elasticsearch.
 */
@Service
public class MultilingualDocumentService {
    @Autowired
    private MultilingualDocumentRepository repository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    /**
     * Inserts a multilingual document into Elasticsearch.
     * Throws DuplicateIdentifierException if the identifier already exists.
     * @param document the document to insert
     * @return the saved document
     */
    public MultilingualDocument insert(MultilingualDocument document) {
        if (repository.existsById(document.getIdentifier())) {
            throw new DuplicateKeyException("Document with identifier '" + document.getIdentifier() + "' already exists.");
        }
        // Always update bodyAll before saving
        document.setBodyAll(String.join(" ", document.getBody() != null ? document.getBody().values() : List.of()));
        return repository.save(document);
    }

    /**
     * Searches for documents containing the given text in any language (optimized).
     * @param text the text to search for
     * @param size the maximum number of results
     * @return list of matching documents
     */
    public List<MultilingualDocument> search(String text, int size) {
        // Optimized: search only in bodyAll
        Criteria criteria = new Criteria("bodyAll").contains(text);
        CriteriaQuery query = new CriteriaQuery(criteria);
        query.setPageable(PageRequest.of(0, size));
        SearchHits<MultilingualDocument> hits = elasticsearchOperations.search(query, MultilingualDocument.class);
        List<MultilingualDocument> results = new ArrayList<>();
        for (SearchHit<MultilingualDocument> hit : hits) {
            results.add(hit.getContent());
        }
        return results;
    }
} 