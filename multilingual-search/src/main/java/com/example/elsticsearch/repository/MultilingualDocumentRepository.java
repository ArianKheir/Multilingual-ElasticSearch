package com.example.elasticsearch.repository;

import com.example.elasticsearch.model.MultilingualDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing MultilingualDocument entities in Elasticsearch.
 */
@Repository
public interface MultilingualDocumentRepository extends ElasticsearchRepository<MultilingualDocument, String> {
} 