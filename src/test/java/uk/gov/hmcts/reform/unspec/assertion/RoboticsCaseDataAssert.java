package uk.gov.hmcts.reform.unspec.assertion;

import uk.gov.hmcts.reform.unspec.model.CaseData;
import uk.gov.hmcts.reform.unspec.model.Party;
import uk.gov.hmcts.reform.unspec.model.robotics.CaseHeader;
import uk.gov.hmcts.reform.unspec.model.robotics.LitigiousParty;
import uk.gov.hmcts.reform.unspec.model.robotics.RoboticsCaseData;
import uk.gov.hmcts.reform.unspec.model.robotics.Solicitor;
import uk.gov.hmcts.reform.unspec.utils.PartyUtils;

import java.util.Optional;

import static uk.gov.hmcts.reform.unspec.assertion.CustomAssertions.assertThat;

public class RoboticsCaseDataAssert extends CustomAssert<RoboticsCaseDataAssert, RoboticsCaseData> {

    public RoboticsCaseDataAssert(RoboticsCaseData actual) {
        super("RoboticsCaseData", actual, RoboticsCaseDataAssert.class);
    }

    public RoboticsCaseDataAssert isEqualTo(CaseData expected) {
        isNotNull();

        CaseHeader header = actual.getHeader();
        compare(
            "caseNumber",
            expected.getLegacyCaseReference(),
            Optional.ofNullable(header.getCaseNumber())
        );
        compare(
            "preferredCourtName",
            expected.getCourtLocation().getApplicantPreferredCourt(),
            Optional.ofNullable(header.getPreferredCourtName())
        );

        compare(
            "amountClaimed",
            expected.getClaimValue().getStatementOfValueInPennies(),
            Optional.ofNullable(actual.getClaimDetails().getAmountClaimed())
        );

        assertParty("applicant1", actual.getLitigiousParties().get(0), expected.getApplicant1());
        assertParty("respondent1", actual.getLitigiousParties().get(1), expected.getRespondent1());

        assertSolicitor(
            "applicant1" + "." + "solicitorPartyReference",
            actual.getSolicitors().get(0),
            expected.getSolicitorReferences().getApplicantSolicitor1Reference()
        );
        assertSolicitor(
            "respondent1" + "." + "solicitorPartyReference",
            actual.getSolicitors().get(1),
            expected.getSolicitorReferences().getRespondentSolicitor1Reference()
        );

        return this;
    }

    private void assertSolicitor(String fieldName, Solicitor solicitor, String reference) {
        compare(
            fieldName,
            solicitor.getSolicitorPartyReference(),
            Optional.ofNullable(reference)
        );
    }

    private void assertParty(String fieldName, LitigiousParty litigiousParty, Party party) {
        if (party == null && litigiousParty != null) {
            failExpectedPresent(fieldName, litigiousParty);
            return;
        }

        if (party != null && litigiousParty == null) {
            failExpectedAbsent(fieldName, party);
            return;
        }

        compare(
            "litigiousPartyName",
            litigiousParty.getLitigiousPartyName(),
            Optional.ofNullable(party.getPartyName())
        );
        compare(
            "litigiousPartyType",
            litigiousParty.getLitigiousPartyType(),
            Optional.ofNullable(party.getType().getDisplayValue())
        );
        compare(
            "litigiousPartyDateOfBirth",
            litigiousParty.getLitigiousPartyDateOfBirth(),
            PartyUtils.getDateOfBirth(party)
        );

        assertThat(litigiousParty.getLitigiousPartyAddresses().getContactAddress())
            .isEqualTo(party.getPrimaryAddress());
    }
}
