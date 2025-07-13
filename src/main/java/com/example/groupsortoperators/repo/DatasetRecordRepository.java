package com.example.groupsortoperators.repo;

import com.example.groupsortoperators.entity.DatasetRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DatasetRecordRepository extends JpaRepository<DatasetRecordEntity, UUID>  {
    List<DatasetRecordEntity> findByDatasetName(String datasetName);

}
