package com.ergoton.ergoaudit.assemblers.report;

import com.ergoton.ergoaudit.controllers.ReportController;
import com.ergoton.ergoaudit.model.dto.report.GetReportPreview;
import com.ergoton.ergoaudit.model.report.Report;
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
public class ReportPreviewAssembler implements RepresentationModelAssembler<Report, GetReportPreview> {

    private final ModelMapper modelMapper;

    @NotNull
    @Override
    public final GetReportPreview toModel(@NotNull final Report report) {

        GetReportPreview reportDto = modelMapper.map(report, GetReportPreview.class);

        Link selfRelation = linkTo(methodOn(ReportController.class)
                .getReportById(report.getId()))
                .withRel("self");

        reportDto.add(selfRelation);

        return reportDto;

    }

}
