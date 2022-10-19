package com.ergoton.ergoaudit.controllers;

import com.ergoton.ergoaudit.assemblers.report.ReportWithPreviewAssembler;
import com.ergoton.ergoaudit.converters.ProjectIdToProjectConverter;
import com.ergoton.ergoaudit.converters.WalletAddressToUserConverter;
import com.ergoton.ergoaudit.model.report.Report;
import com.ergoton.ergoaudit.model.dto.report.GetReport;
import com.ergoton.ergoaudit.model.dto.report.PostReport;
import com.ergoton.ergoaudit.services.ProjectService;
import com.ergoton.ergoaudit.services.ReportService;
import com.ergoton.ergoaudit.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Tag(name = "Report Controller", description = "This controller provides interfaces to query existing reports and create new ones.")
public class ReportController {

    private final ModelMapper modelMapper;
    private final ReportService reportService;
    private final ReportWithPreviewAssembler reportWithPreviewAssembler;

    public ReportController(ModelMapper modelMapper, ReportService reportService,
                            ReportWithPreviewAssembler reportWithPreviewAssembler,
                            UserService userService, ProjectService projectService) {

        modelMapper.addConverter(new ProjectIdToProjectConverter(projectService));
        modelMapper.addConverter(new WalletAddressToUserConverter(userService));

        TypeMap<PostReport, Report> propertyMapper = modelMapper.createTypeMap(PostReport.class, Report.class);
        propertyMapper.addMappings(mapper -> mapper.skip(Report::setId));

        this.modelMapper = modelMapper;

        this.reportService = reportService;
        this.reportWithPreviewAssembler = reportWithPreviewAssembler;

    }

    @GetMapping(value = "/reports/{reportId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a single report by providing an id"
            , description = "This interface is responds with a single report matching the id.")
    public GetReport getReportById(@PathVariable final String reportId) {

        Report reportWithId = reportService.getReportById(reportId);

        return reportWithPreviewAssembler.toModel(reportWithId);
    }

    @PostMapping(value = "/reports", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Submit a new report"
             , description = "This interface is used to create a new report.")
    public ResponseEntity<?> submitNewReport(@Valid @RequestBody PostReport newReportDto,
                                                     @AuthenticationPrincipal String walletAddress) {

        if (walletAddress.equals(newReportDto.getAuthorWalletAddress())) {

            Report newReport = modelMapper.map(newReportDto, Report.class);
            Report report = reportService.createReport(newReport);
            GetReport reportDto = reportWithPreviewAssembler.toModel(report);
            Optional<GetReport> reportOptional = Optional.of(reportDto);
            return ResponseEntity.of(reportOptional);

        }

        return ResponseEntity.badRequest().build();

    }
}
