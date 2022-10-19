package com.ergoton.ergoaudit.services;

import com.ergoton.ergoaudit.model.project.Project;
import com.ergoton.ergoaudit.model.report.Report;
import com.ergoton.ergoaudit.model.webhook.EventType;
import com.ergoton.ergoaudit.model.webhook.VotingWebhook;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VotingWebhookService {

    private final ReportService reportService;
    private final ProjectService projectService;

    public void handleVotingWebhook(final VotingWebhook votingWebhook) {

        EventType eventType = votingWebhook.getEventType();
        if (eventType != EventType.FINISHED) {
            return;
        }

        // If a voting service was available,
        // then the VotingWebhookService would
        // call the voting services API and
        // resolve the reports state by checking
        // whether the report was accepted or not.

        // Additionally, the voting service could
        // also be used to retire old and out of
        // date reports.

        // Right now, the service will simply accept
        // all reports.

        String reportId = votingWebhook.getReportId();

        Boolean criteriaMeet = Boolean.TRUE;
        if (!criteriaMeet) {
            return;
        }

        Boolean wasAccepted = reportService.acceptReport(reportId);

        if (wasAccepted) {

            Report report = reportService.getReportById(reportId);
            Project project = report.getProject();

            System.out.println("Old project State:\n" + project);

            Project updatedProject = projectService.updateProjectScores(project);

            System.out.println("Old project State:\n" + project);
            System.out.println("New project State:\n" + updatedProject);

        }



    }

}
