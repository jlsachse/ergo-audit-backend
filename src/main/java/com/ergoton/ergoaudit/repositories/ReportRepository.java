package com.ergoton.ergoaudit.repositories;

import com.ergoton.ergoaudit.model.report.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;

public interface ReportRepository extends MongoRepository<Report, String> {

    @Query(value = "{ 'project.$id': ObjectId('?0') }")
    ArrayList<Report> findAllByProjectId(final String projectId);

}

