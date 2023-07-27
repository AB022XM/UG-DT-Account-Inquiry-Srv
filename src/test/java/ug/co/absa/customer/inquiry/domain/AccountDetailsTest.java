package ug.co.absa.customer.inquiry.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import ug.co.absa.customer.inquiry.web.rest.TestUtil;

class AccountDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountDetails.class);
        AccountDetails accountDetails1 = new AccountDetails();
        accountDetails1.setId(UUID.randomUUID());
        AccountDetails accountDetails2 = new AccountDetails();
        accountDetails2.setId(accountDetails1.getId());
        assertThat(accountDetails1).isEqualTo(accountDetails2);
        accountDetails2.setId(UUID.randomUUID());
        assertThat(accountDetails1).isNotEqualTo(accountDetails2);
        accountDetails1.setId(null);
        assertThat(accountDetails1).isNotEqualTo(accountDetails2);
    }
}
