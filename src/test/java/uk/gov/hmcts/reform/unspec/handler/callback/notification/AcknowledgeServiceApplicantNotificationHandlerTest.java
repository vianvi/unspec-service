package uk.gov.hmcts.reform.unspec.handler.callback.notification;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.gov.hmcts.reform.unspec.callback.CallbackParams;
import uk.gov.hmcts.reform.unspec.config.properties.notification.NotificationsProperties;
import uk.gov.hmcts.reform.unspec.handler.callback.BaseCallbackHandlerTest;
import uk.gov.hmcts.reform.unspec.model.CaseData;
import uk.gov.hmcts.reform.unspec.sampledata.CallbackParamsBuilder;
import uk.gov.hmcts.reform.unspec.sampledata.CaseDataBuilder;
import uk.gov.hmcts.reform.unspec.service.NotificationService;

import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.hmcts.reform.unspec.callback.CallbackType.ABOUT_TO_SUBMIT;
import static uk.gov.hmcts.reform.unspec.handler.callback.notification.NotificationData.CLAIM_REFERENCE_NUMBER;
import static uk.gov.hmcts.reform.unspec.handler.callback.notification.NotificationData.DEFENDANT_NAME;
import static uk.gov.hmcts.reform.unspec.handler.callback.notification.NotificationData.RESPONSE_DEADLINE;
import static uk.gov.hmcts.reform.unspec.handler.callback.notification.NotificationData.SOLICITOR_REFERENCE;
import static uk.gov.hmcts.reform.unspec.sampledata.CaseDataBuilder.LEGACY_CASE_REFERENCE;

@SpringBootTest(classes = {
    AcknowledgeServiceApplicantNotificationHandler.class,
    JacksonAutoConfiguration.class
})
class AcknowledgeServiceApplicantNotificationHandlerTest extends BaseCallbackHandlerTest {

    @MockBean
    private NotificationService notificationService;
    @MockBean
    private NotificationsProperties notificationsProperties;
    @Autowired
    private AcknowledgeServiceApplicantNotificationHandler handler;

    @Nested
    class AboutToSubmitCallback {

        @BeforeEach
        void setup() {
            when(notificationsProperties.getDefendantSolicitorAcknowledgeService()).thenReturn("template-id");
            when(notificationsProperties.getClaimantSolicitorEmail()).thenReturn("claimantsolicitor@example.com");
            when(notificationsProperties.getDefendantSolicitorEmail()).thenReturn("defendantsolicitor@example.com");
        }

        @Test
        void shouldNotifyClaimantSolicitor_whenInvoked() {
            CaseData caseData = CaseDataBuilder.builder().atStateExtensionRequested().build();
            CallbackParams params = CallbackParamsBuilder.builder().of(ABOUT_TO_SUBMIT, caseData).build();

            handler.handle(params);

            verify(notificationService).sendMail(
                "claimantsolicitor@example.com",
                "template-id",
                getNotificationDataMap(caseData),
                "acknowledge-service-applicant-notification-000LR001"
            );
        }

        @NotNull
        private Map<String, String> getNotificationDataMap(CaseData caseData) {
            return Map.of(
                CLAIM_REFERENCE_NUMBER, LEGACY_CASE_REFERENCE,
                DEFENDANT_NAME, caseData.getRespondent1().getPartyName(),
                SOLICITOR_REFERENCE, "claimant solicitor",
                RESPONSE_DEADLINE, caseData.getRespondentSolicitor1ResponseDeadline().toString()
            );
        }
    }
}
