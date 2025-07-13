package com.example.groupsortoperators.model;


import com.example.groupsortoperators.entity.DatasetRecordEntity;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class DatasetRecord {
    private UUID id;
    private String dataset;
    private Instant createdAt;

    public static DatasetRecord from(DatasetRecordEntity entity) {
        DatasetRecord dto = new DatasetRecord();
        dto.setId(entity.getId());
        dto.setDataset(entity.getDatasetName());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
