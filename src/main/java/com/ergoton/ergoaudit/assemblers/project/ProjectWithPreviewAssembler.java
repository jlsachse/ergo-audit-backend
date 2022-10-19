package com.ergoton.ergoaudit.assemblers.project;

import com.ergoton.ergoaudit.assemblers.report.ReportPreviewAssembler;
import com.ergoton.ergoaudit.controllers.ProjectController;
import com.ergoton.ergoaudit.model.dto.project.GetProject;
import com.ergoton.ergoaudit.model.project.Project;
import com.ergoton.ergoaudit.model.report.Report;
import com.ergoton.ergoaudit.services.ReportService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@AllArgsConstructor
public class ProjectWithPreviewAssembler implements RepresentationModelAssembler<Project, GetProject> {

    private final ModelMapper modelMapper;
    private final ReportService reportService;
    private final ReportPreviewAssembler reportPreviewAssembler;

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

        ArrayList<Report> reportsForProject = reportService.listReportsForProject(project.getId());

        for (Report report : reportsForProject) {
            projectDto.embed("reports", reportPreviewAssembler.toModel(report));
        }

        return projectDto;

    }

}
