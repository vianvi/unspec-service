package uk.gov.hmcts.reform.unspec.handler.callback;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.unspec.callback.Callback;
import uk.gov.hmcts.reform.unspec.callback.CallbackHandler;
import uk.gov.hmcts.reform.unspec.callback.CaseEvent;

import java.util.List;
import java.util.Map;

import static uk.gov.hmcts.reform.unspec.callback.CallbackType.ABOUT_TO_START;
import static uk.gov.hmcts.reform.unspec.callback.CaseEvent.ADD_DEFENDANT_LITIGATION_FRIEND;

@Service
@RequiredArgsConstructor
public class AddDefendantLitigationFriendCallbackHandler extends CallbackHandler {

    private static final List<CaseEvent> EVENTS = List.of(ADD_DEFENDANT_LITIGATION_FRIEND);

    @Override
    protected Map<String, Callback> callbacks() {
        return Map.of(
            callbackKey(ABOUT_TO_START), this::emptyCallbackResponse
        );
    }

    @Override
    public List<CaseEvent> handledEvents() {
        return EVENTS;
    }
}
