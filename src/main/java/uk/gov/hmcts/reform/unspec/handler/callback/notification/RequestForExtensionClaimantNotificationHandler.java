package uk.gov.hmcts.reform.unspec.handler.callback.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.ccd.client.model.AboutToStartOrSubmitCallbackResponse;
import uk.gov.hmcts.reform.ccd.client.model.CallbackResponse;
import uk.gov.hmcts.reform.unspec.callback.Callback;
import uk.gov.hmcts.reform.unspec.callback.CallbackHandler;
import uk.gov.hmcts.reform.unspec.callback.CallbackParams;
import uk.gov.hmcts.reform.unspec.callback.CallbackType;
import uk.gov.hmcts.reform.unspec.callback.CaseEvent;
import uk.gov.hmcts.reform.unspec.config.properties.notification.NotificationsProperties;
import uk.gov.hmcts.reform.unspec.helpers.CaseDetailsConverter;
import uk.gov.hmcts.reform.unspec.model.CaseData;
import uk.gov.hmcts.reform.unspec.service.NotificationService;

import java.util.List;
import java.util.Map;

import static uk.gov.hmcts.reform.unspec.callback.CaseEvent.NOTIFY_APPLICANT_SOLICITOR1_FOR_REQUEST_FOR_EXTENSION;
import static uk.gov.hmcts.reform.unspec.handler.callback.notification.NotificationData.CLAIM_REFERENCE_NUMBER;
import static uk.gov.hmcts.reform.unspec.handler.callback.notification.NotificationData.SOLICITOR_NAME;

@Service
@RequiredArgsConstructor
public class RequestForExtensionClaimantNotificationHandler extends CallbackHandler {

    private static final List<CaseEvent> EVENTS = List.of(NOTIFY_APPLICANT_SOLICITOR1_FOR_REQUEST_FOR_EXTENSION);
    public static final String NOTIFY_APPLICANT_SOLICITOR1_FOR_REQUEST_FOR_EXTENSION_TASK_ID =
        "NotifyClaimantSolicitorForRequestForExtension";
    private static final String REFERENCE_TEMPLATE = "request-for-extension-claimant-notification-%s";

    private final NotificationService notificationService;
    private final NotificationsProperties notificationsProperties;
    private final CaseDetailsConverter caseDetailsConverter;

    @Override
    protected Map<CallbackType, Callback> callbacks() {
        return Map.of(
            CallbackType.ABOUT_TO_SUBMIT, this::notifyClaimantSolicitorForRequestForExtension
        );
    }

    @Override
    public String camundaActivityId() {
        return NOTIFY_APPLICANT_SOLICITOR1_FOR_REQUEST_FOR_EXTENSION_TASK_ID;
    }

    @Override
    public List<CaseEvent> handledEvents() {
        return EVENTS;
    }

    private CallbackResponse notifyClaimantSolicitorForRequestForExtension(CallbackParams callbackParams) {
        CaseData caseData = caseDetailsConverter.toCaseData(callbackParams.getRequest().getCaseDetails());

        notificationService.sendMail(
            "claimant-solicitor@example.com",
            notificationsProperties.getSolicitorResponseToCase(),
            getNotificationProperties(caseData),
            String.format(REFERENCE_TEMPLATE, caseData.getLegacyCaseReference())
        );
        return AboutToStartOrSubmitCallbackResponse.builder().build();
    }

    private Map<String, String> getNotificationProperties(CaseData caseData) {
        return Map.of(
            CLAIM_REFERENCE_NUMBER, caseData.getLegacyCaseReference(),
            SOLICITOR_NAME, caseData.getSolicitorReferences().getApplicantSolicitor1Reference()
        );
    }
}