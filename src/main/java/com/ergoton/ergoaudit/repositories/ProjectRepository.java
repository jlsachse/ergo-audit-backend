package com.ergoton.ergoaudit.repositories;

import com.ergoton.ergoaudit.model.project.Project;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, String> {

    @Aggregation(pipeline = {
            "{ $match: { '_id': ?0 } }",
            "{ $lookup: { from: 'report', localField: '_id', foreignField: 'project.$id', as: 'reports', pipeline: [{ $match: { state: 'ACCEPTED' } }] } }",
            "{ $unwind: { path: '$reports', preserveNullAndEmptyArrays: true } }",
            "{ $unwind: { path: '$reports.sections', preserveNullAndEmptyArrays: true } }",
            "{ $group: { _id: { _id: '$_id', sectionLabel: '$reports.sections.label' }, sectionLabel: { $first: '$reports.sections.label' }, sum: { $sum: '$reports.sections.percentage' }, count: { $sum: 1 } } }",
            "{ $group: { _id: '$_id._id', averages: { $push: { sectionLabel: '$sectionLabel', percentage: { $divide: [ '$sum', '$count' ] } } } } }",
            "{ $unwind: { path: '$averages' } }",
            "{ $group: { _id: '$_id', scores: { $push: { label: '$averages.sectionLabel', percentage: { $round: [ '$averages.percentage', 0 ] } } }, percentage: { $avg: '$averages.percentage' } } }",
            "{ $merge: { into: 'project', whenMatched: [{ $set: { percentage: { $cond: [{ $and: [{ $gt: [{ $size: '$$new.scores' }, 1 ] }, { $ne: [ '$$new.scores.0.label', null ] } ] }, { $round: [ '$$new.percentage', 0 ] }, '$$REMOVE' ] } } }, { $set: { scores: { $cond: [{ $and: [{ $gt: [{ $size: '$$new.scores' }, 1 ] }, { $ne: [ '$$new.scores.0.label', null ] } ] }, '$$new.scores', '$$REMOVE' ] } } } ] } }"
    })
    List<Project> updateProjectScores(final String projectId);

}
