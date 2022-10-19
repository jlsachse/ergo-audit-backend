package com.ergoton.ergoaudit.assemblers.project;

import com.ergoton.ergoaudit.controllers.ProjectController;
import com.ergoton.ergoaudit.model.project.Project;
import com.ergoton.ergoaudit.model.dto.project.GetProject;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@AllArgsConstructor
public class ProjectAssembler implements RepresentationModelAssembler<Project, GetProject> {

    private final ModelMapper modelMapper;

    @NotNull
    @Override
    public final GetProject toModel(@NotNull final Project project) {

        GetProject projectDto = modelMapper.map(project, GetProject.class);

        Link aggregateRoot = linkTo(methodOn(ProjectController.class)
                .getPaginatedProjects(null, null, null))
                .withRel("projects");

        Link selfRelation = linkTo(methodOn(ProjectController.class)
                .getProjectById(project.getId()))
                .withRel("self");

        projectDto.add(selfRelation, aggregateRoot);

        return projectDto;

    }

}
