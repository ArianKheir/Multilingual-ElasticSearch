package com.example.elasticsearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Map;

/**
 * Represents a multilingual document stored in Elasticsearch.
 * Each document has a unique identifier and a map of language codes to text bodies.
 */
@Document(indexName = "multilingual_documents")
public class MultilingualDocument {
    /**
     * Unique identifier for the document.
     */
    @Id
    private String identifier;

    /**
     * Map of language code (e.g., "en", "fa") to text body.
     */
    private Map<String, String> body;

    /**
     * Concatenated text of all languages for optimized search.
     */
    private String bodyAll;

    public MultilingualDocument() {}

    public MultilingualDocument(String identifier, Map<String, String> body) {
        this.identifier = identifier;
        this.body = body;
        this.bodyAll = concatenateBody(body);
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
        this.bodyAll = concatenateBody(body);
    }

    public String getBodyAll() {
        return bodyAll;
    }

    public void setBodyAll(String bodyAll) {
        this.bodyAll = bodyAll;
    }

    /**
     * Concatenates all language texts into a single string.
     * @param body map of language to text
     * @return concatenated string
     */
    private String concatenateBody(Map<String, String> body) {
        if (body == null) return null;
        return String.join(" ", body.values());
    }
} 