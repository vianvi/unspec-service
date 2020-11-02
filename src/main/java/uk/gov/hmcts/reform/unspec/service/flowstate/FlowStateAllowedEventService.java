package uk.gov.hmcts.reform.unspec.service.flowstate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.unspec.callback.CaseEvent;
import uk.gov.hmcts.reform.unspec.model.CaseData;
import uk.gov.hmcts.reform.unspec.stateflow.StateFlow;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static uk.gov.hmcts.reform.unspec.callback.CaseEvent.ACKNOWLEDGE_SERVICE;
import static uk.gov.hmcts.reform.unspec.callback.CaseEvent.CLAIMANT_RESPONSE;
import static uk.gov.hmcts.reform.unspec.callback.CaseEvent.CONFIRM_SERVICE;
import static uk.gov.hmcts.reform.unspec.callback.CaseEvent.CREATE_CLAIM;
import static uk.gov.hmcts.reform.unspec.callback.CaseEvent.DEFENDANT_RESPONSE;
import static uk.gov.hmcts.reform.unspec.callback.CaseEvent.DISCONTINUE_CLAIM;
import static uk.gov.hmcts.reform.unspec.callback.CaseEvent.DISPATCH_BUSINESS_PROCESS;
import static uk.gov.hmcts.reform.unspec.callback.CaseEvent.MOVE_TO_STAYED;
import static uk.gov.hmcts.reform.unspec.callback.CaseEvent.REQUEST_EXTENSION;
import static uk.gov.hmcts.reform.unspec.callback.CaseEvent.RESPOND_EXTENSION;
import static uk.gov.hmcts.reform.unspec.callback.CaseEvent.WITHDRAW_CLAIM;
import static uk.gov.hmcts.reform.unspec.service.flowstate.FlowState.Main.CLAIM_ISSUED;
import static uk.gov.hmcts.reform.unspec.service.flowstate.FlowState.Main.CLAIM_STAYED;
import static uk.gov.hmcts.reform.unspec.service.flowstate.FlowState.Main.DRAFT;
import static uk.gov.hmcts.reform.unspec.service.flowstate.FlowState.Main.EXTENSION_REQUESTED;
import static uk.gov.hmcts.reform.unspec.service.flowstate.FlowState.Main.EXTENSION_RESPONDED;
import static uk.gov.hmcts.reform.unspec.service.flowstate.FlowState.Main.FULL_DEFENCE;
import static uk.gov.hmcts.reform.unspec.service.flowstate.FlowState.Main.RESPONDED_TO_CLAIM;
import static uk.gov.hmcts.reform.unspec.service.flowstate.FlowState.Main.SERVICE_ACKNOWLEDGED;
import static uk.gov.hmcts.reform.unspec.service.flowstate.FlowState.Main.SERVICE_CONFIRMED;

@Service
@RequiredArgsConstructor
public class FlowStateAllowedEventService {

    private final StateFlowEngine stateFlowEngine;

    private static final Map<String, List<CaseEvent>> ALLOWED_EVENTS_ON_FLOW_STATE = Map.of(
        DRAFT.fullName(),
        List.of(CREATE_CLAIM, WITHDRAW_CLAIM, DISCONTINUE_CLAIM, DISPATCH_BUSINESS_PROCESS),

        CLAIM_ISSUED.fullName(),
        List.of(MOVE_TO_STAYED, CONFIRM_SERVICE, WITHDRAW_CLAIM, DISCONTINUE_CLAIM, DISPATCH_BUSINESS_PROCESS),

        CLAIM_STAYED.fullName(),
        List.of(WITHDRAW_CLAIM, DISCONTINUE_CLAIM, DISPATCH_BUSINESS_PROCESS),

        SERVICE_CONFIRMED.fullName(),
        List.of(ACKNOWLEDGE_SERVICE, DEFENDANT_RESPONSE, WITHDRAW_CLAIM, DISCONTINUE_CLAIM, DISPATCH_BUSINESS_PROCESS),

        SERVICE_ACKNOWLEDGED.fullName(),
        List.of(REQUEST_EXTENSION, DEFENDANT_RESPONSE, WITHDRAW_CLAIM, DISCONTINUE_CLAIM, DISPATCH_BUSINESS_PROCESS),

        EXTENSION_REQUESTED.fullName(),
        List.of(DEFENDANT_RESPONSE, RESPOND_EXTENSION, WITHDRAW_CLAIM, DISCONTINUE_CLAIM, DISPATCH_BUSINESS_PROCESS),

        EXTENSION_RESPONDED.fullName(),
        List.of(DEFENDANT_RESPONSE, WITHDRAW_CLAIM, DISCONTINUE_CLAIM, DISPATCH_BUSINESS_PROCESS),

        RESPONDED_TO_CLAIM.fullName(),
        List.of(CLAIMANT_RESPONSE, WITHDRAW_CLAIM, DISCONTINUE_CLAIM, DISPATCH_BUSINESS_PROCESS),

        FULL_DEFENCE.fullName(),
        List.of(WITHDRAW_CLAIM, DISCONTINUE_CLAIM, DISPATCH_BUSINESS_PROCESS)
    );

    public FlowState getFlowState(CaseData caseData) {
        StateFlow stateFlow = stateFlowEngine.evaluate(caseData);
        return FlowState.fromFullName(stateFlow.getState().getName());
    }

    public List<CaseEvent> getAllowedEvents(String stateFullName) {
        return ALLOWED_EVENTS_ON_FLOW_STATE.getOrDefault(stateFullName, emptyList());
    }

    public boolean isAllowedOnState(String stateFullName, CaseEvent caseEvent) {
        return ALLOWED_EVENTS_ON_FLOW_STATE
            .getOrDefault(stateFullName, emptyList())
            .contains(caseEvent);
    }

    public boolean isAllowed(CaseDetails caseDetails, CaseEvent caseEvent) {
        StateFlow stateFlow = stateFlowEngine.evaluate(caseDetails);
        return isAllowedOnState(stateFlow.getState().getName(), caseEvent);
    }

    public List<String> getAllowedStates(CaseEvent caseEvent) {
        return ALLOWED_EVENTS_ON_FLOW_STATE.entrySet().stream()
            .filter(entry -> entry.getValue().contains(caseEvent))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
}
