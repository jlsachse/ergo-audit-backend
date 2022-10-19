package com.ergoton.ergoaudit.services;

import com.ergoton.ergoaudit.exceptions.EntityNotFoundException;
import com.ergoton.ergoaudit.model.project.Category;
import com.ergoton.ergoaudit.model.project.Project;
import com.ergoton.ergoaudit.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Page<Project> listProjectsByCategory(final Pageable pageable, final Category category) {

        Project projectWithCategory = Project.builder().category(category).build();
        ExampleMatcher matcher = UntypedExampleMatcher.matching().withIgnoreNullValues();

        return projectRepository.findAll(Example.of(projectWithCategory, matcher), pageable);
    }

    public Iterable<Project> listProjectsByCategory(final Category category) {

        Project projectWithCategory = Project.builder().category(category).build();
        ExampleMatcher matcher = UntypedExampleMatcher.matching().withIgnoreNullValues();

        return projectRepository.findAll(Example.of(projectWithCategory, matcher));
    }

    public Project updateProjectScores(final Project project) {

        String projectId = project.getId();
        projectRepository.updateProjectScores(projectId);

        Optional<Project> optionalProject = projectRepository.findById(projectId);

        optionalProject.orElseThrow(() -> new EntityNotFoundException("Project with ID " + projectId + " doesn't exist."));

        return optionalProject.get();

    }

    public Project getProjectById(final String projectId) {

        Optional<Project> optionalProject = projectRepository.findById(projectId);

        optionalProject.orElseThrow(() -> new EntityNotFoundException("Project with ID " + projectId + " doesn't exist."));

        return optionalProject.get();
    }
}
