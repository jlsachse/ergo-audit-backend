package com.ergoton.ergoaudit.model.webhook;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VotingWebhook {

    private String reportId;
    private EventType eventType;

}
