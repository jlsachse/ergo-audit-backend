package com.ergoton.ergoaudit.controllers;
import com.ergoton.ergoaudit.model.webhook.VotingWebhook;
import com.ergoton.ergoaudit.services.VotingWebhookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Tag(name = "Voting Webhook Controller", description = "This controller provides interfaces to query existing users.")
public class VotingWebhookController {

    private final VotingWebhookService votingWebhookService;

    @PostMapping(value = "/votingWebhooks", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Webhook interface for the external voting service"
            , description = "This interface is used to handle voting events from an external voting service.")
    public void updateReportState(@Valid @RequestBody final VotingWebhook votingWebhook) {

        System.out.println("Received webhook post request: " + votingWebhook);
        votingWebhookService.handleVotingWebhook(votingWebhook);

    }


}
