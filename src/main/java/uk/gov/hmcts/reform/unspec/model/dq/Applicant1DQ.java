package uk.gov.hmcts.reform.unspec.model.dq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import uk.gov.hmcts.reform.unspec.model.StatementOfTruth;
import uk.gov.hmcts.reform.unspec.model.documents.Document;

@Setter
@Data
@Builder
public class Applicant1DQ implements DQ {

    private final FileDirectionsQuestionnaire applicant1DQFileDirectionsQuestionnaire;
    private final DisclosureOfElectronicDocuments applicant1DQDisclosureOfElectronicDocuments;
    private final String applicant1DQDisclosureOfNonElectronicDocuments;
    private final DisclosureReport applicant1DQDisclosureReport;
    private final Experts applicant1DQExperts;
    private final Witnesses applicant1DQWitnesses;
    private final Hearing applicant1DQHearing;
    private final Document applicant1DQDraftDirections;
    private final RequestedCourt applicant1DQRequestedCourt;
    private final HearingSupport applicant1DQHearingSupport;
    private final FurtherInformation applicant1DQFurtherInformation;
    private final StatementOfTruth applicant1DQStatementOfTruth;

    @Override
    @JsonProperty("applicant1DQFileDirectionsQuestionnaire")
    public FileDirectionsQuestionnaire getFileDirectionQuestionnaire() {
        return applicant1DQFileDirectionsQuestionnaire;
    }

    @Override
    @JsonProperty("applicant1DQDisclosureOfElectronicDocuments")
    public DisclosureOfElectronicDocuments getDisclosureOfElectronicDocuments() {
        return applicant1DQDisclosureOfElectronicDocuments;
    }

    @Override
    @JsonProperty("applicant1DQDisclosureOfNonElectronicDocuments")
    public String getDisclosureOfNonElectronicDocuments() {
        return applicant1DQDisclosureOfNonElectronicDocuments;
    }

    @Override
    @JsonProperty("applicant1DQDisclosureReport")
    public DisclosureReport getDisclosureReport() {
        return applicant1DQDisclosureReport;
    }

    @Override
    @JsonProperty("applicant1DQExperts")
    public Experts getExperts() {
        return applicant1DQExperts;
    }

    @Override
    @JsonProperty("applicant1DQWitnesses")
    public Witnesses getWitnesses() {
        return applicant1DQWitnesses;
    }

    @Override
    @JsonProperty("applicant1DQHearing")
    public Hearing getHearing() {
        return applicant1DQHearing;
    }

    @Override
    @JsonProperty("applicant1DQDraftDirections")
    public Document getDraftDirections() {
        return applicant1DQDraftDirections;
    }

    @Override
    public RequestedCourt getRequestedCourt() {
        return null;
    }

    @Override
    @JsonProperty("applicant1DQHearingSupport")
    public HearingSupport getHearingSupport() {
        return applicant1DQHearingSupport;
    }

    @Override
    @JsonProperty("applicant1DQFurtherInformation")
    public FurtherInformation getFurtherInformation() {
        return applicant1DQFurtherInformation;
    }

    @Override
    @JsonProperty("applicant1DQStatementOfTruth")
    public StatementOfTruth getStatementOfTruth() {
        return applicant1DQStatementOfTruth;
    }
}
