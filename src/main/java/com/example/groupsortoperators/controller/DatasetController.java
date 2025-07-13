package com.example.groupsortoperators.controller;


import com.example.groupsortoperators.model.DatasetRecord;

import com.example.groupsortoperators.repo.DatasetRecordRepository;
import com.example.groupsortoperators.service.DatasetService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/dataset")
@RequiredArgsConstructor
public class DatasetController {

    @Autowired
    private DatasetService service;

    @Autowired
    private DatasetRecordRepository datasetRecordRepository;


    @PostMapping("/{dataset}/record")
    public ResponseEntity<Map<String, Object>> insertRecord(
            @PathVariable String dataset,
            @RequestBody JsonNode body) {

        DatasetRecord saved = service.insert(dataset, body);

        Object recordId = body.has("id")
                ? body.get("id").asText()
                : saved.getId();

        Map<String, Object> response = Map.of(
                "message",  "Record added successfully",
                "dataset",  saved.getDataset(),
                "recordId", recordId
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{dataset}/query")
    public ResponseEntity<Map<String, Object>> queryGroupedSorted(
            @PathVariable String dataset,
            @RequestParam(required = false) String groupBy,
            @RequestParam(required = false, defaultValue = "") List<String> sortBy) {

        List<Sort.Order> sortOrders = sortBy.stream()
                .filter(s -> !s.isBlank())
                .map(s -> {
                    String[] parts = s.split(":", 2);
                    String field = parts[0];
                    Sort.Direction dir = (parts.length > 1 && parts[1].equalsIgnoreCase("desc"))
                            ? Sort.Direction.DESC : Sort.Direction.ASC;
                    return new Sort.Order(dir, field);
                }).toList();

        if (groupBy != null && !groupBy.isBlank()) {
            Map<String, List<JsonNode>> grouped = service.groupByAndSort(dataset, groupBy, sortOrders);
            return ResponseEntity.ok(Map.of("groupedRecords", grouped));
        } else {
            List<JsonNode> sorted = service.sortAll(dataset, sortOrders);
            return ResponseEntity.ok(Map.of("sortedRecords", sorted));
        }
    }




}
