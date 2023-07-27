package ug.co.absa.customer.inquiry.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ug.co.absa.customer.inquiry.web.rest.TestUtil.sameInstant;
import static ug.co.absa.customer.inquiry.web.rest.TestUtil.sameNumber;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ug.co.absa.customer.inquiry.IntegrationTest;
import ug.co.absa.customer.inquiry.domain.AccountDetails;
import ug.co.absa.customer.inquiry.repository.AccountDetailsRepository;

/**
 * Integration tests for the {@link AccountDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccountDetailsResourceIT {

    private static final String DEFAULT_ACCOUNT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_LIFECYCLE_STATUS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_LIFECYCLE_STATUS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCRUAL_STATUS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACCRUAL_STATUS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CASA_ACCOUNT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_CASA_ACCOUNT_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_MINOR_ACCOUNT_STATUS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MINOR_ACCOUNT_STATUS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TITLE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ADVANCE_AGAINST_UNCLEARED_FUNDS = new BigDecimal(1);
    private static final BigDecimal UPDATED_ADVANCE_AGAINST_UNCLEARED_FUNDS = new BigDecimal(2);

    private static final Boolean DEFAULT_AD_HOC_STATEMENT_FLAG = false;
    private static final Boolean UPDATED_AD_HOC_STATEMENT_FLAG = true;

    private static final Boolean DEFAULT_ADDITIONAL_ADDRESS_FLAG = false;
    private static final Boolean UPDATED_ADDITIONAL_ADDRESS_FLAG = true;

    private static final Boolean DEFAULT_ATM_FACILITY_FLAG = false;
    private static final Boolean UPDATED_ATM_FACILITY_FLAG = true;

    private static final BigDecimal DEFAULT_CHECK_REORDER_THRESHOLD_NUMBER = new BigDecimal(1);
    private static final BigDecimal UPDATED_CHECK_REORDER_THRESHOLD_NUMBER = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DEFERRED_STMT_GENERATION_DAY_OF_MONTH = new BigDecimal(1);
    private static final BigDecimal UPDATED_DEFERRED_STMT_GENERATION_DAY_OF_MONTH = new BigDecimal(2);

    private static final Boolean DEFAULT_GENERATE_RATE_CHANGE_INTIMATION_FLAG = false;
    private static final Boolean UPDATED_GENERATE_RATE_CHANGE_INTIMATION_FLAG = true;

    private static final String DEFAULT_GROUP_BONUS_INTERES_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_BONUS_INTERES_FLAG = "BBBBBBBBBB";

    private static final String DEFAULT_HOLD_MAIL_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_HOLD_MAIL_FLAG = "BBBBBBBBBB";

    private static final String DEFAULT_INTEREST_WAIVER_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_INTEREST_WAIVER_FLAG = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INTERNET_BANKING_ACCESS_FLAG = false;
    private static final Boolean UPDATED_INTERNET_BANKING_ACCESS_FLAG = true;

    private static final Boolean DEFAULT_INWARD_DIRECT_DEBIT_AUTHORIZATION_FLAG = false;
    private static final Boolean UPDATED_INWARD_DIRECT_DEBIT_AUTHORIZATION_FLAG = true;

    private static final Boolean DEFAULT_JOINT_ACCOUNT_FLAG = false;
    private static final Boolean UPDATED_JOINT_ACCOUNT_FLAG = true;

    private static final Boolean DEFAULT_LEAD_DAYS_INTIMATION = false;
    private static final Boolean UPDATED_LEAD_DAYS_INTIMATION = true;

    private static final Boolean DEFAULT_MAIL_ADDRESS_CONTROL_FLAG = false;
    private static final Boolean UPDATED_MAIL_ADDRESS_CONTROL_FLAG = true;

    private static final Boolean DEFAULT_MOBILE_FACILITY_FLAG = false;
    private static final Boolean UPDATED_MOBILE_FACILITY_FLAG = true;

    private static final Integer DEFAULT_NUMBER_OF_CHECK_WITHDRAWALS = 1;
    private static final Integer UPDATED_NUMBER_OF_CHECK_WITHDRAWALS = 2;

    private static final Integer DEFAULT_NUMBER_OF_PAST_DUE_CHECKS = 1;
    private static final Integer UPDATED_NUMBER_OF_PAST_DUE_CHECKS = 2;

    private static final Integer DEFAULT_NUMBER_OF_STATEMENT_COPIES = 1;
    private static final Integer UPDATED_NUMBER_OF_STATEMENT_COPIES = 2;

    private static final Integer DEFAULT_OVERDRAFT_LIMIT_AMOUNT = 1;
    private static final Integer UPDATED_OVERDRAFT_LIMIT_AMOUNT = 2;

    private static final Boolean DEFAULT_POINT_OF_SALE_FACILITY_FLAG = false;
    private static final Boolean UPDATED_POINT_OF_SALE_FACILITY_FLAG = true;

    private static final Boolean DEFAULT_STANDING_INSTRUCTION_FLAG = false;
    private static final Boolean UPDATED_STANDING_INSTRUCTION_FLAG = true;

    private static final Boolean DEFAULT_SWEEPOUT_INSTRUCTION_FLAG = false;
    private static final Boolean UPDATED_SWEEPOUT_INSTRUCTION_FLAG = true;

    private static final BigDecimal DEFAULT_AVAILABLE_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_AVAILABLE_BALANCE = new BigDecimal(2);

    private static final Integer DEFAULT_BRANCH_CODE = 1;
    private static final Integer UPDATED_BRANCH_CODE = 2;

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_SHORT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREDITS_MONTH_TILL_DATE_AMOUNT = 1;
    private static final Integer UPDATED_CREDITS_MONTH_TILL_DATE_AMOUNT = 2;

    private static final Integer DEFAULT_CURRENT_BALANCE = 1;
    private static final Integer UPDATED_CURRENT_BALANCE = 2;

    private static final String DEFAULT_CUSTOMER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ABSA_CUSTOMER = false;
    private static final Boolean UPDATED_IS_ABSA_CUSTOMER = true;

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ACCOUNT_OPENING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACCOUNT_OPENING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_STATEMENT_PERIOD_START_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(0L),
        ZoneOffset.UTC
    );
    private static final ZonedDateTime UPDATED_STATEMENT_PERIOD_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_STATEMENT_PERIOD_END_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(0L),
        ZoneOffset.UTC
    );
    private static final ZonedDateTime UPDATED_STATEMENT_PERIOD_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DEBITS_LAST_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DEBITS_LAST_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CREDIT_LAST_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREDIT_LAST_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DEBITS_MONTH_TILL_DATE_AMOUNT = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(0L),
        ZoneOffset.UTC
    );
    private static final ZonedDateTime UPDATED_DEBITS_MONTH_TILL_DATE_AMOUNT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_DEBITS_YEAR_TILL_DATE_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DEBITS_YEAR_TILL_DATE_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CREDIT_INTEREST_ACCRUED_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_CREDIT_INTEREST_ACCRUED_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DEBIT_INTEREST_ACCRUED_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DEBIT_INTEREST_ACCRUED_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ADJUSTED_CREDIT_INTEREST_ACCRUED = new BigDecimal(1);
    private static final BigDecimal UPDATED_ADJUSTED_CREDIT_INTEREST_ACCRUED = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ADJUSTED_DEBIT_INTEREST_ACCRUED = new BigDecimal(1);
    private static final BigDecimal UPDATED_ADJUSTED_DEBIT_INTEREST_ACCRUED = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PROJECTED_TAX_ON_ACCRUED_INTEREST_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROJECTED_TAX_ON_ACCRUED_INTEREST_AMOUNT = new BigDecimal(2);

    private static final Integer DEFAULT_INTEREST_ACCRUED_IN_CURRENT_FINANCIAL_YEAR = 1;
    private static final Integer UPDATED_INTEREST_ACCRUED_IN_CURRENT_FINANCIAL_YEAR = 2;

    private static final String DEFAULT_ISSUED_INVENTORY_PROPERTY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ISSUED_INVENTORY_PROPERTY_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LAST_ISSUED_CHECK_NUMBER = 1;
    private static final Integer UPDATED_LAST_ISSUED_CHECK_NUMBER = 2;

    private static final String DEFAULT_LANGUAGE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LINE_NUMBER = 1;
    private static final Integer UPDATED_LINE_NUMBER = 2;

    private static final BigDecimal DEFAULT_MINIMUM_REQUIRED_BALANCE_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MINIMUM_REQUIRED_BALANCE_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MINIMUM_REQUIRED_TRADING_BALANCE_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MINIMUM_REQUIRED_TRADING_BALANCE_AMOUNT = new BigDecimal(2);

    private static final Integer DEFAULT_MTD_CREDITS_COUNT = 1;
    private static final Integer UPDATED_MTD_CREDITS_COUNT = 2;

    private static final Integer DEFAULT_MTD_DEBITS_COUNT = 1;
    private static final Integer UPDATED_MTD_DEBITS_COUNT = 2;

    private static final Integer DEFAULT_NET_AVAILABLE_BALANCE_FOR_WITHDRAWAL = 1;
    private static final Integer UPDATED_NET_AVAILABLE_BALANCE_FOR_WITHDRAWAL = 2;

    private static final Integer DEFAULT_NET_BALANCE_AMOUNT = 1;
    private static final Integer UPDATED_NET_BALANCE_AMOUNT = 2;

    private static final Integer DEFAULT_PASSBOOK_LIFECYCLE_STATUS_CODE = 1;
    private static final Integer UPDATED_PASSBOOK_LIFECYCLE_STATUS_CODE = 2;

    private static final BigDecimal DEFAULT_PERIODIC_AVERAGE_BALANCE_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PERIODIC_AVERAGE_BALANCE_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PREVIOUS_DAY_CLOSING_BOOK_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PREVIOUS_DAY_CLOSING_BOOK_BALANCE = new BigDecimal(2);

    private static final Integer DEFAULT_PRODUCT_CODE = 1;
    private static final Integer UPDATED_PRODUCT_CODE = 2;

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RESTRICTED_ACCOUNT_FLAG = false;
    private static final Boolean UPDATED_RESTRICTED_ACCOUNT_FLAG = true;

    private static final Boolean DEFAULT_STAFF_FLAG = false;
    private static final Boolean UPDATED_STAFF_FLAG = true;

    private static final Integer DEFAULT_SWEEPIN_AMOUNT_ON_LIEN = 1;
    private static final Integer UPDATED_SWEEPIN_AMOUNT_ON_LIEN = 2;

    private static final Integer DEFAULT_TAX_CODE_1 = 1;
    private static final Integer UPDATED_TAX_CODE_1 = 2;

    private static final Integer DEFAULT_TAX_CODE_2 = 1;
    private static final Integer UPDATED_TAX_CODE_2 = 2;

    private static final Integer DEFAULT_TDS_EXEMPTION_LIMIT_AMOUNT_1 = 1;
    private static final Integer UPDATED_TDS_EXEMPTION_LIMIT_AMOUNT_1 = 2;

    private static final Integer DEFAULT_TDS_EXEMPTION_LIMIT_AMOUNT_2 = 1;
    private static final Integer UPDATED_TDS_EXEMPTION_LIMIT_AMOUNT_2 = 2;

    private static final BigDecimal DEFAULT_TOTAL_CASA_HOLD_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_CASA_HOLD_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_UNCLEAR_FUND_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_UNCLEAR_FUND_AMOUNT = new BigDecimal(2);

    private static final Integer DEFAULT_YTD_CREDIT_LAST_AMOUNT = 1;
    private static final Integer UPDATED_YTD_CREDIT_LAST_AMOUNT = 2;

    private static final Integer DEFAULT_YTD_CREDITS_COUNT = 1;
    private static final Integer UPDATED_YTD_CREDITS_COUNT = 2;

    private static final Integer DEFAULT_YTD_DEBITS_COUNT = 1;
    private static final Integer UPDATED_YTD_DEBITS_COUNT = 2;

    private static final Integer DEFAULT_YTD_DEBITS_LAST_AMOUNT = 1;
    private static final Integer UPDATED_YTD_DEBITS_LAST_AMOUNT = 2;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE_INFO = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_3 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_4 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_4 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_5 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_5 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_6 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_6 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_7 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_7 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_8 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_8 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_9 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_9 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_10 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_10 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_11 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_11 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_12 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_12 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_13 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_13 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_14 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_14 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_15 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_15 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_16 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_16 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_17 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_17 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_18 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_18 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_19 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_19 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_20 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_20 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_21 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_21 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_22 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_22 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_23 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_23 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_24 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_24 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_25 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_25 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_26 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_26 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_27 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_27 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_28 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_28 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_29 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_29 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_30 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_30 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_FREE_TEXT_FIELD_31 = "AAAAAAAAAA";
    private static final String UPDATED_CUST_FREE_TEXT_FIELD_31 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/account-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AccountDetailsRepository accountDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountDetailsMockMvc;

    private AccountDetails accountDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountDetails createEntity(EntityManager em) {
        AccountDetails accountDetails = new AccountDetails()
            .accountCurrency(DEFAULT_ACCOUNT_CURRENCY)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .accountStatus(DEFAULT_ACCOUNT_STATUS)
            .accountLifecycleStatusCode(DEFAULT_ACCOUNT_LIFECYCLE_STATUS_CODE)
            .accrualStatusCode(DEFAULT_ACCRUAL_STATUS_CODE)
            .casaAccountStatus(DEFAULT_CASA_ACCOUNT_STATUS)
            .minorAccountStatusCode(DEFAULT_MINOR_ACCOUNT_STATUS_CODE)
            .accountTitle(DEFAULT_ACCOUNT_TITLE)
            .advanceAgainstUnclearedFunds(DEFAULT_ADVANCE_AGAINST_UNCLEARED_FUNDS)
            .adHocStatementFlag(DEFAULT_AD_HOC_STATEMENT_FLAG)
            .additionalAddressFlag(DEFAULT_ADDITIONAL_ADDRESS_FLAG)
            .atmFacilityFlag(DEFAULT_ATM_FACILITY_FLAG)
            .checkReorderThresholdNumber(DEFAULT_CHECK_REORDER_THRESHOLD_NUMBER)
            .deferredStmtGenerationDayOfMonth(DEFAULT_DEFERRED_STMT_GENERATION_DAY_OF_MONTH)
            .generateRateChangeIntimationFlag(DEFAULT_GENERATE_RATE_CHANGE_INTIMATION_FLAG)
            .groupBonusInteresFlag(DEFAULT_GROUP_BONUS_INTERES_FLAG)
            .holdMailFlag(DEFAULT_HOLD_MAIL_FLAG)
            .interestWaiverFlag(DEFAULT_INTEREST_WAIVER_FLAG)
            .internetBankingAccessFlag(DEFAULT_INTERNET_BANKING_ACCESS_FLAG)
            .inwardDirectDebitAuthorizationFlag(DEFAULT_INWARD_DIRECT_DEBIT_AUTHORIZATION_FLAG)
            .jointAccountFlag(DEFAULT_JOINT_ACCOUNT_FLAG)
            .leadDaysIntimation(DEFAULT_LEAD_DAYS_INTIMATION)
            .mailAddressControlFlag(DEFAULT_MAIL_ADDRESS_CONTROL_FLAG)
            .mobileFacilityFlag(DEFAULT_MOBILE_FACILITY_FLAG)
            .numberOfCheckWithdrawals(DEFAULT_NUMBER_OF_CHECK_WITHDRAWALS)
            .numberOfPastDueChecks(DEFAULT_NUMBER_OF_PAST_DUE_CHECKS)
            .numberOfStatementCopies(DEFAULT_NUMBER_OF_STATEMENT_COPIES)
            .overdraftLimitAmount(DEFAULT_OVERDRAFT_LIMIT_AMOUNT)
            .pointOfSaleFacilityFlag(DEFAULT_POINT_OF_SALE_FACILITY_FLAG)
            .standingInstructionFlag(DEFAULT_STANDING_INSTRUCTION_FLAG)
            .sweepoutInstructionFlag(DEFAULT_SWEEPOUT_INSTRUCTION_FLAG)
            .availableBalance(DEFAULT_AVAILABLE_BALANCE)
            .branchCode(DEFAULT_BRANCH_CODE)
            .branchName(DEFAULT_BRANCH_NAME)
            .branchShortName(DEFAULT_BRANCH_SHORT_NAME)
            .creditsMonthTillDateAmount(DEFAULT_CREDITS_MONTH_TILL_DATE_AMOUNT)
            .currentBalance(DEFAULT_CURRENT_BALANCE)
            .customerNumber(DEFAULT_CUSTOMER_NUMBER)
            .isAbsaCustomer(DEFAULT_IS_ABSA_CUSTOMER)
            .fullName(DEFAULT_FULL_NAME)
            .accountOpeningDate(DEFAULT_ACCOUNT_OPENING_DATE)
            .statementPeriodStartDate(DEFAULT_STATEMENT_PERIOD_START_DATE)
            .statementPeriodEndDate(DEFAULT_STATEMENT_PERIOD_END_DATE)
            .debitsLastDate(DEFAULT_DEBITS_LAST_DATE)
            .creditLastDate(DEFAULT_CREDIT_LAST_DATE)
            .debitsMonthTillDateAmount(DEFAULT_DEBITS_MONTH_TILL_DATE_AMOUNT)
            .debitsYearTillDateAmount(DEFAULT_DEBITS_YEAR_TILL_DATE_AMOUNT)
            .creditInterestAccruedAmount(DEFAULT_CREDIT_INTEREST_ACCRUED_AMOUNT)
            .debitInterestAccruedAmount(DEFAULT_DEBIT_INTEREST_ACCRUED_AMOUNT)
            .adjustedCreditInterestAccrued(DEFAULT_ADJUSTED_CREDIT_INTEREST_ACCRUED)
            .adjustedDebitInterestAccrued(DEFAULT_ADJUSTED_DEBIT_INTEREST_ACCRUED)
            .projectedTaxOnAccruedInterestAmount(DEFAULT_PROJECTED_TAX_ON_ACCRUED_INTEREST_AMOUNT)
            .interestAccruedInCurrentFinancialYear(DEFAULT_INTEREST_ACCRUED_IN_CURRENT_FINANCIAL_YEAR)
            .issuedInventoryPropertyType(DEFAULT_ISSUED_INVENTORY_PROPERTY_TYPE)
            .lastIssuedCheckNumber(DEFAULT_LAST_ISSUED_CHECK_NUMBER)
            .languageCode(DEFAULT_LANGUAGE_CODE)
            .lineNumber(DEFAULT_LINE_NUMBER)
            .minimumRequiredBalanceAmount(DEFAULT_MINIMUM_REQUIRED_BALANCE_AMOUNT)
            .minimumRequiredTradingBalanceAmount(DEFAULT_MINIMUM_REQUIRED_TRADING_BALANCE_AMOUNT)
            .mtdCreditsCount(DEFAULT_MTD_CREDITS_COUNT)
            .mtdDebitsCount(DEFAULT_MTD_DEBITS_COUNT)
            .netAvailableBalanceForWithdrawal(DEFAULT_NET_AVAILABLE_BALANCE_FOR_WITHDRAWAL)
            .netBalanceAmount(DEFAULT_NET_BALANCE_AMOUNT)
            .passbookLifecycleStatusCode(DEFAULT_PASSBOOK_LIFECYCLE_STATUS_CODE)
            .periodicAverageBalanceAmount(DEFAULT_PERIODIC_AVERAGE_BALANCE_AMOUNT)
            .previousDayClosingBookBalance(DEFAULT_PREVIOUS_DAY_CLOSING_BOOK_BALANCE)
            .productCode(DEFAULT_PRODUCT_CODE)
            .productName(DEFAULT_PRODUCT_NAME)
            .restrictedAccountFlag(DEFAULT_RESTRICTED_ACCOUNT_FLAG)
            .staffFlag(DEFAULT_STAFF_FLAG)
            .sweepinAmountOnLien(DEFAULT_SWEEPIN_AMOUNT_ON_LIEN)
            .taxCode1(DEFAULT_TAX_CODE_1)
            .taxCode2(DEFAULT_TAX_CODE_2)
            .tdsExemptionLimitAmount1(DEFAULT_TDS_EXEMPTION_LIMIT_AMOUNT_1)
            .tdsExemptionLimitAmount2(DEFAULT_TDS_EXEMPTION_LIMIT_AMOUNT_2)
            .totalCASAHoldAmount(DEFAULT_TOTAL_CASA_HOLD_AMOUNT)
            .totalUnclearFundAmount(DEFAULT_TOTAL_UNCLEAR_FUND_AMOUNT)
            .ytdCreditLastAmount(DEFAULT_YTD_CREDIT_LAST_AMOUNT)
            .ytdCreditsCount(DEFAULT_YTD_CREDITS_COUNT)
            .ytdDebitsCount(DEFAULT_YTD_DEBITS_COUNT)
            .ytdDebitsLastAmount(DEFAULT_YTD_DEBITS_LAST_AMOUNT)
            .message(DEFAULT_MESSAGE)
            .sourceInfo(DEFAULT_SOURCE_INFO)
            .status(DEFAULT_STATUS)
            .custFreeTextField1(DEFAULT_CUST_FREE_TEXT_FIELD_1)
            .custFreeTextField2(DEFAULT_CUST_FREE_TEXT_FIELD_2)
            .custFreeTextField3(DEFAULT_CUST_FREE_TEXT_FIELD_3)
            .custFreeTextField4(DEFAULT_CUST_FREE_TEXT_FIELD_4)
            .custFreeTextField5(DEFAULT_CUST_FREE_TEXT_FIELD_5)
            .custFreeTextField6(DEFAULT_CUST_FREE_TEXT_FIELD_6)
            .custFreeTextField7(DEFAULT_CUST_FREE_TEXT_FIELD_7)
            .custFreeTextField8(DEFAULT_CUST_FREE_TEXT_FIELD_8)
            .custFreeTextField9(DEFAULT_CUST_FREE_TEXT_FIELD_9)
            .custFreeTextField10(DEFAULT_CUST_FREE_TEXT_FIELD_10)
            .custFreeTextField11(DEFAULT_CUST_FREE_TEXT_FIELD_11)
            .custFreeTextField12(DEFAULT_CUST_FREE_TEXT_FIELD_12)
            .custFreeTextField13(DEFAULT_CUST_FREE_TEXT_FIELD_13)
            .custFreeTextField14(DEFAULT_CUST_FREE_TEXT_FIELD_14)
            .custFreeTextField15(DEFAULT_CUST_FREE_TEXT_FIELD_15)
            .custFreeTextField16(DEFAULT_CUST_FREE_TEXT_FIELD_16)
            .custFreeTextField17(DEFAULT_CUST_FREE_TEXT_FIELD_17)
            .custFreeTextField18(DEFAULT_CUST_FREE_TEXT_FIELD_18)
            .custFreeTextField19(DEFAULT_CUST_FREE_TEXT_FIELD_19)
            .custFreeTextField20(DEFAULT_CUST_FREE_TEXT_FIELD_20)
            .custFreeTextField21(DEFAULT_CUST_FREE_TEXT_FIELD_21)
            .custFreeTextField22(DEFAULT_CUST_FREE_TEXT_FIELD_22)
            .custFreeTextField23(DEFAULT_CUST_FREE_TEXT_FIELD_23)
            .custFreeTextField24(DEFAULT_CUST_FREE_TEXT_FIELD_24)
            .custFreeTextField25(DEFAULT_CUST_FREE_TEXT_FIELD_25)
            .custFreeTextField26(DEFAULT_CUST_FREE_TEXT_FIELD_26)
            .custFreeTextField27(DEFAULT_CUST_FREE_TEXT_FIELD_27)
            .custFreeTextField28(DEFAULT_CUST_FREE_TEXT_FIELD_28)
            .custFreeTextField29(DEFAULT_CUST_FREE_TEXT_FIELD_29)
            .custFreeTextField30(DEFAULT_CUST_FREE_TEXT_FIELD_30)
            .custFreeTextField31(DEFAULT_CUST_FREE_TEXT_FIELD_31);
        return accountDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountDetails createUpdatedEntity(EntityManager em) {
        AccountDetails accountDetails = new AccountDetails()
            .accountCurrency(UPDATED_ACCOUNT_CURRENCY)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountStatus(UPDATED_ACCOUNT_STATUS)
            .accountLifecycleStatusCode(UPDATED_ACCOUNT_LIFECYCLE_STATUS_CODE)
            .accrualStatusCode(UPDATED_ACCRUAL_STATUS_CODE)
            .casaAccountStatus(UPDATED_CASA_ACCOUNT_STATUS)
            .minorAccountStatusCode(UPDATED_MINOR_ACCOUNT_STATUS_CODE)
            .accountTitle(UPDATED_ACCOUNT_TITLE)
            .advanceAgainstUnclearedFunds(UPDATED_ADVANCE_AGAINST_UNCLEARED_FUNDS)
            .adHocStatementFlag(UPDATED_AD_HOC_STATEMENT_FLAG)
            .additionalAddressFlag(UPDATED_ADDITIONAL_ADDRESS_FLAG)
            .atmFacilityFlag(UPDATED_ATM_FACILITY_FLAG)
            .checkReorderThresholdNumber(UPDATED_CHECK_REORDER_THRESHOLD_NUMBER)
            .deferredStmtGenerationDayOfMonth(UPDATED_DEFERRED_STMT_GENERATION_DAY_OF_MONTH)
            .generateRateChangeIntimationFlag(UPDATED_GENERATE_RATE_CHANGE_INTIMATION_FLAG)
            .groupBonusInteresFlag(UPDATED_GROUP_BONUS_INTERES_FLAG)
            .holdMailFlag(UPDATED_HOLD_MAIL_FLAG)
            .interestWaiverFlag(UPDATED_INTEREST_WAIVER_FLAG)
            .internetBankingAccessFlag(UPDATED_INTERNET_BANKING_ACCESS_FLAG)
            .inwardDirectDebitAuthorizationFlag(UPDATED_INWARD_DIRECT_DEBIT_AUTHORIZATION_FLAG)
            .jointAccountFlag(UPDATED_JOINT_ACCOUNT_FLAG)
            .leadDaysIntimation(UPDATED_LEAD_DAYS_INTIMATION)
            .mailAddressControlFlag(UPDATED_MAIL_ADDRESS_CONTROL_FLAG)
            .mobileFacilityFlag(UPDATED_MOBILE_FACILITY_FLAG)
            .numberOfCheckWithdrawals(UPDATED_NUMBER_OF_CHECK_WITHDRAWALS)
            .numberOfPastDueChecks(UPDATED_NUMBER_OF_PAST_DUE_CHECKS)
            .numberOfStatementCopies(UPDATED_NUMBER_OF_STATEMENT_COPIES)
            .overdraftLimitAmount(UPDATED_OVERDRAFT_LIMIT_AMOUNT)
            .pointOfSaleFacilityFlag(UPDATED_POINT_OF_SALE_FACILITY_FLAG)
            .standingInstructionFlag(UPDATED_STANDING_INSTRUCTION_FLAG)
            .sweepoutInstructionFlag(UPDATED_SWEEPOUT_INSTRUCTION_FLAG)
            .availableBalance(UPDATED_AVAILABLE_BALANCE)
            .branchCode(UPDATED_BRANCH_CODE)
            .branchName(UPDATED_BRANCH_NAME)
            .branchShortName(UPDATED_BRANCH_SHORT_NAME)
            .creditsMonthTillDateAmount(UPDATED_CREDITS_MONTH_TILL_DATE_AMOUNT)
            .currentBalance(UPDATED_CURRENT_BALANCE)
            .customerNumber(UPDATED_CUSTOMER_NUMBER)
            .isAbsaCustomer(UPDATED_IS_ABSA_CUSTOMER)
            .fullName(UPDATED_FULL_NAME)
            .accountOpeningDate(UPDATED_ACCOUNT_OPENING_DATE)
            .statementPeriodStartDate(UPDATED_STATEMENT_PERIOD_START_DATE)
            .statementPeriodEndDate(UPDATED_STATEMENT_PERIOD_END_DATE)
            .debitsLastDate(UPDATED_DEBITS_LAST_DATE)
            .creditLastDate(UPDATED_CREDIT_LAST_DATE)
            .debitsMonthTillDateAmount(UPDATED_DEBITS_MONTH_TILL_DATE_AMOUNT)
            .debitsYearTillDateAmount(UPDATED_DEBITS_YEAR_TILL_DATE_AMOUNT)
            .creditInterestAccruedAmount(UPDATED_CREDIT_INTEREST_ACCRUED_AMOUNT)
            .debitInterestAccruedAmount(UPDATED_DEBIT_INTEREST_ACCRUED_AMOUNT)
            .adjustedCreditInterestAccrued(UPDATED_ADJUSTED_CREDIT_INTEREST_ACCRUED)
            .adjustedDebitInterestAccrued(UPDATED_ADJUSTED_DEBIT_INTEREST_ACCRUED)
            .projectedTaxOnAccruedInterestAmount(UPDATED_PROJECTED_TAX_ON_ACCRUED_INTEREST_AMOUNT)
            .interestAccruedInCurrentFinancialYear(UPDATED_INTEREST_ACCRUED_IN_CURRENT_FINANCIAL_YEAR)
            .issuedInventoryPropertyType(UPDATED_ISSUED_INVENTORY_PROPERTY_TYPE)
            .lastIssuedCheckNumber(UPDATED_LAST_ISSUED_CHECK_NUMBER)
            .languageCode(UPDATED_LANGUAGE_CODE)
            .lineNumber(UPDATED_LINE_NUMBER)
            .minimumRequiredBalanceAmount(UPDATED_MINIMUM_REQUIRED_BALANCE_AMOUNT)
            .minimumRequiredTradingBalanceAmount(UPDATED_MINIMUM_REQUIRED_TRADING_BALANCE_AMOUNT)
            .mtdCreditsCount(UPDATED_MTD_CREDITS_COUNT)
            .mtdDebitsCount(UPDATED_MTD_DEBITS_COUNT)
            .netAvailableBalanceForWithdrawal(UPDATED_NET_AVAILABLE_BALANCE_FOR_WITHDRAWAL)
            .netBalanceAmount(UPDATED_NET_BALANCE_AMOUNT)
            .passbookLifecycleStatusCode(UPDATED_PASSBOOK_LIFECYCLE_STATUS_CODE)
            .periodicAverageBalanceAmount(UPDATED_PERIODIC_AVERAGE_BALANCE_AMOUNT)
            .previousDayClosingBookBalance(UPDATED_PREVIOUS_DAY_CLOSING_BOOK_BALANCE)
            .productCode(UPDATED_PRODUCT_CODE)
            .productName(UPDATED_PRODUCT_NAME)
            .restrictedAccountFlag(UPDATED_RESTRICTED_ACCOUNT_FLAG)
            .staffFlag(UPDATED_STAFF_FLAG)
            .sweepinAmountOnLien(UPDATED_SWEEPIN_AMOUNT_ON_LIEN)
            .taxCode1(UPDATED_TAX_CODE_1)
            .taxCode2(UPDATED_TAX_CODE_2)
            .tdsExemptionLimitAmount1(UPDATED_TDS_EXEMPTION_LIMIT_AMOUNT_1)
            .tdsExemptionLimitAmount2(UPDATED_TDS_EXEMPTION_LIMIT_AMOUNT_2)
            .totalCASAHoldAmount(UPDATED_TOTAL_CASA_HOLD_AMOUNT)
            .totalUnclearFundAmount(UPDATED_TOTAL_UNCLEAR_FUND_AMOUNT)
            .ytdCreditLastAmount(UPDATED_YTD_CREDIT_LAST_AMOUNT)
            .ytdCreditsCount(UPDATED_YTD_CREDITS_COUNT)
            .ytdDebitsCount(UPDATED_YTD_DEBITS_COUNT)
            .ytdDebitsLastAmount(UPDATED_YTD_DEBITS_LAST_AMOUNT)
            .message(UPDATED_MESSAGE)
            .sourceInfo(UPDATED_SOURCE_INFO)
            .status(UPDATED_STATUS)
            .custFreeTextField1(UPDATED_CUST_FREE_TEXT_FIELD_1)
            .custFreeTextField2(UPDATED_CUST_FREE_TEXT_FIELD_2)
            .custFreeTextField3(UPDATED_CUST_FREE_TEXT_FIELD_3)
            .custFreeTextField4(UPDATED_CUST_FREE_TEXT_FIELD_4)
            .custFreeTextField5(UPDATED_CUST_FREE_TEXT_FIELD_5)
            .custFreeTextField6(UPDATED_CUST_FREE_TEXT_FIELD_6)
            .custFreeTextField7(UPDATED_CUST_FREE_TEXT_FIELD_7)
            .custFreeTextField8(UPDATED_CUST_FREE_TEXT_FIELD_8)
            .custFreeTextField9(UPDATED_CUST_FREE_TEXT_FIELD_9)
            .custFreeTextField10(UPDATED_CUST_FREE_TEXT_FIELD_10)
            .custFreeTextField11(UPDATED_CUST_FREE_TEXT_FIELD_11)
            .custFreeTextField12(UPDATED_CUST_FREE_TEXT_FIELD_12)
            .custFreeTextField13(UPDATED_CUST_FREE_TEXT_FIELD_13)
            .custFreeTextField14(UPDATED_CUST_FREE_TEXT_FIELD_14)
            .custFreeTextField15(UPDATED_CUST_FREE_TEXT_FIELD_15)
            .custFreeTextField16(UPDATED_CUST_FREE_TEXT_FIELD_16)
            .custFreeTextField17(UPDATED_CUST_FREE_TEXT_FIELD_17)
            .custFreeTextField18(UPDATED_CUST_FREE_TEXT_FIELD_18)
            .custFreeTextField19(UPDATED_CUST_FREE_TEXT_FIELD_19)
            .custFreeTextField20(UPDATED_CUST_FREE_TEXT_FIELD_20)
            .custFreeTextField21(UPDATED_CUST_FREE_TEXT_FIELD_21)
            .custFreeTextField22(UPDATED_CUST_FREE_TEXT_FIELD_22)
            .custFreeTextField23(UPDATED_CUST_FREE_TEXT_FIELD_23)
            .custFreeTextField24(UPDATED_CUST_FREE_TEXT_FIELD_24)
            .custFreeTextField25(UPDATED_CUST_FREE_TEXT_FIELD_25)
            .custFreeTextField26(UPDATED_CUST_FREE_TEXT_FIELD_26)
            .custFreeTextField27(UPDATED_CUST_FREE_TEXT_FIELD_27)
            .custFreeTextField28(UPDATED_CUST_FREE_TEXT_FIELD_28)
            .custFreeTextField29(UPDATED_CUST_FREE_TEXT_FIELD_29)
            .custFreeTextField30(UPDATED_CUST_FREE_TEXT_FIELD_30)
            .custFreeTextField31(UPDATED_CUST_FREE_TEXT_FIELD_31);
        return accountDetails;
    }

    @BeforeEach
    public void initTest() {
        accountDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createAccountDetails() throws Exception {
        int databaseSizeBeforeCreate = accountDetailsRepository.findAll().size();
        // Create the AccountDetails
        restAccountDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountDetails))
            )
            .andExpect(status().isCreated());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        AccountDetails testAccountDetails = accountDetailsList.get(accountDetailsList.size() - 1);
        assertThat(testAccountDetails.getAccountCurrency()).isEqualTo(DEFAULT_ACCOUNT_CURRENCY);
        assertThat(testAccountDetails.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testAccountDetails.getAccountStatus()).isEqualTo(DEFAULT_ACCOUNT_STATUS);
        assertThat(testAccountDetails.getAccountLifecycleStatusCode()).isEqualTo(DEFAULT_ACCOUNT_LIFECYCLE_STATUS_CODE);
        assertThat(testAccountDetails.getAccrualStatusCode()).isEqualTo(DEFAULT_ACCRUAL_STATUS_CODE);
        assertThat(testAccountDetails.getCasaAccountStatus()).isEqualTo(DEFAULT_CASA_ACCOUNT_STATUS);
        assertThat(testAccountDetails.getMinorAccountStatusCode()).isEqualTo(DEFAULT_MINOR_ACCOUNT_STATUS_CODE);
        assertThat(testAccountDetails.getAccountTitle()).isEqualTo(DEFAULT_ACCOUNT_TITLE);
        assertThat(testAccountDetails.getAdvanceAgainstUnclearedFunds()).isEqualByComparingTo(DEFAULT_ADVANCE_AGAINST_UNCLEARED_FUNDS);
        assertThat(testAccountDetails.getAdHocStatementFlag()).isEqualTo(DEFAULT_AD_HOC_STATEMENT_FLAG);
        assertThat(testAccountDetails.getAdditionalAddressFlag()).isEqualTo(DEFAULT_ADDITIONAL_ADDRESS_FLAG);
        assertThat(testAccountDetails.getAtmFacilityFlag()).isEqualTo(DEFAULT_ATM_FACILITY_FLAG);
        assertThat(testAccountDetails.getCheckReorderThresholdNumber()).isEqualByComparingTo(DEFAULT_CHECK_REORDER_THRESHOLD_NUMBER);
        assertThat(testAccountDetails.getDeferredStmtGenerationDayOfMonth())
            .isEqualByComparingTo(DEFAULT_DEFERRED_STMT_GENERATION_DAY_OF_MONTH);
        assertThat(testAccountDetails.getGenerateRateChangeIntimationFlag()).isEqualTo(DEFAULT_GENERATE_RATE_CHANGE_INTIMATION_FLAG);
        assertThat(testAccountDetails.getGroupBonusInteresFlag()).isEqualTo(DEFAULT_GROUP_BONUS_INTERES_FLAG);
        assertThat(testAccountDetails.getHoldMailFlag()).isEqualTo(DEFAULT_HOLD_MAIL_FLAG);
        assertThat(testAccountDetails.getInterestWaiverFlag()).isEqualTo(DEFAULT_INTEREST_WAIVER_FLAG);
        assertThat(testAccountDetails.getInternetBankingAccessFlag()).isEqualTo(DEFAULT_INTERNET_BANKING_ACCESS_FLAG);
        assertThat(testAccountDetails.getInwardDirectDebitAuthorizationFlag()).isEqualTo(DEFAULT_INWARD_DIRECT_DEBIT_AUTHORIZATION_FLAG);
        assertThat(testAccountDetails.getJointAccountFlag()).isEqualTo(DEFAULT_JOINT_ACCOUNT_FLAG);
        assertThat(testAccountDetails.getLeadDaysIntimation()).isEqualTo(DEFAULT_LEAD_DAYS_INTIMATION);
        assertThat(testAccountDetails.getMailAddressControlFlag()).isEqualTo(DEFAULT_MAIL_ADDRESS_CONTROL_FLAG);
        assertThat(testAccountDetails.getMobileFacilityFlag()).isEqualTo(DEFAULT_MOBILE_FACILITY_FLAG);
        assertThat(testAccountDetails.getNumberOfCheckWithdrawals()).isEqualTo(DEFAULT_NUMBER_OF_CHECK_WITHDRAWALS);
        assertThat(testAccountDetails.getNumberOfPastDueChecks()).isEqualTo(DEFAULT_NUMBER_OF_PAST_DUE_CHECKS);
        assertThat(testAccountDetails.getNumberOfStatementCopies()).isEqualTo(DEFAULT_NUMBER_OF_STATEMENT_COPIES);
        assertThat(testAccountDetails.getOverdraftLimitAmount()).isEqualTo(DEFAULT_OVERDRAFT_LIMIT_AMOUNT);
        assertThat(testAccountDetails.getPointOfSaleFacilityFlag()).isEqualTo(DEFAULT_POINT_OF_SALE_FACILITY_FLAG);
        assertThat(testAccountDetails.getStandingInstructionFlag()).isEqualTo(DEFAULT_STANDING_INSTRUCTION_FLAG);
        assertThat(testAccountDetails.getSweepoutInstructionFlag()).isEqualTo(DEFAULT_SWEEPOUT_INSTRUCTION_FLAG);
        assertThat(testAccountDetails.getAvailableBalance()).isEqualByComparingTo(DEFAULT_AVAILABLE_BALANCE);
        assertThat(testAccountDetails.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testAccountDetails.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testAccountDetails.getBranchShortName()).isEqualTo(DEFAULT_BRANCH_SHORT_NAME);
        assertThat(testAccountDetails.getCreditsMonthTillDateAmount()).isEqualTo(DEFAULT_CREDITS_MONTH_TILL_DATE_AMOUNT);
        assertThat(testAccountDetails.getCurrentBalance()).isEqualTo(DEFAULT_CURRENT_BALANCE);
        assertThat(testAccountDetails.getCustomerNumber()).isEqualTo(DEFAULT_CUSTOMER_NUMBER);
        assertThat(testAccountDetails.getIsAbsaCustomer()).isEqualTo(DEFAULT_IS_ABSA_CUSTOMER);
        assertThat(testAccountDetails.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testAccountDetails.getAccountOpeningDate()).isEqualTo(DEFAULT_ACCOUNT_OPENING_DATE);
        assertThat(testAccountDetails.getStatementPeriodStartDate()).isEqualTo(DEFAULT_STATEMENT_PERIOD_START_DATE);
        assertThat(testAccountDetails.getStatementPeriodEndDate()).isEqualTo(DEFAULT_STATEMENT_PERIOD_END_DATE);
        assertThat(testAccountDetails.getDebitsLastDate()).isEqualTo(DEFAULT_DEBITS_LAST_DATE);
        assertThat(testAccountDetails.getCreditLastDate()).isEqualTo(DEFAULT_CREDIT_LAST_DATE);
        assertThat(testAccountDetails.getDebitsMonthTillDateAmount()).isEqualTo(DEFAULT_DEBITS_MONTH_TILL_DATE_AMOUNT);
        assertThat(testAccountDetails.getDebitsYearTillDateAmount()).isEqualByComparingTo(DEFAULT_DEBITS_YEAR_TILL_DATE_AMOUNT);
        assertThat(testAccountDetails.getCreditInterestAccruedAmount()).isEqualByComparingTo(DEFAULT_CREDIT_INTEREST_ACCRUED_AMOUNT);
        assertThat(testAccountDetails.getDebitInterestAccruedAmount()).isEqualByComparingTo(DEFAULT_DEBIT_INTEREST_ACCRUED_AMOUNT);
        assertThat(testAccountDetails.getAdjustedCreditInterestAccrued()).isEqualByComparingTo(DEFAULT_ADJUSTED_CREDIT_INTEREST_ACCRUED);
        assertThat(testAccountDetails.getAdjustedDebitInterestAccrued()).isEqualByComparingTo(DEFAULT_ADJUSTED_DEBIT_INTEREST_ACCRUED);
        assertThat(testAccountDetails.getProjectedTaxOnAccruedInterestAmount())
            .isEqualByComparingTo(DEFAULT_PROJECTED_TAX_ON_ACCRUED_INTEREST_AMOUNT);
        assertThat(testAccountDetails.getInterestAccruedInCurrentFinancialYear())
            .isEqualTo(DEFAULT_INTEREST_ACCRUED_IN_CURRENT_FINANCIAL_YEAR);
        assertThat(testAccountDetails.getIssuedInventoryPropertyType()).isEqualTo(DEFAULT_ISSUED_INVENTORY_PROPERTY_TYPE);
        assertThat(testAccountDetails.getLastIssuedCheckNumber()).isEqualTo(DEFAULT_LAST_ISSUED_CHECK_NUMBER);
        assertThat(testAccountDetails.getLanguageCode()).isEqualTo(DEFAULT_LANGUAGE_CODE);
        assertThat(testAccountDetails.getLineNumber()).isEqualTo(DEFAULT_LINE_NUMBER);
        assertThat(testAccountDetails.getMinimumRequiredBalanceAmount()).isEqualByComparingTo(DEFAULT_MINIMUM_REQUIRED_BALANCE_AMOUNT);
        assertThat(testAccountDetails.getMinimumRequiredTradingBalanceAmount())
            .isEqualByComparingTo(DEFAULT_MINIMUM_REQUIRED_TRADING_BALANCE_AMOUNT);
        assertThat(testAccountDetails.getMtdCreditsCount()).isEqualTo(DEFAULT_MTD_CREDITS_COUNT);
        assertThat(testAccountDetails.getMtdDebitsCount()).isEqualTo(DEFAULT_MTD_DEBITS_COUNT);
        assertThat(testAccountDetails.getNetAvailableBalanceForWithdrawal()).isEqualTo(DEFAULT_NET_AVAILABLE_BALANCE_FOR_WITHDRAWAL);
        assertThat(testAccountDetails.getNetBalanceAmount()).isEqualTo(DEFAULT_NET_BALANCE_AMOUNT);
        assertThat(testAccountDetails.getPassbookLifecycleStatusCode()).isEqualTo(DEFAULT_PASSBOOK_LIFECYCLE_STATUS_CODE);
        assertThat(testAccountDetails.getPeriodicAverageBalanceAmount()).isEqualByComparingTo(DEFAULT_PERIODIC_AVERAGE_BALANCE_AMOUNT);
        assertThat(testAccountDetails.getPreviousDayClosingBookBalance()).isEqualByComparingTo(DEFAULT_PREVIOUS_DAY_CLOSING_BOOK_BALANCE);
        assertThat(testAccountDetails.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testAccountDetails.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testAccountDetails.getRestrictedAccountFlag()).isEqualTo(DEFAULT_RESTRICTED_ACCOUNT_FLAG);
        assertThat(testAccountDetails.getStaffFlag()).isEqualTo(DEFAULT_STAFF_FLAG);
        assertThat(testAccountDetails.getSweepinAmountOnLien()).isEqualTo(DEFAULT_SWEEPIN_AMOUNT_ON_LIEN);
        assertThat(testAccountDetails.getTaxCode1()).isEqualTo(DEFAULT_TAX_CODE_1);
        assertThat(testAccountDetails.getTaxCode2()).isEqualTo(DEFAULT_TAX_CODE_2);
        assertThat(testAccountDetails.getTdsExemptionLimitAmount1()).isEqualTo(DEFAULT_TDS_EXEMPTION_LIMIT_AMOUNT_1);
        assertThat(testAccountDetails.getTdsExemptionLimitAmount2()).isEqualTo(DEFAULT_TDS_EXEMPTION_LIMIT_AMOUNT_2);
        assertThat(testAccountDetails.getTotalCASAHoldAmount()).isEqualByComparingTo(DEFAULT_TOTAL_CASA_HOLD_AMOUNT);
        assertThat(testAccountDetails.getTotalUnclearFundAmount()).isEqualByComparingTo(DEFAULT_TOTAL_UNCLEAR_FUND_AMOUNT);
        assertThat(testAccountDetails.getYtdCreditLastAmount()).isEqualTo(DEFAULT_YTD_CREDIT_LAST_AMOUNT);
        assertThat(testAccountDetails.getYtdCreditsCount()).isEqualTo(DEFAULT_YTD_CREDITS_COUNT);
        assertThat(testAccountDetails.getYtdDebitsCount()).isEqualTo(DEFAULT_YTD_DEBITS_COUNT);
        assertThat(testAccountDetails.getYtdDebitsLastAmount()).isEqualTo(DEFAULT_YTD_DEBITS_LAST_AMOUNT);
        assertThat(testAccountDetails.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testAccountDetails.getSourceInfo()).isEqualTo(DEFAULT_SOURCE_INFO);
        assertThat(testAccountDetails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAccountDetails.getCustFreeTextField1()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_1);
        assertThat(testAccountDetails.getCustFreeTextField2()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_2);
        assertThat(testAccountDetails.getCustFreeTextField3()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_3);
        assertThat(testAccountDetails.getCustFreeTextField4()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_4);
        assertThat(testAccountDetails.getCustFreeTextField5()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_5);
        assertThat(testAccountDetails.getCustFreeTextField6()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_6);
        assertThat(testAccountDetails.getCustFreeTextField7()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_7);
        assertThat(testAccountDetails.getCustFreeTextField8()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_8);
        assertThat(testAccountDetails.getCustFreeTextField9()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_9);
        assertThat(testAccountDetails.getCustFreeTextField10()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_10);
        assertThat(testAccountDetails.getCustFreeTextField11()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_11);
        assertThat(testAccountDetails.getCustFreeTextField12()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_12);
        assertThat(testAccountDetails.getCustFreeTextField13()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_13);
        assertThat(testAccountDetails.getCustFreeTextField14()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_14);
        assertThat(testAccountDetails.getCustFreeTextField15()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_15);
        assertThat(testAccountDetails.getCustFreeTextField16()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_16);
        assertThat(testAccountDetails.getCustFreeTextField17()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_17);
        assertThat(testAccountDetails.getCustFreeTextField18()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_18);
        assertThat(testAccountDetails.getCustFreeTextField19()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_19);
        assertThat(testAccountDetails.getCustFreeTextField20()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_20);
        assertThat(testAccountDetails.getCustFreeTextField21()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_21);
        assertThat(testAccountDetails.getCustFreeTextField22()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_22);
        assertThat(testAccountDetails.getCustFreeTextField23()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_23);
        assertThat(testAccountDetails.getCustFreeTextField24()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_24);
        assertThat(testAccountDetails.getCustFreeTextField25()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_25);
        assertThat(testAccountDetails.getCustFreeTextField26()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_26);
        assertThat(testAccountDetails.getCustFreeTextField27()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_27);
        assertThat(testAccountDetails.getCustFreeTextField28()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_28);
        assertThat(testAccountDetails.getCustFreeTextField29()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_29);
        assertThat(testAccountDetails.getCustFreeTextField30()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_30);
        assertThat(testAccountDetails.getCustFreeTextField31()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_31);
    }

    @Test
    @Transactional
    void createAccountDetailsWithExistingId() throws Exception {
        // Create the AccountDetails with an existing ID
        accountDetailsRepository.saveAndFlush(accountDetails);

        int databaseSizeBeforeCreate = accountDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAccountDetails() throws Exception {
        // Initialize the database
        accountDetailsRepository.saveAndFlush(accountDetails);

        // Get all the accountDetailsList
        restAccountDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountDetails.getId().toString())))
            .andExpect(jsonPath("$.[*].accountCurrency").value(hasItem(DEFAULT_ACCOUNT_CURRENCY)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].accountStatus").value(hasItem(DEFAULT_ACCOUNT_STATUS)))
            .andExpect(jsonPath("$.[*].accountLifecycleStatusCode").value(hasItem(DEFAULT_ACCOUNT_LIFECYCLE_STATUS_CODE)))
            .andExpect(jsonPath("$.[*].accrualStatusCode").value(hasItem(DEFAULT_ACCRUAL_STATUS_CODE)))
            .andExpect(jsonPath("$.[*].casaAccountStatus").value(hasItem(DEFAULT_CASA_ACCOUNT_STATUS)))
            .andExpect(jsonPath("$.[*].minorAccountStatusCode").value(hasItem(DEFAULT_MINOR_ACCOUNT_STATUS_CODE)))
            .andExpect(jsonPath("$.[*].accountTitle").value(hasItem(DEFAULT_ACCOUNT_TITLE)))
            .andExpect(jsonPath("$.[*].advanceAgainstUnclearedFunds").value(hasItem(sameNumber(DEFAULT_ADVANCE_AGAINST_UNCLEARED_FUNDS))))
            .andExpect(jsonPath("$.[*].adHocStatementFlag").value(hasItem(DEFAULT_AD_HOC_STATEMENT_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].additionalAddressFlag").value(hasItem(DEFAULT_ADDITIONAL_ADDRESS_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].atmFacilityFlag").value(hasItem(DEFAULT_ATM_FACILITY_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].checkReorderThresholdNumber").value(hasItem(sameNumber(DEFAULT_CHECK_REORDER_THRESHOLD_NUMBER))))
            .andExpect(
                jsonPath("$.[*].deferredStmtGenerationDayOfMonth").value(hasItem(sameNumber(DEFAULT_DEFERRED_STMT_GENERATION_DAY_OF_MONTH)))
            )
            .andExpect(
                jsonPath("$.[*].generateRateChangeIntimationFlag")
                    .value(hasItem(DEFAULT_GENERATE_RATE_CHANGE_INTIMATION_FLAG.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].groupBonusInteresFlag").value(hasItem(DEFAULT_GROUP_BONUS_INTERES_FLAG)))
            .andExpect(jsonPath("$.[*].holdMailFlag").value(hasItem(DEFAULT_HOLD_MAIL_FLAG)))
            .andExpect(jsonPath("$.[*].interestWaiverFlag").value(hasItem(DEFAULT_INTEREST_WAIVER_FLAG)))
            .andExpect(jsonPath("$.[*].internetBankingAccessFlag").value(hasItem(DEFAULT_INTERNET_BANKING_ACCESS_FLAG.booleanValue())))
            .andExpect(
                jsonPath("$.[*].inwardDirectDebitAuthorizationFlag")
                    .value(hasItem(DEFAULT_INWARD_DIRECT_DEBIT_AUTHORIZATION_FLAG.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].jointAccountFlag").value(hasItem(DEFAULT_JOINT_ACCOUNT_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].leadDaysIntimation").value(hasItem(DEFAULT_LEAD_DAYS_INTIMATION.booleanValue())))
            .andExpect(jsonPath("$.[*].mailAddressControlFlag").value(hasItem(DEFAULT_MAIL_ADDRESS_CONTROL_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].mobileFacilityFlag").value(hasItem(DEFAULT_MOBILE_FACILITY_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].numberOfCheckWithdrawals").value(hasItem(DEFAULT_NUMBER_OF_CHECK_WITHDRAWALS)))
            .andExpect(jsonPath("$.[*].numberOfPastDueChecks").value(hasItem(DEFAULT_NUMBER_OF_PAST_DUE_CHECKS)))
            .andExpect(jsonPath("$.[*].numberOfStatementCopies").value(hasItem(DEFAULT_NUMBER_OF_STATEMENT_COPIES)))
            .andExpect(jsonPath("$.[*].overdraftLimitAmount").value(hasItem(DEFAULT_OVERDRAFT_LIMIT_AMOUNT)))
            .andExpect(jsonPath("$.[*].pointOfSaleFacilityFlag").value(hasItem(DEFAULT_POINT_OF_SALE_FACILITY_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].standingInstructionFlag").value(hasItem(DEFAULT_STANDING_INSTRUCTION_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].sweepoutInstructionFlag").value(hasItem(DEFAULT_SWEEPOUT_INSTRUCTION_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].availableBalance").value(hasItem(sameNumber(DEFAULT_AVAILABLE_BALANCE))))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].branchShortName").value(hasItem(DEFAULT_BRANCH_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].creditsMonthTillDateAmount").value(hasItem(DEFAULT_CREDITS_MONTH_TILL_DATE_AMOUNT)))
            .andExpect(jsonPath("$.[*].currentBalance").value(hasItem(DEFAULT_CURRENT_BALANCE)))
            .andExpect(jsonPath("$.[*].customerNumber").value(hasItem(DEFAULT_CUSTOMER_NUMBER)))
            .andExpect(jsonPath("$.[*].isAbsaCustomer").value(hasItem(DEFAULT_IS_ABSA_CUSTOMER.booleanValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].accountOpeningDate").value(hasItem(sameInstant(DEFAULT_ACCOUNT_OPENING_DATE))))
            .andExpect(jsonPath("$.[*].statementPeriodStartDate").value(hasItem(sameInstant(DEFAULT_STATEMENT_PERIOD_START_DATE))))
            .andExpect(jsonPath("$.[*].statementPeriodEndDate").value(hasItem(sameInstant(DEFAULT_STATEMENT_PERIOD_END_DATE))))
            .andExpect(jsonPath("$.[*].debitsLastDate").value(hasItem(sameInstant(DEFAULT_DEBITS_LAST_DATE))))
            .andExpect(jsonPath("$.[*].creditLastDate").value(hasItem(sameInstant(DEFAULT_CREDIT_LAST_DATE))))
            .andExpect(jsonPath("$.[*].debitsMonthTillDateAmount").value(hasItem(sameInstant(DEFAULT_DEBITS_MONTH_TILL_DATE_AMOUNT))))
            .andExpect(jsonPath("$.[*].debitsYearTillDateAmount").value(hasItem(sameNumber(DEFAULT_DEBITS_YEAR_TILL_DATE_AMOUNT))))
            .andExpect(jsonPath("$.[*].creditInterestAccruedAmount").value(hasItem(sameNumber(DEFAULT_CREDIT_INTEREST_ACCRUED_AMOUNT))))
            .andExpect(jsonPath("$.[*].debitInterestAccruedAmount").value(hasItem(sameNumber(DEFAULT_DEBIT_INTEREST_ACCRUED_AMOUNT))))
            .andExpect(jsonPath("$.[*].adjustedCreditInterestAccrued").value(hasItem(sameNumber(DEFAULT_ADJUSTED_CREDIT_INTEREST_ACCRUED))))
            .andExpect(jsonPath("$.[*].adjustedDebitInterestAccrued").value(hasItem(sameNumber(DEFAULT_ADJUSTED_DEBIT_INTEREST_ACCRUED))))
            .andExpect(
                jsonPath("$.[*].projectedTaxOnAccruedInterestAmount")
                    .value(hasItem(sameNumber(DEFAULT_PROJECTED_TAX_ON_ACCRUED_INTEREST_AMOUNT)))
            )
            .andExpect(
                jsonPath("$.[*].interestAccruedInCurrentFinancialYear").value(hasItem(DEFAULT_INTEREST_ACCRUED_IN_CURRENT_FINANCIAL_YEAR))
            )
            .andExpect(jsonPath("$.[*].issuedInventoryPropertyType").value(hasItem(DEFAULT_ISSUED_INVENTORY_PROPERTY_TYPE)))
            .andExpect(jsonPath("$.[*].lastIssuedCheckNumber").value(hasItem(DEFAULT_LAST_ISSUED_CHECK_NUMBER)))
            .andExpect(jsonPath("$.[*].languageCode").value(hasItem(DEFAULT_LANGUAGE_CODE)))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER)))
            .andExpect(jsonPath("$.[*].minimumRequiredBalanceAmount").value(hasItem(sameNumber(DEFAULT_MINIMUM_REQUIRED_BALANCE_AMOUNT))))
            .andExpect(
                jsonPath("$.[*].minimumRequiredTradingBalanceAmount")
                    .value(hasItem(sameNumber(DEFAULT_MINIMUM_REQUIRED_TRADING_BALANCE_AMOUNT)))
            )
            .andExpect(jsonPath("$.[*].mtdCreditsCount").value(hasItem(DEFAULT_MTD_CREDITS_COUNT)))
            .andExpect(jsonPath("$.[*].mtdDebitsCount").value(hasItem(DEFAULT_MTD_DEBITS_COUNT)))
            .andExpect(jsonPath("$.[*].netAvailableBalanceForWithdrawal").value(hasItem(DEFAULT_NET_AVAILABLE_BALANCE_FOR_WITHDRAWAL)))
            .andExpect(jsonPath("$.[*].netBalanceAmount").value(hasItem(DEFAULT_NET_BALANCE_AMOUNT)))
            .andExpect(jsonPath("$.[*].passbookLifecycleStatusCode").value(hasItem(DEFAULT_PASSBOOK_LIFECYCLE_STATUS_CODE)))
            .andExpect(jsonPath("$.[*].periodicAverageBalanceAmount").value(hasItem(sameNumber(DEFAULT_PERIODIC_AVERAGE_BALANCE_AMOUNT))))
            .andExpect(
                jsonPath("$.[*].previousDayClosingBookBalance").value(hasItem(sameNumber(DEFAULT_PREVIOUS_DAY_CLOSING_BOOK_BALANCE)))
            )
            .andExpect(jsonPath("$.[*].productCode").value(hasItem(DEFAULT_PRODUCT_CODE)))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].restrictedAccountFlag").value(hasItem(DEFAULT_RESTRICTED_ACCOUNT_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].staffFlag").value(hasItem(DEFAULT_STAFF_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].sweepinAmountOnLien").value(hasItem(DEFAULT_SWEEPIN_AMOUNT_ON_LIEN)))
            .andExpect(jsonPath("$.[*].taxCode1").value(hasItem(DEFAULT_TAX_CODE_1)))
            .andExpect(jsonPath("$.[*].taxCode2").value(hasItem(DEFAULT_TAX_CODE_2)))
            .andExpect(jsonPath("$.[*].tdsExemptionLimitAmount1").value(hasItem(DEFAULT_TDS_EXEMPTION_LIMIT_AMOUNT_1)))
            .andExpect(jsonPath("$.[*].tdsExemptionLimitAmount2").value(hasItem(DEFAULT_TDS_EXEMPTION_LIMIT_AMOUNT_2)))
            .andExpect(jsonPath("$.[*].totalCASAHoldAmount").value(hasItem(sameNumber(DEFAULT_TOTAL_CASA_HOLD_AMOUNT))))
            .andExpect(jsonPath("$.[*].totalUnclearFundAmount").value(hasItem(sameNumber(DEFAULT_TOTAL_UNCLEAR_FUND_AMOUNT))))
            .andExpect(jsonPath("$.[*].ytdCreditLastAmount").value(hasItem(DEFAULT_YTD_CREDIT_LAST_AMOUNT)))
            .andExpect(jsonPath("$.[*].ytdCreditsCount").value(hasItem(DEFAULT_YTD_CREDITS_COUNT)))
            .andExpect(jsonPath("$.[*].ytdDebitsCount").value(hasItem(DEFAULT_YTD_DEBITS_COUNT)))
            .andExpect(jsonPath("$.[*].ytdDebitsLastAmount").value(hasItem(DEFAULT_YTD_DEBITS_LAST_AMOUNT)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].sourceInfo").value(hasItem(DEFAULT_SOURCE_INFO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].custFreeTextField1").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_1)))
            .andExpect(jsonPath("$.[*].custFreeTextField2").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_2)))
            .andExpect(jsonPath("$.[*].custFreeTextField3").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_3)))
            .andExpect(jsonPath("$.[*].custFreeTextField4").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_4)))
            .andExpect(jsonPath("$.[*].custFreeTextField5").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_5)))
            .andExpect(jsonPath("$.[*].custFreeTextField6").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_6)))
            .andExpect(jsonPath("$.[*].custFreeTextField7").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_7)))
            .andExpect(jsonPath("$.[*].custFreeTextField8").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_8)))
            .andExpect(jsonPath("$.[*].custFreeTextField9").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_9)))
            .andExpect(jsonPath("$.[*].custFreeTextField10").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_10)))
            .andExpect(jsonPath("$.[*].custFreeTextField11").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_11)))
            .andExpect(jsonPath("$.[*].custFreeTextField12").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_12)))
            .andExpect(jsonPath("$.[*].custFreeTextField13").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_13)))
            .andExpect(jsonPath("$.[*].custFreeTextField14").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_14)))
            .andExpect(jsonPath("$.[*].custFreeTextField15").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_15)))
            .andExpect(jsonPath("$.[*].custFreeTextField16").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_16)))
            .andExpect(jsonPath("$.[*].custFreeTextField17").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_17)))
            .andExpect(jsonPath("$.[*].custFreeTextField18").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_18)))
            .andExpect(jsonPath("$.[*].custFreeTextField19").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_19)))
            .andExpect(jsonPath("$.[*].custFreeTextField20").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_20)))
            .andExpect(jsonPath("$.[*].custFreeTextField21").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_21)))
            .andExpect(jsonPath("$.[*].custFreeTextField22").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_22)))
            .andExpect(jsonPath("$.[*].custFreeTextField23").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_23)))
            .andExpect(jsonPath("$.[*].custFreeTextField24").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_24)))
            .andExpect(jsonPath("$.[*].custFreeTextField25").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_25)))
            .andExpect(jsonPath("$.[*].custFreeTextField26").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_26)))
            .andExpect(jsonPath("$.[*].custFreeTextField27").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_27)))
            .andExpect(jsonPath("$.[*].custFreeTextField28").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_28)))
            .andExpect(jsonPath("$.[*].custFreeTextField29").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_29)))
            .andExpect(jsonPath("$.[*].custFreeTextField30").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_30)))
            .andExpect(jsonPath("$.[*].custFreeTextField31").value(hasItem(DEFAULT_CUST_FREE_TEXT_FIELD_31)));
    }

    @Test
    @Transactional
    void getAccountDetails() throws Exception {
        // Initialize the database
        accountDetailsRepository.saveAndFlush(accountDetails);

        // Get the accountDetails
        restAccountDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, accountDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountDetails.getId().toString()))
            .andExpect(jsonPath("$.accountCurrency").value(DEFAULT_ACCOUNT_CURRENCY))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.accountStatus").value(DEFAULT_ACCOUNT_STATUS))
            .andExpect(jsonPath("$.accountLifecycleStatusCode").value(DEFAULT_ACCOUNT_LIFECYCLE_STATUS_CODE))
            .andExpect(jsonPath("$.accrualStatusCode").value(DEFAULT_ACCRUAL_STATUS_CODE))
            .andExpect(jsonPath("$.casaAccountStatus").value(DEFAULT_CASA_ACCOUNT_STATUS))
            .andExpect(jsonPath("$.minorAccountStatusCode").value(DEFAULT_MINOR_ACCOUNT_STATUS_CODE))
            .andExpect(jsonPath("$.accountTitle").value(DEFAULT_ACCOUNT_TITLE))
            .andExpect(jsonPath("$.advanceAgainstUnclearedFunds").value(sameNumber(DEFAULT_ADVANCE_AGAINST_UNCLEARED_FUNDS)))
            .andExpect(jsonPath("$.adHocStatementFlag").value(DEFAULT_AD_HOC_STATEMENT_FLAG.booleanValue()))
            .andExpect(jsonPath("$.additionalAddressFlag").value(DEFAULT_ADDITIONAL_ADDRESS_FLAG.booleanValue()))
            .andExpect(jsonPath("$.atmFacilityFlag").value(DEFAULT_ATM_FACILITY_FLAG.booleanValue()))
            .andExpect(jsonPath("$.checkReorderThresholdNumber").value(sameNumber(DEFAULT_CHECK_REORDER_THRESHOLD_NUMBER)))
            .andExpect(jsonPath("$.deferredStmtGenerationDayOfMonth").value(sameNumber(DEFAULT_DEFERRED_STMT_GENERATION_DAY_OF_MONTH)))
            .andExpect(jsonPath("$.generateRateChangeIntimationFlag").value(DEFAULT_GENERATE_RATE_CHANGE_INTIMATION_FLAG.booleanValue()))
            .andExpect(jsonPath("$.groupBonusInteresFlag").value(DEFAULT_GROUP_BONUS_INTERES_FLAG))
            .andExpect(jsonPath("$.holdMailFlag").value(DEFAULT_HOLD_MAIL_FLAG))
            .andExpect(jsonPath("$.interestWaiverFlag").value(DEFAULT_INTEREST_WAIVER_FLAG))
            .andExpect(jsonPath("$.internetBankingAccessFlag").value(DEFAULT_INTERNET_BANKING_ACCESS_FLAG.booleanValue()))
            .andExpect(
                jsonPath("$.inwardDirectDebitAuthorizationFlag").value(DEFAULT_INWARD_DIRECT_DEBIT_AUTHORIZATION_FLAG.booleanValue())
            )
            .andExpect(jsonPath("$.jointAccountFlag").value(DEFAULT_JOINT_ACCOUNT_FLAG.booleanValue()))
            .andExpect(jsonPath("$.leadDaysIntimation").value(DEFAULT_LEAD_DAYS_INTIMATION.booleanValue()))
            .andExpect(jsonPath("$.mailAddressControlFlag").value(DEFAULT_MAIL_ADDRESS_CONTROL_FLAG.booleanValue()))
            .andExpect(jsonPath("$.mobileFacilityFlag").value(DEFAULT_MOBILE_FACILITY_FLAG.booleanValue()))
            .andExpect(jsonPath("$.numberOfCheckWithdrawals").value(DEFAULT_NUMBER_OF_CHECK_WITHDRAWALS))
            .andExpect(jsonPath("$.numberOfPastDueChecks").value(DEFAULT_NUMBER_OF_PAST_DUE_CHECKS))
            .andExpect(jsonPath("$.numberOfStatementCopies").value(DEFAULT_NUMBER_OF_STATEMENT_COPIES))
            .andExpect(jsonPath("$.overdraftLimitAmount").value(DEFAULT_OVERDRAFT_LIMIT_AMOUNT))
            .andExpect(jsonPath("$.pointOfSaleFacilityFlag").value(DEFAULT_POINT_OF_SALE_FACILITY_FLAG.booleanValue()))
            .andExpect(jsonPath("$.standingInstructionFlag").value(DEFAULT_STANDING_INSTRUCTION_FLAG.booleanValue()))
            .andExpect(jsonPath("$.sweepoutInstructionFlag").value(DEFAULT_SWEEPOUT_INSTRUCTION_FLAG.booleanValue()))
            .andExpect(jsonPath("$.availableBalance").value(sameNumber(DEFAULT_AVAILABLE_BALANCE)))
            .andExpect(jsonPath("$.branchCode").value(DEFAULT_BRANCH_CODE))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
            .andExpect(jsonPath("$.branchShortName").value(DEFAULT_BRANCH_SHORT_NAME))
            .andExpect(jsonPath("$.creditsMonthTillDateAmount").value(DEFAULT_CREDITS_MONTH_TILL_DATE_AMOUNT))
            .andExpect(jsonPath("$.currentBalance").value(DEFAULT_CURRENT_BALANCE))
            .andExpect(jsonPath("$.customerNumber").value(DEFAULT_CUSTOMER_NUMBER))
            .andExpect(jsonPath("$.isAbsaCustomer").value(DEFAULT_IS_ABSA_CUSTOMER.booleanValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.accountOpeningDate").value(sameInstant(DEFAULT_ACCOUNT_OPENING_DATE)))
            .andExpect(jsonPath("$.statementPeriodStartDate").value(sameInstant(DEFAULT_STATEMENT_PERIOD_START_DATE)))
            .andExpect(jsonPath("$.statementPeriodEndDate").value(sameInstant(DEFAULT_STATEMENT_PERIOD_END_DATE)))
            .andExpect(jsonPath("$.debitsLastDate").value(sameInstant(DEFAULT_DEBITS_LAST_DATE)))
            .andExpect(jsonPath("$.creditLastDate").value(sameInstant(DEFAULT_CREDIT_LAST_DATE)))
            .andExpect(jsonPath("$.debitsMonthTillDateAmount").value(sameInstant(DEFAULT_DEBITS_MONTH_TILL_DATE_AMOUNT)))
            .andExpect(jsonPath("$.debitsYearTillDateAmount").value(sameNumber(DEFAULT_DEBITS_YEAR_TILL_DATE_AMOUNT)))
            .andExpect(jsonPath("$.creditInterestAccruedAmount").value(sameNumber(DEFAULT_CREDIT_INTEREST_ACCRUED_AMOUNT)))
            .andExpect(jsonPath("$.debitInterestAccruedAmount").value(sameNumber(DEFAULT_DEBIT_INTEREST_ACCRUED_AMOUNT)))
            .andExpect(jsonPath("$.adjustedCreditInterestAccrued").value(sameNumber(DEFAULT_ADJUSTED_CREDIT_INTEREST_ACCRUED)))
            .andExpect(jsonPath("$.adjustedDebitInterestAccrued").value(sameNumber(DEFAULT_ADJUSTED_DEBIT_INTEREST_ACCRUED)))
            .andExpect(
                jsonPath("$.projectedTaxOnAccruedInterestAmount").value(sameNumber(DEFAULT_PROJECTED_TAX_ON_ACCRUED_INTEREST_AMOUNT))
            )
            .andExpect(jsonPath("$.interestAccruedInCurrentFinancialYear").value(DEFAULT_INTEREST_ACCRUED_IN_CURRENT_FINANCIAL_YEAR))
            .andExpect(jsonPath("$.issuedInventoryPropertyType").value(DEFAULT_ISSUED_INVENTORY_PROPERTY_TYPE))
            .andExpect(jsonPath("$.lastIssuedCheckNumber").value(DEFAULT_LAST_ISSUED_CHECK_NUMBER))
            .andExpect(jsonPath("$.languageCode").value(DEFAULT_LANGUAGE_CODE))
            .andExpect(jsonPath("$.lineNumber").value(DEFAULT_LINE_NUMBER))
            .andExpect(jsonPath("$.minimumRequiredBalanceAmount").value(sameNumber(DEFAULT_MINIMUM_REQUIRED_BALANCE_AMOUNT)))
            .andExpect(jsonPath("$.minimumRequiredTradingBalanceAmount").value(sameNumber(DEFAULT_MINIMUM_REQUIRED_TRADING_BALANCE_AMOUNT)))
            .andExpect(jsonPath("$.mtdCreditsCount").value(DEFAULT_MTD_CREDITS_COUNT))
            .andExpect(jsonPath("$.mtdDebitsCount").value(DEFAULT_MTD_DEBITS_COUNT))
            .andExpect(jsonPath("$.netAvailableBalanceForWithdrawal").value(DEFAULT_NET_AVAILABLE_BALANCE_FOR_WITHDRAWAL))
            .andExpect(jsonPath("$.netBalanceAmount").value(DEFAULT_NET_BALANCE_AMOUNT))
            .andExpect(jsonPath("$.passbookLifecycleStatusCode").value(DEFAULT_PASSBOOK_LIFECYCLE_STATUS_CODE))
            .andExpect(jsonPath("$.periodicAverageBalanceAmount").value(sameNumber(DEFAULT_PERIODIC_AVERAGE_BALANCE_AMOUNT)))
            .andExpect(jsonPath("$.previousDayClosingBookBalance").value(sameNumber(DEFAULT_PREVIOUS_DAY_CLOSING_BOOK_BALANCE)))
            .andExpect(jsonPath("$.productCode").value(DEFAULT_PRODUCT_CODE))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.restrictedAccountFlag").value(DEFAULT_RESTRICTED_ACCOUNT_FLAG.booleanValue()))
            .andExpect(jsonPath("$.staffFlag").value(DEFAULT_STAFF_FLAG.booleanValue()))
            .andExpect(jsonPath("$.sweepinAmountOnLien").value(DEFAULT_SWEEPIN_AMOUNT_ON_LIEN))
            .andExpect(jsonPath("$.taxCode1").value(DEFAULT_TAX_CODE_1))
            .andExpect(jsonPath("$.taxCode2").value(DEFAULT_TAX_CODE_2))
            .andExpect(jsonPath("$.tdsExemptionLimitAmount1").value(DEFAULT_TDS_EXEMPTION_LIMIT_AMOUNT_1))
            .andExpect(jsonPath("$.tdsExemptionLimitAmount2").value(DEFAULT_TDS_EXEMPTION_LIMIT_AMOUNT_2))
            .andExpect(jsonPath("$.totalCASAHoldAmount").value(sameNumber(DEFAULT_TOTAL_CASA_HOLD_AMOUNT)))
            .andExpect(jsonPath("$.totalUnclearFundAmount").value(sameNumber(DEFAULT_TOTAL_UNCLEAR_FUND_AMOUNT)))
            .andExpect(jsonPath("$.ytdCreditLastAmount").value(DEFAULT_YTD_CREDIT_LAST_AMOUNT))
            .andExpect(jsonPath("$.ytdCreditsCount").value(DEFAULT_YTD_CREDITS_COUNT))
            .andExpect(jsonPath("$.ytdDebitsCount").value(DEFAULT_YTD_DEBITS_COUNT))
            .andExpect(jsonPath("$.ytdDebitsLastAmount").value(DEFAULT_YTD_DEBITS_LAST_AMOUNT))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.sourceInfo").value(DEFAULT_SOURCE_INFO))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.custFreeTextField1").value(DEFAULT_CUST_FREE_TEXT_FIELD_1))
            .andExpect(jsonPath("$.custFreeTextField2").value(DEFAULT_CUST_FREE_TEXT_FIELD_2))
            .andExpect(jsonPath("$.custFreeTextField3").value(DEFAULT_CUST_FREE_TEXT_FIELD_3))
            .andExpect(jsonPath("$.custFreeTextField4").value(DEFAULT_CUST_FREE_TEXT_FIELD_4))
            .andExpect(jsonPath("$.custFreeTextField5").value(DEFAULT_CUST_FREE_TEXT_FIELD_5))
            .andExpect(jsonPath("$.custFreeTextField6").value(DEFAULT_CUST_FREE_TEXT_FIELD_6))
            .andExpect(jsonPath("$.custFreeTextField7").value(DEFAULT_CUST_FREE_TEXT_FIELD_7))
            .andExpect(jsonPath("$.custFreeTextField8").value(DEFAULT_CUST_FREE_TEXT_FIELD_8))
            .andExpect(jsonPath("$.custFreeTextField9").value(DEFAULT_CUST_FREE_TEXT_FIELD_9))
            .andExpect(jsonPath("$.custFreeTextField10").value(DEFAULT_CUST_FREE_TEXT_FIELD_10))
            .andExpect(jsonPath("$.custFreeTextField11").value(DEFAULT_CUST_FREE_TEXT_FIELD_11))
            .andExpect(jsonPath("$.custFreeTextField12").value(DEFAULT_CUST_FREE_TEXT_FIELD_12))
            .andExpect(jsonPath("$.custFreeTextField13").value(DEFAULT_CUST_FREE_TEXT_FIELD_13))
            .andExpect(jsonPath("$.custFreeTextField14").value(DEFAULT_CUST_FREE_TEXT_FIELD_14))
            .andExpect(jsonPath("$.custFreeTextField15").value(DEFAULT_CUST_FREE_TEXT_FIELD_15))
            .andExpect(jsonPath("$.custFreeTextField16").value(DEFAULT_CUST_FREE_TEXT_FIELD_16))
            .andExpect(jsonPath("$.custFreeTextField17").value(DEFAULT_CUST_FREE_TEXT_FIELD_17))
            .andExpect(jsonPath("$.custFreeTextField18").value(DEFAULT_CUST_FREE_TEXT_FIELD_18))
            .andExpect(jsonPath("$.custFreeTextField19").value(DEFAULT_CUST_FREE_TEXT_FIELD_19))
            .andExpect(jsonPath("$.custFreeTextField20").value(DEFAULT_CUST_FREE_TEXT_FIELD_20))
            .andExpect(jsonPath("$.custFreeTextField21").value(DEFAULT_CUST_FREE_TEXT_FIELD_21))
            .andExpect(jsonPath("$.custFreeTextField22").value(DEFAULT_CUST_FREE_TEXT_FIELD_22))
            .andExpect(jsonPath("$.custFreeTextField23").value(DEFAULT_CUST_FREE_TEXT_FIELD_23))
            .andExpect(jsonPath("$.custFreeTextField24").value(DEFAULT_CUST_FREE_TEXT_FIELD_24))
            .andExpect(jsonPath("$.custFreeTextField25").value(DEFAULT_CUST_FREE_TEXT_FIELD_25))
            .andExpect(jsonPath("$.custFreeTextField26").value(DEFAULT_CUST_FREE_TEXT_FIELD_26))
            .andExpect(jsonPath("$.custFreeTextField27").value(DEFAULT_CUST_FREE_TEXT_FIELD_27))
            .andExpect(jsonPath("$.custFreeTextField28").value(DEFAULT_CUST_FREE_TEXT_FIELD_28))
            .andExpect(jsonPath("$.custFreeTextField29").value(DEFAULT_CUST_FREE_TEXT_FIELD_29))
            .andExpect(jsonPath("$.custFreeTextField30").value(DEFAULT_CUST_FREE_TEXT_FIELD_30))
            .andExpect(jsonPath("$.custFreeTextField31").value(DEFAULT_CUST_FREE_TEXT_FIELD_31));
    }

    @Test
    @Transactional
    void getNonExistingAccountDetails() throws Exception {
        // Get the accountDetails
        restAccountDetailsMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAccountDetails() throws Exception {
        // Initialize the database
        accountDetailsRepository.saveAndFlush(accountDetails);

        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();

        // Update the accountDetails
        AccountDetails updatedAccountDetails = accountDetailsRepository.findById(accountDetails.getId()).get();
        // Disconnect from session so that the updates on updatedAccountDetails are not directly saved in db
        em.detach(updatedAccountDetails);
        updatedAccountDetails
            .accountCurrency(UPDATED_ACCOUNT_CURRENCY)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountStatus(UPDATED_ACCOUNT_STATUS)
            .accountLifecycleStatusCode(UPDATED_ACCOUNT_LIFECYCLE_STATUS_CODE)
            .accrualStatusCode(UPDATED_ACCRUAL_STATUS_CODE)
            .casaAccountStatus(UPDATED_CASA_ACCOUNT_STATUS)
            .minorAccountStatusCode(UPDATED_MINOR_ACCOUNT_STATUS_CODE)
            .accountTitle(UPDATED_ACCOUNT_TITLE)
            .advanceAgainstUnclearedFunds(UPDATED_ADVANCE_AGAINST_UNCLEARED_FUNDS)
            .adHocStatementFlag(UPDATED_AD_HOC_STATEMENT_FLAG)
            .additionalAddressFlag(UPDATED_ADDITIONAL_ADDRESS_FLAG)
            .atmFacilityFlag(UPDATED_ATM_FACILITY_FLAG)
            .checkReorderThresholdNumber(UPDATED_CHECK_REORDER_THRESHOLD_NUMBER)
            .deferredStmtGenerationDayOfMonth(UPDATED_DEFERRED_STMT_GENERATION_DAY_OF_MONTH)
            .generateRateChangeIntimationFlag(UPDATED_GENERATE_RATE_CHANGE_INTIMATION_FLAG)
            .groupBonusInteresFlag(UPDATED_GROUP_BONUS_INTERES_FLAG)
            .holdMailFlag(UPDATED_HOLD_MAIL_FLAG)
            .interestWaiverFlag(UPDATED_INTEREST_WAIVER_FLAG)
            .internetBankingAccessFlag(UPDATED_INTERNET_BANKING_ACCESS_FLAG)
            .inwardDirectDebitAuthorizationFlag(UPDATED_INWARD_DIRECT_DEBIT_AUTHORIZATION_FLAG)
            .jointAccountFlag(UPDATED_JOINT_ACCOUNT_FLAG)
            .leadDaysIntimation(UPDATED_LEAD_DAYS_INTIMATION)
            .mailAddressControlFlag(UPDATED_MAIL_ADDRESS_CONTROL_FLAG)
            .mobileFacilityFlag(UPDATED_MOBILE_FACILITY_FLAG)
            .numberOfCheckWithdrawals(UPDATED_NUMBER_OF_CHECK_WITHDRAWALS)
            .numberOfPastDueChecks(UPDATED_NUMBER_OF_PAST_DUE_CHECKS)
            .numberOfStatementCopies(UPDATED_NUMBER_OF_STATEMENT_COPIES)
            .overdraftLimitAmount(UPDATED_OVERDRAFT_LIMIT_AMOUNT)
            .pointOfSaleFacilityFlag(UPDATED_POINT_OF_SALE_FACILITY_FLAG)
            .standingInstructionFlag(UPDATED_STANDING_INSTRUCTION_FLAG)
            .sweepoutInstructionFlag(UPDATED_SWEEPOUT_INSTRUCTION_FLAG)
            .availableBalance(UPDATED_AVAILABLE_BALANCE)
            .branchCode(UPDATED_BRANCH_CODE)
            .branchName(UPDATED_BRANCH_NAME)
            .branchShortName(UPDATED_BRANCH_SHORT_NAME)
            .creditsMonthTillDateAmount(UPDATED_CREDITS_MONTH_TILL_DATE_AMOUNT)
            .currentBalance(UPDATED_CURRENT_BALANCE)
            .customerNumber(UPDATED_CUSTOMER_NUMBER)
            .isAbsaCustomer(UPDATED_IS_ABSA_CUSTOMER)
            .fullName(UPDATED_FULL_NAME)
            .accountOpeningDate(UPDATED_ACCOUNT_OPENING_DATE)
            .statementPeriodStartDate(UPDATED_STATEMENT_PERIOD_START_DATE)
            .statementPeriodEndDate(UPDATED_STATEMENT_PERIOD_END_DATE)
            .debitsLastDate(UPDATED_DEBITS_LAST_DATE)
            .creditLastDate(UPDATED_CREDIT_LAST_DATE)
            .debitsMonthTillDateAmount(UPDATED_DEBITS_MONTH_TILL_DATE_AMOUNT)
            .debitsYearTillDateAmount(UPDATED_DEBITS_YEAR_TILL_DATE_AMOUNT)
            .creditInterestAccruedAmount(UPDATED_CREDIT_INTEREST_ACCRUED_AMOUNT)
            .debitInterestAccruedAmount(UPDATED_DEBIT_INTEREST_ACCRUED_AMOUNT)
            .adjustedCreditInterestAccrued(UPDATED_ADJUSTED_CREDIT_INTEREST_ACCRUED)
            .adjustedDebitInterestAccrued(UPDATED_ADJUSTED_DEBIT_INTEREST_ACCRUED)
            .projectedTaxOnAccruedInterestAmount(UPDATED_PROJECTED_TAX_ON_ACCRUED_INTEREST_AMOUNT)
            .interestAccruedInCurrentFinancialYear(UPDATED_INTEREST_ACCRUED_IN_CURRENT_FINANCIAL_YEAR)
            .issuedInventoryPropertyType(UPDATED_ISSUED_INVENTORY_PROPERTY_TYPE)
            .lastIssuedCheckNumber(UPDATED_LAST_ISSUED_CHECK_NUMBER)
            .languageCode(UPDATED_LANGUAGE_CODE)
            .lineNumber(UPDATED_LINE_NUMBER)
            .minimumRequiredBalanceAmount(UPDATED_MINIMUM_REQUIRED_BALANCE_AMOUNT)
            .minimumRequiredTradingBalanceAmount(UPDATED_MINIMUM_REQUIRED_TRADING_BALANCE_AMOUNT)
            .mtdCreditsCount(UPDATED_MTD_CREDITS_COUNT)
            .mtdDebitsCount(UPDATED_MTD_DEBITS_COUNT)
            .netAvailableBalanceForWithdrawal(UPDATED_NET_AVAILABLE_BALANCE_FOR_WITHDRAWAL)
            .netBalanceAmount(UPDATED_NET_BALANCE_AMOUNT)
            .passbookLifecycleStatusCode(UPDATED_PASSBOOK_LIFECYCLE_STATUS_CODE)
            .periodicAverageBalanceAmount(UPDATED_PERIODIC_AVERAGE_BALANCE_AMOUNT)
            .previousDayClosingBookBalance(UPDATED_PREVIOUS_DAY_CLOSING_BOOK_BALANCE)
            .productCode(UPDATED_PRODUCT_CODE)
            .productName(UPDATED_PRODUCT_NAME)
            .restrictedAccountFlag(UPDATED_RESTRICTED_ACCOUNT_FLAG)
            .staffFlag(UPDATED_STAFF_FLAG)
            .sweepinAmountOnLien(UPDATED_SWEEPIN_AMOUNT_ON_LIEN)
            .taxCode1(UPDATED_TAX_CODE_1)
            .taxCode2(UPDATED_TAX_CODE_2)
            .tdsExemptionLimitAmount1(UPDATED_TDS_EXEMPTION_LIMIT_AMOUNT_1)
            .tdsExemptionLimitAmount2(UPDATED_TDS_EXEMPTION_LIMIT_AMOUNT_2)
            .totalCASAHoldAmount(UPDATED_TOTAL_CASA_HOLD_AMOUNT)
            .totalUnclearFundAmount(UPDATED_TOTAL_UNCLEAR_FUND_AMOUNT)
            .ytdCreditLastAmount(UPDATED_YTD_CREDIT_LAST_AMOUNT)
            .ytdCreditsCount(UPDATED_YTD_CREDITS_COUNT)
            .ytdDebitsCount(UPDATED_YTD_DEBITS_COUNT)
            .ytdDebitsLastAmount(UPDATED_YTD_DEBITS_LAST_AMOUNT)
            .message(UPDATED_MESSAGE)
            .sourceInfo(UPDATED_SOURCE_INFO)
            .status(UPDATED_STATUS)
            .custFreeTextField1(UPDATED_CUST_FREE_TEXT_FIELD_1)
            .custFreeTextField2(UPDATED_CUST_FREE_TEXT_FIELD_2)
            .custFreeTextField3(UPDATED_CUST_FREE_TEXT_FIELD_3)
            .custFreeTextField4(UPDATED_CUST_FREE_TEXT_FIELD_4)
            .custFreeTextField5(UPDATED_CUST_FREE_TEXT_FIELD_5)
            .custFreeTextField6(UPDATED_CUST_FREE_TEXT_FIELD_6)
            .custFreeTextField7(UPDATED_CUST_FREE_TEXT_FIELD_7)
            .custFreeTextField8(UPDATED_CUST_FREE_TEXT_FIELD_8)
            .custFreeTextField9(UPDATED_CUST_FREE_TEXT_FIELD_9)
            .custFreeTextField10(UPDATED_CUST_FREE_TEXT_FIELD_10)
            .custFreeTextField11(UPDATED_CUST_FREE_TEXT_FIELD_11)
            .custFreeTextField12(UPDATED_CUST_FREE_TEXT_FIELD_12)
            .custFreeTextField13(UPDATED_CUST_FREE_TEXT_FIELD_13)
            .custFreeTextField14(UPDATED_CUST_FREE_TEXT_FIELD_14)
            .custFreeTextField15(UPDATED_CUST_FREE_TEXT_FIELD_15)
            .custFreeTextField16(UPDATED_CUST_FREE_TEXT_FIELD_16)
            .custFreeTextField17(UPDATED_CUST_FREE_TEXT_FIELD_17)
            .custFreeTextField18(UPDATED_CUST_FREE_TEXT_FIELD_18)
            .custFreeTextField19(UPDATED_CUST_FREE_TEXT_FIELD_19)
            .custFreeTextField20(UPDATED_CUST_FREE_TEXT_FIELD_20)
            .custFreeTextField21(UPDATED_CUST_FREE_TEXT_FIELD_21)
            .custFreeTextField22(UPDATED_CUST_FREE_TEXT_FIELD_22)
            .custFreeTextField23(UPDATED_CUST_FREE_TEXT_FIELD_23)
            .custFreeTextField24(UPDATED_CUST_FREE_TEXT_FIELD_24)
            .custFreeTextField25(UPDATED_CUST_FREE_TEXT_FIELD_25)
            .custFreeTextField26(UPDATED_CUST_FREE_TEXT_FIELD_26)
            .custFreeTextField27(UPDATED_CUST_FREE_TEXT_FIELD_27)
            .custFreeTextField28(UPDATED_CUST_FREE_TEXT_FIELD_28)
            .custFreeTextField29(UPDATED_CUST_FREE_TEXT_FIELD_29)
            .custFreeTextField30(UPDATED_CUST_FREE_TEXT_FIELD_30)
            .custFreeTextField31(UPDATED_CUST_FREE_TEXT_FIELD_31);

        restAccountDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAccountDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAccountDetails))
            )
            .andExpect(status().isOk());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
        AccountDetails testAccountDetails = accountDetailsList.get(accountDetailsList.size() - 1);
        assertThat(testAccountDetails.getAccountCurrency()).isEqualTo(UPDATED_ACCOUNT_CURRENCY);
        assertThat(testAccountDetails.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testAccountDetails.getAccountStatus()).isEqualTo(UPDATED_ACCOUNT_STATUS);
        assertThat(testAccountDetails.getAccountLifecycleStatusCode()).isEqualTo(UPDATED_ACCOUNT_LIFECYCLE_STATUS_CODE);
        assertThat(testAccountDetails.getAccrualStatusCode()).isEqualTo(UPDATED_ACCRUAL_STATUS_CODE);
        assertThat(testAccountDetails.getCasaAccountStatus()).isEqualTo(UPDATED_CASA_ACCOUNT_STATUS);
        assertThat(testAccountDetails.getMinorAccountStatusCode()).isEqualTo(UPDATED_MINOR_ACCOUNT_STATUS_CODE);
        assertThat(testAccountDetails.getAccountTitle()).isEqualTo(UPDATED_ACCOUNT_TITLE);
        assertThat(testAccountDetails.getAdvanceAgainstUnclearedFunds()).isEqualByComparingTo(UPDATED_ADVANCE_AGAINST_UNCLEARED_FUNDS);
        assertThat(testAccountDetails.getAdHocStatementFlag()).isEqualTo(UPDATED_AD_HOC_STATEMENT_FLAG);
        assertThat(testAccountDetails.getAdditionalAddressFlag()).isEqualTo(UPDATED_ADDITIONAL_ADDRESS_FLAG);
        assertThat(testAccountDetails.getAtmFacilityFlag()).isEqualTo(UPDATED_ATM_FACILITY_FLAG);
        assertThat(testAccountDetails.getCheckReorderThresholdNumber()).isEqualByComparingTo(UPDATED_CHECK_REORDER_THRESHOLD_NUMBER);
        assertThat(testAccountDetails.getDeferredStmtGenerationDayOfMonth())
            .isEqualByComparingTo(UPDATED_DEFERRED_STMT_GENERATION_DAY_OF_MONTH);
        assertThat(testAccountDetails.getGenerateRateChangeIntimationFlag()).isEqualTo(UPDATED_GENERATE_RATE_CHANGE_INTIMATION_FLAG);
        assertThat(testAccountDetails.getGroupBonusInteresFlag()).isEqualTo(UPDATED_GROUP_BONUS_INTERES_FLAG);
        assertThat(testAccountDetails.getHoldMailFlag()).isEqualTo(UPDATED_HOLD_MAIL_FLAG);
        assertThat(testAccountDetails.getInterestWaiverFlag()).isEqualTo(UPDATED_INTEREST_WAIVER_FLAG);
        assertThat(testAccountDetails.getInternetBankingAccessFlag()).isEqualTo(UPDATED_INTERNET_BANKING_ACCESS_FLAG);
        assertThat(testAccountDetails.getInwardDirectDebitAuthorizationFlag()).isEqualTo(UPDATED_INWARD_DIRECT_DEBIT_AUTHORIZATION_FLAG);
        assertThat(testAccountDetails.getJointAccountFlag()).isEqualTo(UPDATED_JOINT_ACCOUNT_FLAG);
        assertThat(testAccountDetails.getLeadDaysIntimation()).isEqualTo(UPDATED_LEAD_DAYS_INTIMATION);
        assertThat(testAccountDetails.getMailAddressControlFlag()).isEqualTo(UPDATED_MAIL_ADDRESS_CONTROL_FLAG);
        assertThat(testAccountDetails.getMobileFacilityFlag()).isEqualTo(UPDATED_MOBILE_FACILITY_FLAG);
        assertThat(testAccountDetails.getNumberOfCheckWithdrawals()).isEqualTo(UPDATED_NUMBER_OF_CHECK_WITHDRAWALS);
        assertThat(testAccountDetails.getNumberOfPastDueChecks()).isEqualTo(UPDATED_NUMBER_OF_PAST_DUE_CHECKS);
        assertThat(testAccountDetails.getNumberOfStatementCopies()).isEqualTo(UPDATED_NUMBER_OF_STATEMENT_COPIES);
        assertThat(testAccountDetails.getOverdraftLimitAmount()).isEqualTo(UPDATED_OVERDRAFT_LIMIT_AMOUNT);
        assertThat(testAccountDetails.getPointOfSaleFacilityFlag()).isEqualTo(UPDATED_POINT_OF_SALE_FACILITY_FLAG);
        assertThat(testAccountDetails.getStandingInstructionFlag()).isEqualTo(UPDATED_STANDING_INSTRUCTION_FLAG);
        assertThat(testAccountDetails.getSweepoutInstructionFlag()).isEqualTo(UPDATED_SWEEPOUT_INSTRUCTION_FLAG);
        assertThat(testAccountDetails.getAvailableBalance()).isEqualByComparingTo(UPDATED_AVAILABLE_BALANCE);
        assertThat(testAccountDetails.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testAccountDetails.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testAccountDetails.getBranchShortName()).isEqualTo(UPDATED_BRANCH_SHORT_NAME);
        assertThat(testAccountDetails.getCreditsMonthTillDateAmount()).isEqualTo(UPDATED_CREDITS_MONTH_TILL_DATE_AMOUNT);
        assertThat(testAccountDetails.getCurrentBalance()).isEqualTo(UPDATED_CURRENT_BALANCE);
        assertThat(testAccountDetails.getCustomerNumber()).isEqualTo(UPDATED_CUSTOMER_NUMBER);
        assertThat(testAccountDetails.getIsAbsaCustomer()).isEqualTo(UPDATED_IS_ABSA_CUSTOMER);
        assertThat(testAccountDetails.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testAccountDetails.getAccountOpeningDate()).isEqualTo(UPDATED_ACCOUNT_OPENING_DATE);
        assertThat(testAccountDetails.getStatementPeriodStartDate()).isEqualTo(UPDATED_STATEMENT_PERIOD_START_DATE);
        assertThat(testAccountDetails.getStatementPeriodEndDate()).isEqualTo(UPDATED_STATEMENT_PERIOD_END_DATE);
        assertThat(testAccountDetails.getDebitsLastDate()).isEqualTo(UPDATED_DEBITS_LAST_DATE);
        assertThat(testAccountDetails.getCreditLastDate()).isEqualTo(UPDATED_CREDIT_LAST_DATE);
        assertThat(testAccountDetails.getDebitsMonthTillDateAmount()).isEqualTo(UPDATED_DEBITS_MONTH_TILL_DATE_AMOUNT);
        assertThat(testAccountDetails.getDebitsYearTillDateAmount()).isEqualByComparingTo(UPDATED_DEBITS_YEAR_TILL_DATE_AMOUNT);
        assertThat(testAccountDetails.getCreditInterestAccruedAmount()).isEqualByComparingTo(UPDATED_CREDIT_INTEREST_ACCRUED_AMOUNT);
        assertThat(testAccountDetails.getDebitInterestAccruedAmount()).isEqualByComparingTo(UPDATED_DEBIT_INTEREST_ACCRUED_AMOUNT);
        assertThat(testAccountDetails.getAdjustedCreditInterestAccrued()).isEqualByComparingTo(UPDATED_ADJUSTED_CREDIT_INTEREST_ACCRUED);
        assertThat(testAccountDetails.getAdjustedDebitInterestAccrued()).isEqualByComparingTo(UPDATED_ADJUSTED_DEBIT_INTEREST_ACCRUED);
        assertThat(testAccountDetails.getProjectedTaxOnAccruedInterestAmount())
            .isEqualByComparingTo(UPDATED_PROJECTED_TAX_ON_ACCRUED_INTEREST_AMOUNT);
        assertThat(testAccountDetails.getInterestAccruedInCurrentFinancialYear())
            .isEqualTo(UPDATED_INTEREST_ACCRUED_IN_CURRENT_FINANCIAL_YEAR);
        assertThat(testAccountDetails.getIssuedInventoryPropertyType()).isEqualTo(UPDATED_ISSUED_INVENTORY_PROPERTY_TYPE);
        assertThat(testAccountDetails.getLastIssuedCheckNumber()).isEqualTo(UPDATED_LAST_ISSUED_CHECK_NUMBER);
        assertThat(testAccountDetails.getLanguageCode()).isEqualTo(UPDATED_LANGUAGE_CODE);
        assertThat(testAccountDetails.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
        assertThat(testAccountDetails.getMinimumRequiredBalanceAmount()).isEqualByComparingTo(UPDATED_MINIMUM_REQUIRED_BALANCE_AMOUNT);
        assertThat(testAccountDetails.getMinimumRequiredTradingBalanceAmount())
            .isEqualByComparingTo(UPDATED_MINIMUM_REQUIRED_TRADING_BALANCE_AMOUNT);
        assertThat(testAccountDetails.getMtdCreditsCount()).isEqualTo(UPDATED_MTD_CREDITS_COUNT);
        assertThat(testAccountDetails.getMtdDebitsCount()).isEqualTo(UPDATED_MTD_DEBITS_COUNT);
        assertThat(testAccountDetails.getNetAvailableBalanceForWithdrawal()).isEqualTo(UPDATED_NET_AVAILABLE_BALANCE_FOR_WITHDRAWAL);
        assertThat(testAccountDetails.getNetBalanceAmount()).isEqualTo(UPDATED_NET_BALANCE_AMOUNT);
        assertThat(testAccountDetails.getPassbookLifecycleStatusCode()).isEqualTo(UPDATED_PASSBOOK_LIFECYCLE_STATUS_CODE);
        assertThat(testAccountDetails.getPeriodicAverageBalanceAmount()).isEqualByComparingTo(UPDATED_PERIODIC_AVERAGE_BALANCE_AMOUNT);
        assertThat(testAccountDetails.getPreviousDayClosingBookBalance()).isEqualByComparingTo(UPDATED_PREVIOUS_DAY_CLOSING_BOOK_BALANCE);
        assertThat(testAccountDetails.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testAccountDetails.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testAccountDetails.getRestrictedAccountFlag()).isEqualTo(UPDATED_RESTRICTED_ACCOUNT_FLAG);
        assertThat(testAccountDetails.getStaffFlag()).isEqualTo(UPDATED_STAFF_FLAG);
        assertThat(testAccountDetails.getSweepinAmountOnLien()).isEqualTo(UPDATED_SWEEPIN_AMOUNT_ON_LIEN);
        assertThat(testAccountDetails.getTaxCode1()).isEqualTo(UPDATED_TAX_CODE_1);
        assertThat(testAccountDetails.getTaxCode2()).isEqualTo(UPDATED_TAX_CODE_2);
        assertThat(testAccountDetails.getTdsExemptionLimitAmount1()).isEqualTo(UPDATED_TDS_EXEMPTION_LIMIT_AMOUNT_1);
        assertThat(testAccountDetails.getTdsExemptionLimitAmount2()).isEqualTo(UPDATED_TDS_EXEMPTION_LIMIT_AMOUNT_2);
        assertThat(testAccountDetails.getTotalCASAHoldAmount()).isEqualByComparingTo(UPDATED_TOTAL_CASA_HOLD_AMOUNT);
        assertThat(testAccountDetails.getTotalUnclearFundAmount()).isEqualByComparingTo(UPDATED_TOTAL_UNCLEAR_FUND_AMOUNT);
        assertThat(testAccountDetails.getYtdCreditLastAmount()).isEqualTo(UPDATED_YTD_CREDIT_LAST_AMOUNT);
        assertThat(testAccountDetails.getYtdCreditsCount()).isEqualTo(UPDATED_YTD_CREDITS_COUNT);
        assertThat(testAccountDetails.getYtdDebitsCount()).isEqualTo(UPDATED_YTD_DEBITS_COUNT);
        assertThat(testAccountDetails.getYtdDebitsLastAmount()).isEqualTo(UPDATED_YTD_DEBITS_LAST_AMOUNT);
        assertThat(testAccountDetails.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testAccountDetails.getSourceInfo()).isEqualTo(UPDATED_SOURCE_INFO);
        assertThat(testAccountDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAccountDetails.getCustFreeTextField1()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_1);
        assertThat(testAccountDetails.getCustFreeTextField2()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_2);
        assertThat(testAccountDetails.getCustFreeTextField3()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_3);
        assertThat(testAccountDetails.getCustFreeTextField4()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_4);
        assertThat(testAccountDetails.getCustFreeTextField5()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_5);
        assertThat(testAccountDetails.getCustFreeTextField6()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_6);
        assertThat(testAccountDetails.getCustFreeTextField7()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_7);
        assertThat(testAccountDetails.getCustFreeTextField8()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_8);
        assertThat(testAccountDetails.getCustFreeTextField9()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_9);
        assertThat(testAccountDetails.getCustFreeTextField10()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_10);
        assertThat(testAccountDetails.getCustFreeTextField11()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_11);
        assertThat(testAccountDetails.getCustFreeTextField12()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_12);
        assertThat(testAccountDetails.getCustFreeTextField13()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_13);
        assertThat(testAccountDetails.getCustFreeTextField14()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_14);
        assertThat(testAccountDetails.getCustFreeTextField15()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_15);
        assertThat(testAccountDetails.getCustFreeTextField16()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_16);
        assertThat(testAccountDetails.getCustFreeTextField17()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_17);
        assertThat(testAccountDetails.getCustFreeTextField18()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_18);
        assertThat(testAccountDetails.getCustFreeTextField19()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_19);
        assertThat(testAccountDetails.getCustFreeTextField20()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_20);
        assertThat(testAccountDetails.getCustFreeTextField21()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_21);
        assertThat(testAccountDetails.getCustFreeTextField22()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_22);
        assertThat(testAccountDetails.getCustFreeTextField23()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_23);
        assertThat(testAccountDetails.getCustFreeTextField24()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_24);
        assertThat(testAccountDetails.getCustFreeTextField25()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_25);
        assertThat(testAccountDetails.getCustFreeTextField26()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_26);
        assertThat(testAccountDetails.getCustFreeTextField27()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_27);
        assertThat(testAccountDetails.getCustFreeTextField28()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_28);
        assertThat(testAccountDetails.getCustFreeTextField29()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_29);
        assertThat(testAccountDetails.getCustFreeTextField30()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_30);
        assertThat(testAccountDetails.getCustFreeTextField31()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_31);
    }

    @Test
    @Transactional
    void putNonExistingAccountDetails() throws Exception {
        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();
        accountDetails.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccountDetails() throws Exception {
        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();
        accountDetails.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccountDetails() throws Exception {
        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();
        accountDetails.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountDetails)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccountDetailsWithPatch() throws Exception {
        // Initialize the database
        accountDetailsRepository.saveAndFlush(accountDetails);

        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();

        // Update the accountDetails using partial update
        AccountDetails partialUpdatedAccountDetails = new AccountDetails();
        partialUpdatedAccountDetails.setId(accountDetails.getId());

        partialUpdatedAccountDetails
            .accountCurrency(UPDATED_ACCOUNT_CURRENCY)
            .accountStatus(UPDATED_ACCOUNT_STATUS)
            .accountLifecycleStatusCode(UPDATED_ACCOUNT_LIFECYCLE_STATUS_CODE)
            .casaAccountStatus(UPDATED_CASA_ACCOUNT_STATUS)
            .adHocStatementFlag(UPDATED_AD_HOC_STATEMENT_FLAG)
            .deferredStmtGenerationDayOfMonth(UPDATED_DEFERRED_STMT_GENERATION_DAY_OF_MONTH)
            .groupBonusInteresFlag(UPDATED_GROUP_BONUS_INTERES_FLAG)
            .holdMailFlag(UPDATED_HOLD_MAIL_FLAG)
            .inwardDirectDebitAuthorizationFlag(UPDATED_INWARD_DIRECT_DEBIT_AUTHORIZATION_FLAG)
            .jointAccountFlag(UPDATED_JOINT_ACCOUNT_FLAG)
            .mailAddressControlFlag(UPDATED_MAIL_ADDRESS_CONTROL_FLAG)
            .mobileFacilityFlag(UPDATED_MOBILE_FACILITY_FLAG)
            .numberOfStatementCopies(UPDATED_NUMBER_OF_STATEMENT_COPIES)
            .standingInstructionFlag(UPDATED_STANDING_INSTRUCTION_FLAG)
            .sweepoutInstructionFlag(UPDATED_SWEEPOUT_INSTRUCTION_FLAG)
            .availableBalance(UPDATED_AVAILABLE_BALANCE)
            .branchCode(UPDATED_BRANCH_CODE)
            .branchName(UPDATED_BRANCH_NAME)
            .currentBalance(UPDATED_CURRENT_BALANCE)
            .fullName(UPDATED_FULL_NAME)
            .accountOpeningDate(UPDATED_ACCOUNT_OPENING_DATE)
            .statementPeriodStartDate(UPDATED_STATEMENT_PERIOD_START_DATE)
            .statementPeriodEndDate(UPDATED_STATEMENT_PERIOD_END_DATE)
            .creditLastDate(UPDATED_CREDIT_LAST_DATE)
            .debitsMonthTillDateAmount(UPDATED_DEBITS_MONTH_TILL_DATE_AMOUNT)
            .debitsYearTillDateAmount(UPDATED_DEBITS_YEAR_TILL_DATE_AMOUNT)
            .creditInterestAccruedAmount(UPDATED_CREDIT_INTEREST_ACCRUED_AMOUNT)
            .debitInterestAccruedAmount(UPDATED_DEBIT_INTEREST_ACCRUED_AMOUNT)
            .projectedTaxOnAccruedInterestAmount(UPDATED_PROJECTED_TAX_ON_ACCRUED_INTEREST_AMOUNT)
            .interestAccruedInCurrentFinancialYear(UPDATED_INTEREST_ACCRUED_IN_CURRENT_FINANCIAL_YEAR)
            .languageCode(UPDATED_LANGUAGE_CODE)
            .lineNumber(UPDATED_LINE_NUMBER)
            .minimumRequiredBalanceAmount(UPDATED_MINIMUM_REQUIRED_BALANCE_AMOUNT)
            .minimumRequiredTradingBalanceAmount(UPDATED_MINIMUM_REQUIRED_TRADING_BALANCE_AMOUNT)
            .mtdCreditsCount(UPDATED_MTD_CREDITS_COUNT)
            .netAvailableBalanceForWithdrawal(UPDATED_NET_AVAILABLE_BALANCE_FOR_WITHDRAWAL)
            .previousDayClosingBookBalance(UPDATED_PREVIOUS_DAY_CLOSING_BOOK_BALANCE)
            .productCode(UPDATED_PRODUCT_CODE)
            .restrictedAccountFlag(UPDATED_RESTRICTED_ACCOUNT_FLAG)
            .sweepinAmountOnLien(UPDATED_SWEEPIN_AMOUNT_ON_LIEN)
            .taxCode2(UPDATED_TAX_CODE_2)
            .tdsExemptionLimitAmount2(UPDATED_TDS_EXEMPTION_LIMIT_AMOUNT_2)
            .totalCASAHoldAmount(UPDATED_TOTAL_CASA_HOLD_AMOUNT)
            .ytdDebitsCount(UPDATED_YTD_DEBITS_COUNT)
            .message(UPDATED_MESSAGE)
            .status(UPDATED_STATUS)
            .custFreeTextField2(UPDATED_CUST_FREE_TEXT_FIELD_2)
            .custFreeTextField3(UPDATED_CUST_FREE_TEXT_FIELD_3)
            .custFreeTextField8(UPDATED_CUST_FREE_TEXT_FIELD_8)
            .custFreeTextField9(UPDATED_CUST_FREE_TEXT_FIELD_9)
            .custFreeTextField10(UPDATED_CUST_FREE_TEXT_FIELD_10)
            .custFreeTextField11(UPDATED_CUST_FREE_TEXT_FIELD_11)
            .custFreeTextField12(UPDATED_CUST_FREE_TEXT_FIELD_12)
            .custFreeTextField13(UPDATED_CUST_FREE_TEXT_FIELD_13)
            .custFreeTextField15(UPDATED_CUST_FREE_TEXT_FIELD_15)
            .custFreeTextField19(UPDATED_CUST_FREE_TEXT_FIELD_19)
            .custFreeTextField21(UPDATED_CUST_FREE_TEXT_FIELD_21)
            .custFreeTextField22(UPDATED_CUST_FREE_TEXT_FIELD_22)
            .custFreeTextField24(UPDATED_CUST_FREE_TEXT_FIELD_24)
            .custFreeTextField28(UPDATED_CUST_FREE_TEXT_FIELD_28)
            .custFreeTextField30(UPDATED_CUST_FREE_TEXT_FIELD_30);

        restAccountDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountDetails))
            )
            .andExpect(status().isOk());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
        AccountDetails testAccountDetails = accountDetailsList.get(accountDetailsList.size() - 1);
        assertThat(testAccountDetails.getAccountCurrency()).isEqualTo(UPDATED_ACCOUNT_CURRENCY);
        assertThat(testAccountDetails.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testAccountDetails.getAccountStatus()).isEqualTo(UPDATED_ACCOUNT_STATUS);
        assertThat(testAccountDetails.getAccountLifecycleStatusCode()).isEqualTo(UPDATED_ACCOUNT_LIFECYCLE_STATUS_CODE);
        assertThat(testAccountDetails.getAccrualStatusCode()).isEqualTo(DEFAULT_ACCRUAL_STATUS_CODE);
        assertThat(testAccountDetails.getCasaAccountStatus()).isEqualTo(UPDATED_CASA_ACCOUNT_STATUS);
        assertThat(testAccountDetails.getMinorAccountStatusCode()).isEqualTo(DEFAULT_MINOR_ACCOUNT_STATUS_CODE);
        assertThat(testAccountDetails.getAccountTitle()).isEqualTo(DEFAULT_ACCOUNT_TITLE);
        assertThat(testAccountDetails.getAdvanceAgainstUnclearedFunds()).isEqualByComparingTo(DEFAULT_ADVANCE_AGAINST_UNCLEARED_FUNDS);
        assertThat(testAccountDetails.getAdHocStatementFlag()).isEqualTo(UPDATED_AD_HOC_STATEMENT_FLAG);
        assertThat(testAccountDetails.getAdditionalAddressFlag()).isEqualTo(DEFAULT_ADDITIONAL_ADDRESS_FLAG);
        assertThat(testAccountDetails.getAtmFacilityFlag()).isEqualTo(DEFAULT_ATM_FACILITY_FLAG);
        assertThat(testAccountDetails.getCheckReorderThresholdNumber()).isEqualByComparingTo(DEFAULT_CHECK_REORDER_THRESHOLD_NUMBER);
        assertThat(testAccountDetails.getDeferredStmtGenerationDayOfMonth())
            .isEqualByComparingTo(UPDATED_DEFERRED_STMT_GENERATION_DAY_OF_MONTH);
        assertThat(testAccountDetails.getGenerateRateChangeIntimationFlag()).isEqualTo(DEFAULT_GENERATE_RATE_CHANGE_INTIMATION_FLAG);
        assertThat(testAccountDetails.getGroupBonusInteresFlag()).isEqualTo(UPDATED_GROUP_BONUS_INTERES_FLAG);
        assertThat(testAccountDetails.getHoldMailFlag()).isEqualTo(UPDATED_HOLD_MAIL_FLAG);
        assertThat(testAccountDetails.getInterestWaiverFlag()).isEqualTo(DEFAULT_INTEREST_WAIVER_FLAG);
        assertThat(testAccountDetails.getInternetBankingAccessFlag()).isEqualTo(DEFAULT_INTERNET_BANKING_ACCESS_FLAG);
        assertThat(testAccountDetails.getInwardDirectDebitAuthorizationFlag()).isEqualTo(UPDATED_INWARD_DIRECT_DEBIT_AUTHORIZATION_FLAG);
        assertThat(testAccountDetails.getJointAccountFlag()).isEqualTo(UPDATED_JOINT_ACCOUNT_FLAG);
        assertThat(testAccountDetails.getLeadDaysIntimation()).isEqualTo(DEFAULT_LEAD_DAYS_INTIMATION);
        assertThat(testAccountDetails.getMailAddressControlFlag()).isEqualTo(UPDATED_MAIL_ADDRESS_CONTROL_FLAG);
        assertThat(testAccountDetails.getMobileFacilityFlag()).isEqualTo(UPDATED_MOBILE_FACILITY_FLAG);
        assertThat(testAccountDetails.getNumberOfCheckWithdrawals()).isEqualTo(DEFAULT_NUMBER_OF_CHECK_WITHDRAWALS);
        assertThat(testAccountDetails.getNumberOfPastDueChecks()).isEqualTo(DEFAULT_NUMBER_OF_PAST_DUE_CHECKS);
        assertThat(testAccountDetails.getNumberOfStatementCopies()).isEqualTo(UPDATED_NUMBER_OF_STATEMENT_COPIES);
        assertThat(testAccountDetails.getOverdraftLimitAmount()).isEqualTo(DEFAULT_OVERDRAFT_LIMIT_AMOUNT);
        assertThat(testAccountDetails.getPointOfSaleFacilityFlag()).isEqualTo(DEFAULT_POINT_OF_SALE_FACILITY_FLAG);
        assertThat(testAccountDetails.getStandingInstructionFlag()).isEqualTo(UPDATED_STANDING_INSTRUCTION_FLAG);
        assertThat(testAccountDetails.getSweepoutInstructionFlag()).isEqualTo(UPDATED_SWEEPOUT_INSTRUCTION_FLAG);
        assertThat(testAccountDetails.getAvailableBalance()).isEqualByComparingTo(UPDATED_AVAILABLE_BALANCE);
        assertThat(testAccountDetails.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testAccountDetails.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testAccountDetails.getBranchShortName()).isEqualTo(DEFAULT_BRANCH_SHORT_NAME);
        assertThat(testAccountDetails.getCreditsMonthTillDateAmount()).isEqualTo(DEFAULT_CREDITS_MONTH_TILL_DATE_AMOUNT);
        assertThat(testAccountDetails.getCurrentBalance()).isEqualTo(UPDATED_CURRENT_BALANCE);
        assertThat(testAccountDetails.getCustomerNumber()).isEqualTo(DEFAULT_CUSTOMER_NUMBER);
        assertThat(testAccountDetails.getIsAbsaCustomer()).isEqualTo(DEFAULT_IS_ABSA_CUSTOMER);
        assertThat(testAccountDetails.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testAccountDetails.getAccountOpeningDate()).isEqualTo(UPDATED_ACCOUNT_OPENING_DATE);
        assertThat(testAccountDetails.getStatementPeriodStartDate()).isEqualTo(UPDATED_STATEMENT_PERIOD_START_DATE);
        assertThat(testAccountDetails.getStatementPeriodEndDate()).isEqualTo(UPDATED_STATEMENT_PERIOD_END_DATE);
        assertThat(testAccountDetails.getDebitsLastDate()).isEqualTo(DEFAULT_DEBITS_LAST_DATE);
        assertThat(testAccountDetails.getCreditLastDate()).isEqualTo(UPDATED_CREDIT_LAST_DATE);
        assertThat(testAccountDetails.getDebitsMonthTillDateAmount()).isEqualTo(UPDATED_DEBITS_MONTH_TILL_DATE_AMOUNT);
        assertThat(testAccountDetails.getDebitsYearTillDateAmount()).isEqualByComparingTo(UPDATED_DEBITS_YEAR_TILL_DATE_AMOUNT);
        assertThat(testAccountDetails.getCreditInterestAccruedAmount()).isEqualByComparingTo(UPDATED_CREDIT_INTEREST_ACCRUED_AMOUNT);
        assertThat(testAccountDetails.getDebitInterestAccruedAmount()).isEqualByComparingTo(UPDATED_DEBIT_INTEREST_ACCRUED_AMOUNT);
        assertThat(testAccountDetails.getAdjustedCreditInterestAccrued()).isEqualByComparingTo(DEFAULT_ADJUSTED_CREDIT_INTEREST_ACCRUED);
        assertThat(testAccountDetails.getAdjustedDebitInterestAccrued()).isEqualByComparingTo(DEFAULT_ADJUSTED_DEBIT_INTEREST_ACCRUED);
        assertThat(testAccountDetails.getProjectedTaxOnAccruedInterestAmount())
            .isEqualByComparingTo(UPDATED_PROJECTED_TAX_ON_ACCRUED_INTEREST_AMOUNT);
        assertThat(testAccountDetails.getInterestAccruedInCurrentFinancialYear())
            .isEqualTo(UPDATED_INTEREST_ACCRUED_IN_CURRENT_FINANCIAL_YEAR);
        assertThat(testAccountDetails.getIssuedInventoryPropertyType()).isEqualTo(DEFAULT_ISSUED_INVENTORY_PROPERTY_TYPE);
        assertThat(testAccountDetails.getLastIssuedCheckNumber()).isEqualTo(DEFAULT_LAST_ISSUED_CHECK_NUMBER);
        assertThat(testAccountDetails.getLanguageCode()).isEqualTo(UPDATED_LANGUAGE_CODE);
        assertThat(testAccountDetails.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
        assertThat(testAccountDetails.getMinimumRequiredBalanceAmount()).isEqualByComparingTo(UPDATED_MINIMUM_REQUIRED_BALANCE_AMOUNT);
        assertThat(testAccountDetails.getMinimumRequiredTradingBalanceAmount())
            .isEqualByComparingTo(UPDATED_MINIMUM_REQUIRED_TRADING_BALANCE_AMOUNT);
        assertThat(testAccountDetails.getMtdCreditsCount()).isEqualTo(UPDATED_MTD_CREDITS_COUNT);
        assertThat(testAccountDetails.getMtdDebitsCount()).isEqualTo(DEFAULT_MTD_DEBITS_COUNT);
        assertThat(testAccountDetails.getNetAvailableBalanceForWithdrawal()).isEqualTo(UPDATED_NET_AVAILABLE_BALANCE_FOR_WITHDRAWAL);
        assertThat(testAccountDetails.getNetBalanceAmount()).isEqualTo(DEFAULT_NET_BALANCE_AMOUNT);
        assertThat(testAccountDetails.getPassbookLifecycleStatusCode()).isEqualTo(DEFAULT_PASSBOOK_LIFECYCLE_STATUS_CODE);
        assertThat(testAccountDetails.getPeriodicAverageBalanceAmount()).isEqualByComparingTo(DEFAULT_PERIODIC_AVERAGE_BALANCE_AMOUNT);
        assertThat(testAccountDetails.getPreviousDayClosingBookBalance()).isEqualByComparingTo(UPDATED_PREVIOUS_DAY_CLOSING_BOOK_BALANCE);
        assertThat(testAccountDetails.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testAccountDetails.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testAccountDetails.getRestrictedAccountFlag()).isEqualTo(UPDATED_RESTRICTED_ACCOUNT_FLAG);
        assertThat(testAccountDetails.getStaffFlag()).isEqualTo(DEFAULT_STAFF_FLAG);
        assertThat(testAccountDetails.getSweepinAmountOnLien()).isEqualTo(UPDATED_SWEEPIN_AMOUNT_ON_LIEN);
        assertThat(testAccountDetails.getTaxCode1()).isEqualTo(DEFAULT_TAX_CODE_1);
        assertThat(testAccountDetails.getTaxCode2()).isEqualTo(UPDATED_TAX_CODE_2);
        assertThat(testAccountDetails.getTdsExemptionLimitAmount1()).isEqualTo(DEFAULT_TDS_EXEMPTION_LIMIT_AMOUNT_1);
        assertThat(testAccountDetails.getTdsExemptionLimitAmount2()).isEqualTo(UPDATED_TDS_EXEMPTION_LIMIT_AMOUNT_2);
        assertThat(testAccountDetails.getTotalCASAHoldAmount()).isEqualByComparingTo(UPDATED_TOTAL_CASA_HOLD_AMOUNT);
        assertThat(testAccountDetails.getTotalUnclearFundAmount()).isEqualByComparingTo(DEFAULT_TOTAL_UNCLEAR_FUND_AMOUNT);
        assertThat(testAccountDetails.getYtdCreditLastAmount()).isEqualTo(DEFAULT_YTD_CREDIT_LAST_AMOUNT);
        assertThat(testAccountDetails.getYtdCreditsCount()).isEqualTo(DEFAULT_YTD_CREDITS_COUNT);
        assertThat(testAccountDetails.getYtdDebitsCount()).isEqualTo(UPDATED_YTD_DEBITS_COUNT);
        assertThat(testAccountDetails.getYtdDebitsLastAmount()).isEqualTo(DEFAULT_YTD_DEBITS_LAST_AMOUNT);
        assertThat(testAccountDetails.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testAccountDetails.getSourceInfo()).isEqualTo(DEFAULT_SOURCE_INFO);
        assertThat(testAccountDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAccountDetails.getCustFreeTextField1()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_1);
        assertThat(testAccountDetails.getCustFreeTextField2()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_2);
        assertThat(testAccountDetails.getCustFreeTextField3()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_3);
        assertThat(testAccountDetails.getCustFreeTextField4()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_4);
        assertThat(testAccountDetails.getCustFreeTextField5()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_5);
        assertThat(testAccountDetails.getCustFreeTextField6()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_6);
        assertThat(testAccountDetails.getCustFreeTextField7()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_7);
        assertThat(testAccountDetails.getCustFreeTextField8()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_8);
        assertThat(testAccountDetails.getCustFreeTextField9()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_9);
        assertThat(testAccountDetails.getCustFreeTextField10()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_10);
        assertThat(testAccountDetails.getCustFreeTextField11()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_11);
        assertThat(testAccountDetails.getCustFreeTextField12()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_12);
        assertThat(testAccountDetails.getCustFreeTextField13()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_13);
        assertThat(testAccountDetails.getCustFreeTextField14()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_14);
        assertThat(testAccountDetails.getCustFreeTextField15()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_15);
        assertThat(testAccountDetails.getCustFreeTextField16()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_16);
        assertThat(testAccountDetails.getCustFreeTextField17()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_17);
        assertThat(testAccountDetails.getCustFreeTextField18()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_18);
        assertThat(testAccountDetails.getCustFreeTextField19()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_19);
        assertThat(testAccountDetails.getCustFreeTextField20()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_20);
        assertThat(testAccountDetails.getCustFreeTextField21()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_21);
        assertThat(testAccountDetails.getCustFreeTextField22()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_22);
        assertThat(testAccountDetails.getCustFreeTextField23()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_23);
        assertThat(testAccountDetails.getCustFreeTextField24()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_24);
        assertThat(testAccountDetails.getCustFreeTextField25()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_25);
        assertThat(testAccountDetails.getCustFreeTextField26()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_26);
        assertThat(testAccountDetails.getCustFreeTextField27()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_27);
        assertThat(testAccountDetails.getCustFreeTextField28()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_28);
        assertThat(testAccountDetails.getCustFreeTextField29()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_29);
        assertThat(testAccountDetails.getCustFreeTextField30()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_30);
        assertThat(testAccountDetails.getCustFreeTextField31()).isEqualTo(DEFAULT_CUST_FREE_TEXT_FIELD_31);
    }

    @Test
    @Transactional
    void fullUpdateAccountDetailsWithPatch() throws Exception {
        // Initialize the database
        accountDetailsRepository.saveAndFlush(accountDetails);

        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();

        // Update the accountDetails using partial update
        AccountDetails partialUpdatedAccountDetails = new AccountDetails();
        partialUpdatedAccountDetails.setId(accountDetails.getId());

        partialUpdatedAccountDetails
            .accountCurrency(UPDATED_ACCOUNT_CURRENCY)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountStatus(UPDATED_ACCOUNT_STATUS)
            .accountLifecycleStatusCode(UPDATED_ACCOUNT_LIFECYCLE_STATUS_CODE)
            .accrualStatusCode(UPDATED_ACCRUAL_STATUS_CODE)
            .casaAccountStatus(UPDATED_CASA_ACCOUNT_STATUS)
            .minorAccountStatusCode(UPDATED_MINOR_ACCOUNT_STATUS_CODE)
            .accountTitle(UPDATED_ACCOUNT_TITLE)
            .advanceAgainstUnclearedFunds(UPDATED_ADVANCE_AGAINST_UNCLEARED_FUNDS)
            .adHocStatementFlag(UPDATED_AD_HOC_STATEMENT_FLAG)
            .additionalAddressFlag(UPDATED_ADDITIONAL_ADDRESS_FLAG)
            .atmFacilityFlag(UPDATED_ATM_FACILITY_FLAG)
            .checkReorderThresholdNumber(UPDATED_CHECK_REORDER_THRESHOLD_NUMBER)
            .deferredStmtGenerationDayOfMonth(UPDATED_DEFERRED_STMT_GENERATION_DAY_OF_MONTH)
            .generateRateChangeIntimationFlag(UPDATED_GENERATE_RATE_CHANGE_INTIMATION_FLAG)
            .groupBonusInteresFlag(UPDATED_GROUP_BONUS_INTERES_FLAG)
            .holdMailFlag(UPDATED_HOLD_MAIL_FLAG)
            .interestWaiverFlag(UPDATED_INTEREST_WAIVER_FLAG)
            .internetBankingAccessFlag(UPDATED_INTERNET_BANKING_ACCESS_FLAG)
            .inwardDirectDebitAuthorizationFlag(UPDATED_INWARD_DIRECT_DEBIT_AUTHORIZATION_FLAG)
            .jointAccountFlag(UPDATED_JOINT_ACCOUNT_FLAG)
            .leadDaysIntimation(UPDATED_LEAD_DAYS_INTIMATION)
            .mailAddressControlFlag(UPDATED_MAIL_ADDRESS_CONTROL_FLAG)
            .mobileFacilityFlag(UPDATED_MOBILE_FACILITY_FLAG)
            .numberOfCheckWithdrawals(UPDATED_NUMBER_OF_CHECK_WITHDRAWALS)
            .numberOfPastDueChecks(UPDATED_NUMBER_OF_PAST_DUE_CHECKS)
            .numberOfStatementCopies(UPDATED_NUMBER_OF_STATEMENT_COPIES)
            .overdraftLimitAmount(UPDATED_OVERDRAFT_LIMIT_AMOUNT)
            .pointOfSaleFacilityFlag(UPDATED_POINT_OF_SALE_FACILITY_FLAG)
            .standingInstructionFlag(UPDATED_STANDING_INSTRUCTION_FLAG)
            .sweepoutInstructionFlag(UPDATED_SWEEPOUT_INSTRUCTION_FLAG)
            .availableBalance(UPDATED_AVAILABLE_BALANCE)
            .branchCode(UPDATED_BRANCH_CODE)
            .branchName(UPDATED_BRANCH_NAME)
            .branchShortName(UPDATED_BRANCH_SHORT_NAME)
            .creditsMonthTillDateAmount(UPDATED_CREDITS_MONTH_TILL_DATE_AMOUNT)
            .currentBalance(UPDATED_CURRENT_BALANCE)
            .customerNumber(UPDATED_CUSTOMER_NUMBER)
            .isAbsaCustomer(UPDATED_IS_ABSA_CUSTOMER)
            .fullName(UPDATED_FULL_NAME)
            .accountOpeningDate(UPDATED_ACCOUNT_OPENING_DATE)
            .statementPeriodStartDate(UPDATED_STATEMENT_PERIOD_START_DATE)
            .statementPeriodEndDate(UPDATED_STATEMENT_PERIOD_END_DATE)
            .debitsLastDate(UPDATED_DEBITS_LAST_DATE)
            .creditLastDate(UPDATED_CREDIT_LAST_DATE)
            .debitsMonthTillDateAmount(UPDATED_DEBITS_MONTH_TILL_DATE_AMOUNT)
            .debitsYearTillDateAmount(UPDATED_DEBITS_YEAR_TILL_DATE_AMOUNT)
            .creditInterestAccruedAmount(UPDATED_CREDIT_INTEREST_ACCRUED_AMOUNT)
            .debitInterestAccruedAmount(UPDATED_DEBIT_INTEREST_ACCRUED_AMOUNT)
            .adjustedCreditInterestAccrued(UPDATED_ADJUSTED_CREDIT_INTEREST_ACCRUED)
            .adjustedDebitInterestAccrued(UPDATED_ADJUSTED_DEBIT_INTEREST_ACCRUED)
            .projectedTaxOnAccruedInterestAmount(UPDATED_PROJECTED_TAX_ON_ACCRUED_INTEREST_AMOUNT)
            .interestAccruedInCurrentFinancialYear(UPDATED_INTEREST_ACCRUED_IN_CURRENT_FINANCIAL_YEAR)
            .issuedInventoryPropertyType(UPDATED_ISSUED_INVENTORY_PROPERTY_TYPE)
            .lastIssuedCheckNumber(UPDATED_LAST_ISSUED_CHECK_NUMBER)
            .languageCode(UPDATED_LANGUAGE_CODE)
            .lineNumber(UPDATED_LINE_NUMBER)
            .minimumRequiredBalanceAmount(UPDATED_MINIMUM_REQUIRED_BALANCE_AMOUNT)
            .minimumRequiredTradingBalanceAmount(UPDATED_MINIMUM_REQUIRED_TRADING_BALANCE_AMOUNT)
            .mtdCreditsCount(UPDATED_MTD_CREDITS_COUNT)
            .mtdDebitsCount(UPDATED_MTD_DEBITS_COUNT)
            .netAvailableBalanceForWithdrawal(UPDATED_NET_AVAILABLE_BALANCE_FOR_WITHDRAWAL)
            .netBalanceAmount(UPDATED_NET_BALANCE_AMOUNT)
            .passbookLifecycleStatusCode(UPDATED_PASSBOOK_LIFECYCLE_STATUS_CODE)
            .periodicAverageBalanceAmount(UPDATED_PERIODIC_AVERAGE_BALANCE_AMOUNT)
            .previousDayClosingBookBalance(UPDATED_PREVIOUS_DAY_CLOSING_BOOK_BALANCE)
            .productCode(UPDATED_PRODUCT_CODE)
            .productName(UPDATED_PRODUCT_NAME)
            .restrictedAccountFlag(UPDATED_RESTRICTED_ACCOUNT_FLAG)
            .staffFlag(UPDATED_STAFF_FLAG)
            .sweepinAmountOnLien(UPDATED_SWEEPIN_AMOUNT_ON_LIEN)
            .taxCode1(UPDATED_TAX_CODE_1)
            .taxCode2(UPDATED_TAX_CODE_2)
            .tdsExemptionLimitAmount1(UPDATED_TDS_EXEMPTION_LIMIT_AMOUNT_1)
            .tdsExemptionLimitAmount2(UPDATED_TDS_EXEMPTION_LIMIT_AMOUNT_2)
            .totalCASAHoldAmount(UPDATED_TOTAL_CASA_HOLD_AMOUNT)
            .totalUnclearFundAmount(UPDATED_TOTAL_UNCLEAR_FUND_AMOUNT)
            .ytdCreditLastAmount(UPDATED_YTD_CREDIT_LAST_AMOUNT)
            .ytdCreditsCount(UPDATED_YTD_CREDITS_COUNT)
            .ytdDebitsCount(UPDATED_YTD_DEBITS_COUNT)
            .ytdDebitsLastAmount(UPDATED_YTD_DEBITS_LAST_AMOUNT)
            .message(UPDATED_MESSAGE)
            .sourceInfo(UPDATED_SOURCE_INFO)
            .status(UPDATED_STATUS)
            .custFreeTextField1(UPDATED_CUST_FREE_TEXT_FIELD_1)
            .custFreeTextField2(UPDATED_CUST_FREE_TEXT_FIELD_2)
            .custFreeTextField3(UPDATED_CUST_FREE_TEXT_FIELD_3)
            .custFreeTextField4(UPDATED_CUST_FREE_TEXT_FIELD_4)
            .custFreeTextField5(UPDATED_CUST_FREE_TEXT_FIELD_5)
            .custFreeTextField6(UPDATED_CUST_FREE_TEXT_FIELD_6)
            .custFreeTextField7(UPDATED_CUST_FREE_TEXT_FIELD_7)
            .custFreeTextField8(UPDATED_CUST_FREE_TEXT_FIELD_8)
            .custFreeTextField9(UPDATED_CUST_FREE_TEXT_FIELD_9)
            .custFreeTextField10(UPDATED_CUST_FREE_TEXT_FIELD_10)
            .custFreeTextField11(UPDATED_CUST_FREE_TEXT_FIELD_11)
            .custFreeTextField12(UPDATED_CUST_FREE_TEXT_FIELD_12)
            .custFreeTextField13(UPDATED_CUST_FREE_TEXT_FIELD_13)
            .custFreeTextField14(UPDATED_CUST_FREE_TEXT_FIELD_14)
            .custFreeTextField15(UPDATED_CUST_FREE_TEXT_FIELD_15)
            .custFreeTextField16(UPDATED_CUST_FREE_TEXT_FIELD_16)
            .custFreeTextField17(UPDATED_CUST_FREE_TEXT_FIELD_17)
            .custFreeTextField18(UPDATED_CUST_FREE_TEXT_FIELD_18)
            .custFreeTextField19(UPDATED_CUST_FREE_TEXT_FIELD_19)
            .custFreeTextField20(UPDATED_CUST_FREE_TEXT_FIELD_20)
            .custFreeTextField21(UPDATED_CUST_FREE_TEXT_FIELD_21)
            .custFreeTextField22(UPDATED_CUST_FREE_TEXT_FIELD_22)
            .custFreeTextField23(UPDATED_CUST_FREE_TEXT_FIELD_23)
            .custFreeTextField24(UPDATED_CUST_FREE_TEXT_FIELD_24)
            .custFreeTextField25(UPDATED_CUST_FREE_TEXT_FIELD_25)
            .custFreeTextField26(UPDATED_CUST_FREE_TEXT_FIELD_26)
            .custFreeTextField27(UPDATED_CUST_FREE_TEXT_FIELD_27)
            .custFreeTextField28(UPDATED_CUST_FREE_TEXT_FIELD_28)
            .custFreeTextField29(UPDATED_CUST_FREE_TEXT_FIELD_29)
            .custFreeTextField30(UPDATED_CUST_FREE_TEXT_FIELD_30)
            .custFreeTextField31(UPDATED_CUST_FREE_TEXT_FIELD_31);

        restAccountDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountDetails))
            )
            .andExpect(status().isOk());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
        AccountDetails testAccountDetails = accountDetailsList.get(accountDetailsList.size() - 1);
        assertThat(testAccountDetails.getAccountCurrency()).isEqualTo(UPDATED_ACCOUNT_CURRENCY);
        assertThat(testAccountDetails.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testAccountDetails.getAccountStatus()).isEqualTo(UPDATED_ACCOUNT_STATUS);
        assertThat(testAccountDetails.getAccountLifecycleStatusCode()).isEqualTo(UPDATED_ACCOUNT_LIFECYCLE_STATUS_CODE);
        assertThat(testAccountDetails.getAccrualStatusCode()).isEqualTo(UPDATED_ACCRUAL_STATUS_CODE);
        assertThat(testAccountDetails.getCasaAccountStatus()).isEqualTo(UPDATED_CASA_ACCOUNT_STATUS);
        assertThat(testAccountDetails.getMinorAccountStatusCode()).isEqualTo(UPDATED_MINOR_ACCOUNT_STATUS_CODE);
        assertThat(testAccountDetails.getAccountTitle()).isEqualTo(UPDATED_ACCOUNT_TITLE);
        assertThat(testAccountDetails.getAdvanceAgainstUnclearedFunds()).isEqualByComparingTo(UPDATED_ADVANCE_AGAINST_UNCLEARED_FUNDS);
        assertThat(testAccountDetails.getAdHocStatementFlag()).isEqualTo(UPDATED_AD_HOC_STATEMENT_FLAG);
        assertThat(testAccountDetails.getAdditionalAddressFlag()).isEqualTo(UPDATED_ADDITIONAL_ADDRESS_FLAG);
        assertThat(testAccountDetails.getAtmFacilityFlag()).isEqualTo(UPDATED_ATM_FACILITY_FLAG);
        assertThat(testAccountDetails.getCheckReorderThresholdNumber()).isEqualByComparingTo(UPDATED_CHECK_REORDER_THRESHOLD_NUMBER);
        assertThat(testAccountDetails.getDeferredStmtGenerationDayOfMonth())
            .isEqualByComparingTo(UPDATED_DEFERRED_STMT_GENERATION_DAY_OF_MONTH);
        assertThat(testAccountDetails.getGenerateRateChangeIntimationFlag()).isEqualTo(UPDATED_GENERATE_RATE_CHANGE_INTIMATION_FLAG);
        assertThat(testAccountDetails.getGroupBonusInteresFlag()).isEqualTo(UPDATED_GROUP_BONUS_INTERES_FLAG);
        assertThat(testAccountDetails.getHoldMailFlag()).isEqualTo(UPDATED_HOLD_MAIL_FLAG);
        assertThat(testAccountDetails.getInterestWaiverFlag()).isEqualTo(UPDATED_INTEREST_WAIVER_FLAG);
        assertThat(testAccountDetails.getInternetBankingAccessFlag()).isEqualTo(UPDATED_INTERNET_BANKING_ACCESS_FLAG);
        assertThat(testAccountDetails.getInwardDirectDebitAuthorizationFlag()).isEqualTo(UPDATED_INWARD_DIRECT_DEBIT_AUTHORIZATION_FLAG);
        assertThat(testAccountDetails.getJointAccountFlag()).isEqualTo(UPDATED_JOINT_ACCOUNT_FLAG);
        assertThat(testAccountDetails.getLeadDaysIntimation()).isEqualTo(UPDATED_LEAD_DAYS_INTIMATION);
        assertThat(testAccountDetails.getMailAddressControlFlag()).isEqualTo(UPDATED_MAIL_ADDRESS_CONTROL_FLAG);
        assertThat(testAccountDetails.getMobileFacilityFlag()).isEqualTo(UPDATED_MOBILE_FACILITY_FLAG);
        assertThat(testAccountDetails.getNumberOfCheckWithdrawals()).isEqualTo(UPDATED_NUMBER_OF_CHECK_WITHDRAWALS);
        assertThat(testAccountDetails.getNumberOfPastDueChecks()).isEqualTo(UPDATED_NUMBER_OF_PAST_DUE_CHECKS);
        assertThat(testAccountDetails.getNumberOfStatementCopies()).isEqualTo(UPDATED_NUMBER_OF_STATEMENT_COPIES);
        assertThat(testAccountDetails.getOverdraftLimitAmount()).isEqualTo(UPDATED_OVERDRAFT_LIMIT_AMOUNT);
        assertThat(testAccountDetails.getPointOfSaleFacilityFlag()).isEqualTo(UPDATED_POINT_OF_SALE_FACILITY_FLAG);
        assertThat(testAccountDetails.getStandingInstructionFlag()).isEqualTo(UPDATED_STANDING_INSTRUCTION_FLAG);
        assertThat(testAccountDetails.getSweepoutInstructionFlag()).isEqualTo(UPDATED_SWEEPOUT_INSTRUCTION_FLAG);
        assertThat(testAccountDetails.getAvailableBalance()).isEqualByComparingTo(UPDATED_AVAILABLE_BALANCE);
        assertThat(testAccountDetails.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testAccountDetails.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testAccountDetails.getBranchShortName()).isEqualTo(UPDATED_BRANCH_SHORT_NAME);
        assertThat(testAccountDetails.getCreditsMonthTillDateAmount()).isEqualTo(UPDATED_CREDITS_MONTH_TILL_DATE_AMOUNT);
        assertThat(testAccountDetails.getCurrentBalance()).isEqualTo(UPDATED_CURRENT_BALANCE);
        assertThat(testAccountDetails.getCustomerNumber()).isEqualTo(UPDATED_CUSTOMER_NUMBER);
        assertThat(testAccountDetails.getIsAbsaCustomer()).isEqualTo(UPDATED_IS_ABSA_CUSTOMER);
        assertThat(testAccountDetails.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testAccountDetails.getAccountOpeningDate()).isEqualTo(UPDATED_ACCOUNT_OPENING_DATE);
        assertThat(testAccountDetails.getStatementPeriodStartDate()).isEqualTo(UPDATED_STATEMENT_PERIOD_START_DATE);
        assertThat(testAccountDetails.getStatementPeriodEndDate()).isEqualTo(UPDATED_STATEMENT_PERIOD_END_DATE);
        assertThat(testAccountDetails.getDebitsLastDate()).isEqualTo(UPDATED_DEBITS_LAST_DATE);
        assertThat(testAccountDetails.getCreditLastDate()).isEqualTo(UPDATED_CREDIT_LAST_DATE);
        assertThat(testAccountDetails.getDebitsMonthTillDateAmount()).isEqualTo(UPDATED_DEBITS_MONTH_TILL_DATE_AMOUNT);
        assertThat(testAccountDetails.getDebitsYearTillDateAmount()).isEqualByComparingTo(UPDATED_DEBITS_YEAR_TILL_DATE_AMOUNT);
        assertThat(testAccountDetails.getCreditInterestAccruedAmount()).isEqualByComparingTo(UPDATED_CREDIT_INTEREST_ACCRUED_AMOUNT);
        assertThat(testAccountDetails.getDebitInterestAccruedAmount()).isEqualByComparingTo(UPDATED_DEBIT_INTEREST_ACCRUED_AMOUNT);
        assertThat(testAccountDetails.getAdjustedCreditInterestAccrued()).isEqualByComparingTo(UPDATED_ADJUSTED_CREDIT_INTEREST_ACCRUED);
        assertThat(testAccountDetails.getAdjustedDebitInterestAccrued()).isEqualByComparingTo(UPDATED_ADJUSTED_DEBIT_INTEREST_ACCRUED);
        assertThat(testAccountDetails.getProjectedTaxOnAccruedInterestAmount())
            .isEqualByComparingTo(UPDATED_PROJECTED_TAX_ON_ACCRUED_INTEREST_AMOUNT);
        assertThat(testAccountDetails.getInterestAccruedInCurrentFinancialYear())
            .isEqualTo(UPDATED_INTEREST_ACCRUED_IN_CURRENT_FINANCIAL_YEAR);
        assertThat(testAccountDetails.getIssuedInventoryPropertyType()).isEqualTo(UPDATED_ISSUED_INVENTORY_PROPERTY_TYPE);
        assertThat(testAccountDetails.getLastIssuedCheckNumber()).isEqualTo(UPDATED_LAST_ISSUED_CHECK_NUMBER);
        assertThat(testAccountDetails.getLanguageCode()).isEqualTo(UPDATED_LANGUAGE_CODE);
        assertThat(testAccountDetails.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
        assertThat(testAccountDetails.getMinimumRequiredBalanceAmount()).isEqualByComparingTo(UPDATED_MINIMUM_REQUIRED_BALANCE_AMOUNT);
        assertThat(testAccountDetails.getMinimumRequiredTradingBalanceAmount())
            .isEqualByComparingTo(UPDATED_MINIMUM_REQUIRED_TRADING_BALANCE_AMOUNT);
        assertThat(testAccountDetails.getMtdCreditsCount()).isEqualTo(UPDATED_MTD_CREDITS_COUNT);
        assertThat(testAccountDetails.getMtdDebitsCount()).isEqualTo(UPDATED_MTD_DEBITS_COUNT);
        assertThat(testAccountDetails.getNetAvailableBalanceForWithdrawal()).isEqualTo(UPDATED_NET_AVAILABLE_BALANCE_FOR_WITHDRAWAL);
        assertThat(testAccountDetails.getNetBalanceAmount()).isEqualTo(UPDATED_NET_BALANCE_AMOUNT);
        assertThat(testAccountDetails.getPassbookLifecycleStatusCode()).isEqualTo(UPDATED_PASSBOOK_LIFECYCLE_STATUS_CODE);
        assertThat(testAccountDetails.getPeriodicAverageBalanceAmount()).isEqualByComparingTo(UPDATED_PERIODIC_AVERAGE_BALANCE_AMOUNT);
        assertThat(testAccountDetails.getPreviousDayClosingBookBalance()).isEqualByComparingTo(UPDATED_PREVIOUS_DAY_CLOSING_BOOK_BALANCE);
        assertThat(testAccountDetails.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testAccountDetails.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testAccountDetails.getRestrictedAccountFlag()).isEqualTo(UPDATED_RESTRICTED_ACCOUNT_FLAG);
        assertThat(testAccountDetails.getStaffFlag()).isEqualTo(UPDATED_STAFF_FLAG);
        assertThat(testAccountDetails.getSweepinAmountOnLien()).isEqualTo(UPDATED_SWEEPIN_AMOUNT_ON_LIEN);
        assertThat(testAccountDetails.getTaxCode1()).isEqualTo(UPDATED_TAX_CODE_1);
        assertThat(testAccountDetails.getTaxCode2()).isEqualTo(UPDATED_TAX_CODE_2);
        assertThat(testAccountDetails.getTdsExemptionLimitAmount1()).isEqualTo(UPDATED_TDS_EXEMPTION_LIMIT_AMOUNT_1);
        assertThat(testAccountDetails.getTdsExemptionLimitAmount2()).isEqualTo(UPDATED_TDS_EXEMPTION_LIMIT_AMOUNT_2);
        assertThat(testAccountDetails.getTotalCASAHoldAmount()).isEqualByComparingTo(UPDATED_TOTAL_CASA_HOLD_AMOUNT);
        assertThat(testAccountDetails.getTotalUnclearFundAmount()).isEqualByComparingTo(UPDATED_TOTAL_UNCLEAR_FUND_AMOUNT);
        assertThat(testAccountDetails.getYtdCreditLastAmount()).isEqualTo(UPDATED_YTD_CREDIT_LAST_AMOUNT);
        assertThat(testAccountDetails.getYtdCreditsCount()).isEqualTo(UPDATED_YTD_CREDITS_COUNT);
        assertThat(testAccountDetails.getYtdDebitsCount()).isEqualTo(UPDATED_YTD_DEBITS_COUNT);
        assertThat(testAccountDetails.getYtdDebitsLastAmount()).isEqualTo(UPDATED_YTD_DEBITS_LAST_AMOUNT);
        assertThat(testAccountDetails.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testAccountDetails.getSourceInfo()).isEqualTo(UPDATED_SOURCE_INFO);
        assertThat(testAccountDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAccountDetails.getCustFreeTextField1()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_1);
        assertThat(testAccountDetails.getCustFreeTextField2()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_2);
        assertThat(testAccountDetails.getCustFreeTextField3()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_3);
        assertThat(testAccountDetails.getCustFreeTextField4()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_4);
        assertThat(testAccountDetails.getCustFreeTextField5()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_5);
        assertThat(testAccountDetails.getCustFreeTextField6()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_6);
        assertThat(testAccountDetails.getCustFreeTextField7()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_7);
        assertThat(testAccountDetails.getCustFreeTextField8()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_8);
        assertThat(testAccountDetails.getCustFreeTextField9()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_9);
        assertThat(testAccountDetails.getCustFreeTextField10()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_10);
        assertThat(testAccountDetails.getCustFreeTextField11()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_11);
        assertThat(testAccountDetails.getCustFreeTextField12()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_12);
        assertThat(testAccountDetails.getCustFreeTextField13()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_13);
        assertThat(testAccountDetails.getCustFreeTextField14()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_14);
        assertThat(testAccountDetails.getCustFreeTextField15()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_15);
        assertThat(testAccountDetails.getCustFreeTextField16()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_16);
        assertThat(testAccountDetails.getCustFreeTextField17()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_17);
        assertThat(testAccountDetails.getCustFreeTextField18()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_18);
        assertThat(testAccountDetails.getCustFreeTextField19()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_19);
        assertThat(testAccountDetails.getCustFreeTextField20()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_20);
        assertThat(testAccountDetails.getCustFreeTextField21()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_21);
        assertThat(testAccountDetails.getCustFreeTextField22()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_22);
        assertThat(testAccountDetails.getCustFreeTextField23()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_23);
        assertThat(testAccountDetails.getCustFreeTextField24()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_24);
        assertThat(testAccountDetails.getCustFreeTextField25()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_25);
        assertThat(testAccountDetails.getCustFreeTextField26()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_26);
        assertThat(testAccountDetails.getCustFreeTextField27()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_27);
        assertThat(testAccountDetails.getCustFreeTextField28()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_28);
        assertThat(testAccountDetails.getCustFreeTextField29()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_29);
        assertThat(testAccountDetails.getCustFreeTextField30()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_30);
        assertThat(testAccountDetails.getCustFreeTextField31()).isEqualTo(UPDATED_CUST_FREE_TEXT_FIELD_31);
    }

    @Test
    @Transactional
    void patchNonExistingAccountDetails() throws Exception {
        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();
        accountDetails.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accountDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccountDetails() throws Exception {
        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();
        accountDetails.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccountDetails() throws Exception {
        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();
        accountDetails.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(accountDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccountDetails() throws Exception {
        // Initialize the database
        accountDetailsRepository.saveAndFlush(accountDetails);

        int databaseSizeBeforeDelete = accountDetailsRepository.findAll().size();

        // Delete the accountDetails
        restAccountDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, accountDetails.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
