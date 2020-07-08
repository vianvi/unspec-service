package uk.gov.hmcts.reform.ucmc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatementOfTruth {
    private final String name;
    private final String role;
}
