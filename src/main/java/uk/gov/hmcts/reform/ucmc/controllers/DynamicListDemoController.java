package uk.gov.hmcts.reform.ucmc.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.Api;
import jdk.jfr.DataAmount;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.ccd.client.model.AboutToStartOrSubmitCallbackResponse;
import uk.gov.hmcts.reform.ccd.client.model.CallbackRequest;
import uk.gov.hmcts.reform.ccd.client.model.CallbackResponse;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.SubmittedCallbackResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.lang.String.format;
import static uk.gov.hmcts.reform.ucmc.helpers.DateFormatHelper.DATE_TIME_AT;
import static uk.gov.hmcts.reform.ucmc.helpers.DateFormatHelper.formatLocalDateTime;

@Api
@RestController
@RequestMapping("/dynamic-list")
public class DynamicListDemoController {
    public static final DynamicListElement EMPTY = DynamicListElement.builder().build();

    @Data
    @Builder
    private static class DynamicListElement {
        private final UUID code;
        private final String label;
    }

    @Data
    @Builder
    private static class DynamicList {
        private DynamicListElement value;
        @JsonProperty("list_items")
        private List<DynamicListElement> listItems;
    }

    @PostMapping("/about-to-start")
    public AboutToStartOrSubmitCallbackResponse handleAboutToStart(@RequestBody CallbackRequest callbackrequest) {
        CaseDetails caseDetails = callbackrequest.getCaseDetails();
        Map<String, Object> data = caseDetails.getData();

        List<DynamicListElement> listElements = new ArrayList<>();
        String PBA_1 = "PBA1234567";
        String PBA_2 = "PBA2345678";

        listElements.add(DynamicListElement.builder().code(UUID.randomUUID()).label(PBA_1).build());
        listElements.add(DynamicListElement.builder().code(UUID.randomUUID()).label(PBA_2).build());
        listElements.add(DynamicListElement.builder().code(UUID.fromString("00b3d6d-6e10-4111-86b7-c511e129fa03")).label("OTHER").build());

        data.put("exampleDynamicList", DynamicList.builder().listItems(listElements).value(EMPTY).build());
        data.put("exampleLabel", format("%nPBA 1: %s %nPBA 2: %s", PBA_1, PBA_2));
        data.put("exampleLabel2", format("%nPBA 1: %s %nPBA 2: %s", PBA_1, PBA_2));

        return AboutToStartOrSubmitCallbackResponse.builder().data(data).build();
    }
}
