package uk.gov.hmcts.reform.unspec.config.properties.notification;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
@Data
public class NotificationsProperties {

    @NotEmpty
    private String govNotifyApiKey;

    @NotEmpty
    private String defendantSolicitorClaimIssueEmailTemplate;

    @NotEmpty
    private String solicitorResponseToCase;

    @NotEmpty
    private String defendantSolicitorAcknowledgeService;

    @NotEmpty
    private String failedPayment;

    @NotEmpty
    private String claimantSolicitorEmail;

    @NotEmpty
    private String defendantSolicitorEmail;
}
