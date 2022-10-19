package com.ergoton.ergoaudit.assemblers.report;

import com.ergoton.ergoaudit.assemblers.project.ProjectPreviewAssembler;
import com.ergoton.ergoaudit.assemblers.user.UserPreviewAssembler;
import com.ergoton.ergoaudit.controllers.ReportController;
import com.ergoton.ergoaudit.model.User;
import com.ergoton.ergoaudit.model.project.Project;
import com.ergoton.ergoaudit.model.report.Report;
import com.ergoton.ergoaudit.model.dto.report.GetReport;
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
public class ReportWithPreviewAssembler implements RepresentationModelAssembler<Report, GetReport> {

    private final ModelMapper modelMapper;
    private final ProjectPreviewAssembler projectPreviewAssembler;
    private final UserPreviewAssembler userPreviewAssembler;

    @NotNull
    @Override
    public final GetReport toModel(@NotNull final Report report) {

        GetReport reportDto = modelMapper.map(report, GetReport.class);

        Link selfRelation = linkTo(methodOn(ReportController.class)
                .getReportById(report.getId()))
                .withRel("self");

        reportDto.add(selfRelation);

        Project project = report.getProject();
        User author = report.getAuthor();

        reportDto.embed("projects", projectPreviewAssembler.toModel(project));
        reportDto.embed("authors", userPreviewAssembler.toModel(author));

        return reportDto;

    }

}

