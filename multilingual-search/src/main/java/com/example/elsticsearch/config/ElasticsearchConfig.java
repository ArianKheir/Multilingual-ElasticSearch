package com.example.elasticsearch.config;

import com.example.elasticsearch.model.MultilingualDocument;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;

/**
 * Configuration to ensure the Elasticsearch index and mapping for multilingual documents
 * are created at runtime if they do not exist.
 */
@Configuration
public class ElasticsearchConfig {
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    /**
     * Ensures the index and mapping exist on application startup.
     */
    @PostConstruct
    public void createIndexAndMappingIfNotExists() {
        IndexOperations indexOps = elasticsearchOperations.indexOps(MultilingualDocument.class);
        if (!indexOps.exists()) {
            indexOps.create();
            // Ensure bodyAll is mapped as text
            var mapping = indexOps.createMapping();
            mapping.put("bodyAll", java.util.Map.of("type", "text"));
            indexOps.putMapping(mapping);
        }
    }
}