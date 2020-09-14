package uk.gov.hmcts.reform.unspec.sampledata;

import uk.gov.hmcts.reform.unspec.model.Address;

public class AddressBuilder {

    public static Address.AddressBuilder builder() {
        return Address.builder()
            .addressLine1("address line 1")
            .addressLine2("address line 2")
            .addressLine3("address line 3")
            .postCode("SW1 1AA")
            .county("London")
            .country("UK");
    }

    public Address build() {
        return builder().build();
    }
}
