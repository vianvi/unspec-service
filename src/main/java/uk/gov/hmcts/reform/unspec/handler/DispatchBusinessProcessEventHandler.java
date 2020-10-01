package uk.gov.hmcts.reform.unspec.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.unspec.event.DispatchBusinessProcessEvent;
import uk.gov.hmcts.reform.unspec.model.BusinessProcess;
import uk.gov.hmcts.reform.unspec.service.CoreCaseDataService;

import java.util.Map;

import static uk.gov.hmcts.reform.unspec.callback.CaseEvent.DISPATCH_BUSINESS_PROCESS;
import static uk.gov.hmcts.reform.unspec.model.BusinessProcessStatus.DISPATCHED;
import static uk.gov.hmcts.reform.unspec.model.BusinessProcessStatus.READY;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispatchBusinessProcessEventHandler {

    private final CoreCaseDataService coreCaseDataService;

    @EventListener
    public void dispatchBusinessProcess(DispatchBusinessProcessEvent event) {
        BusinessProcess businessProcess = event.getBusinessProcess();
        if (businessProcess.getStatus() == READY) {
            coreCaseDataService.triggerEvent(
                event.getCaseId(),
                DISPATCH_BUSINESS_PROCESS,
                Map.of("businessProcess", businessProcess.toBuilder().status(DISPATCHED).build())
            );
        }
    }
}
