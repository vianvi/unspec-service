package uk.gov.hmcts.reform.unspec.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.unspec.enums.CaseState;
import uk.gov.hmcts.reform.unspec.model.CaseData;

import java.util.HashMap;
import java.util.Map;

@Service
public class CaseDetailsConverter {

    private final ObjectMapper objectMapper;

    public CaseDetailsConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public CaseData toCaseData(CaseDetails caseDetails) {
        Map<String, Object> data = new HashMap<>(caseDetails.getData());
        data.put("ccdCaseReference", caseDetails.getId());
        if (caseDetails.getState() != null) {
            data.put("ccdState", CaseState.valueOf(caseDetails.getState()));
        }
        return objectMapper.convertValue(data, CaseData.class);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> toMap(CaseData caseData) {
        return objectMapper.convertValue(caseData, Map.class);
    }
}
