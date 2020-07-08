package uk.gov.hmcts.reform.ucmc.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
public class Applicant {
    private final Type type;
    private final String individualTitle;
    private final String individualFirstName;
    private final String individualLastName;
    private final LocalDate individualDateOfBirth;
    private final String companyName;
    private final String organisationName;
    private final String soleTraderTitle;
    private final String soleTraderFirstName;
    private final String soleTraderLastName;
    private final String soleTraderTradingAs;
    private final LocalDate soleTraderDateOfBirth;
    private final Address applicantAddress;

    public enum Type {
        INDIVIDUAL,
        COMPANY,
        ORGANISATION,
        SOLE_TRADER
    }
}