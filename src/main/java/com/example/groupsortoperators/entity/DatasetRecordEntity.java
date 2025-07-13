package com.example.groupsortoperators.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "dataset_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatasetRecordEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "dataset_name", nullable = false)
    private String datasetName;


    @Type(JsonType.class)
    @Column(columnDefinition = "JSON", nullable = false)
    private JsonNode data;

    @CreationTimestamp
    private Instant createdAt;
}
