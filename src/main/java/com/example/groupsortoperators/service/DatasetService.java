package com.example.groupsortoperators.service;

import com.example.groupsortoperators.entity.DatasetRecordEntity;
import com.example.groupsortoperators.model.DatasetRecord;

import com.example.groupsortoperators.repo.DatasetRecordRepository;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DatasetService {

    @Autowired
    private  DatasetRecordRepository datasetRecordRepository;

    public DatasetRecord insert(String dataset, JsonNode body) {
        DatasetRecordEntity rec = new DatasetRecordEntity();
        rec.setDatasetName(dataset);
        rec.setData(body);
        DatasetRecordEntity saved = datasetRecordRepository.save(rec);
        return DatasetRecord.from(saved);
    }



    public Map<String, List<JsonNode>> groupByAndSort(String dataset, String groupField, List<Sort.Order> sortOrders) {
        List<DatasetRecordEntity> records = datasetRecordRepository.findByDatasetName(dataset);

        Map<String, List<JsonNode>> grouped = new LinkedHashMap<>();
        for (DatasetRecordEntity rec : records) {
            JsonNode data = rec.getData();
            String key = data.has(groupField) ? data.get(groupField).asText() : "undefined";
            grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(data);
        }

        final Comparator<JsonNode> comp = buildComparator(sortOrders);
        if (comp != null) {
            grouped.replaceAll((k, v) -> v.stream().sorted(comp).toList());
        }

        return grouped;
    }

    public List<JsonNode> sortAll(String dataset, List<Sort.Order> sortOrders) {
        List<DatasetRecordEntity> records = datasetRecordRepository.findByDatasetName(dataset);
        List<JsonNode> all = records.stream().map(DatasetRecordEntity::getData).toList();

        final Comparator<JsonNode> comp = buildComparator(sortOrders);
        return (comp != null) ? all.stream().sorted(comp).toList() : all;
    }


    private Comparator<JsonNode> buildComparator(List<Sort.Order> sortOrders) {
        Comparator<JsonNode> comparator = null;

        for (Sort.Order o : sortOrders) {
            Comparator<JsonNode> c = Comparator.comparing(
                    node -> node.has(o.getProperty()) ? node.get(o.getProperty()).asText() : ""
            );
            if (o.getDirection() == Sort.Direction.DESC) c = c.reversed();
            comparator = (comparator == null) ? c : comparator.thenComparing(c);
        }
        return comparator;
    }



}

