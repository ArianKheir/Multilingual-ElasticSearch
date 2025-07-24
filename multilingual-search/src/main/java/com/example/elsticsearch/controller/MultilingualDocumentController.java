package com.example.elasticsearch.controller;

import com.example.elasticsearch.model.MultilingualDocument;
import com.example.elasticsearch.service.MultilingualDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing multilingual documents.
 */
@RestController
@RequestMapping("/api/documents")
public class MultilingualDocumentController {
    @Autowired
    private MultilingualDocumentService service;

    /**
     * Inserts a new multilingual document.
     * @param document the document to insert
     * @return the inserted document
     */
    @PostMapping
    public ResponseEntity<MultilingualDocument> insert(@RequestBody MultilingualDocument document) {
        MultilingualDocument saved = service.insert(document);
        return ResponseEntity.ok(saved);
    }

    /**
     * Searches for documents containing the given text in any language.
     * @param text the text to search for
     * @param size the maximum number of results (optional, default 10)
     * @return list of matching documents
     */
    @GetMapping("/search")
    public ResponseEntity<List<MultilingualDocument>> search(
            @RequestParam("text") String text,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        List<MultilingualDocument> results = service.search(text, size);
        return ResponseEntity.ok(results);
    }

    /**
     * Handles duplicate identifier errors and returns 409 Conflict.
     * @param ex the exception
     * @return error message
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateKeyException(DuplicateKeyException ex) {
        return ex.getMessage();
    }
} 