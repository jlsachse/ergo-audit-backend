package com.ergoton.ergoaudit.services;

import com.ergoton.ergoaudit.exceptions.EntityNotFoundException;
import com.ergoton.ergoaudit.model.report.Report;
import com.ergoton.ergoaudit.model.report.State;
import com.ergoton.ergoaudit.repositories.ReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserService userService;

    public Report getReportById(final String reportId) {

        Optional<Report> optionalReport = reportRepository.findById(reportId);
        optionalReport.orElseThrow(() -> new EntityNotFoundException("Report with ID " + reportId + " doesn't exist."));

        return optionalReport.get();

    }

    public final Boolean acceptReport(final String reportId) {
        Report report = this.getReportById(reportId);

        State reportState = report.getState();

        if (reportState != State.PENDING) {
            return Boolean.FALSE;
        }

        report.setState(State.ACCEPTED);
        reportRepository.save(report);

        userService.addExperience(report.getAuthor(), 100);

        return Boolean.TRUE;

    }

    public Report createReport(final Report newReport) {

        newReport.setState(State.PENDING);
        newReport.setDate(LocalDate.now());

        return reportRepository.insert(newReport);
    }

    public ArrayList<Report> listReportsForProject(final String projectId) {
        return reportRepository.findAllByProjectId(projectId);
    }
}
