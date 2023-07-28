package ug.co.absa.customer.inquiry.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import ug.co.absa.customer.inquiry.domain.AccountDetails;
import ug.co.absa.customer.inquiry.repository.AccountDetailsRepository;
import ug.co.absa.customer.inquiry.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link ug.co.absa.customer.inquiry.domain.AccountDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AccountDetailsResource {

    private final Logger log = LoggerFactory.getLogger(AccountDetailsResource.class);

    private static final String ENTITY_NAME = "ugdtAccountInquirySrvAccountDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountDetailsRepository accountDetailsRepository;

    public AccountDetailsResource(AccountDetailsRepository accountDetailsRepository) {
        this.accountDetailsRepository = accountDetailsRepository;
    }

    /**
     * {@code POST  /account-details} : Create a new accountDetails.
     *
     * @param accountDetails the accountDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountDetails, or with status {@code 400 (Bad Request)} if the accountDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
   // @PostMapping("/account-details")
    public ResponseEntity<AccountDetails> createAccountDetails(@Valid @RequestBody AccountDetails accountDetails)
        throws URISyntaxException {
        log.debug("REST request to save AccountDetails : {}", accountDetails);
        if (accountDetails.getId() != null) {
            throw new BadRequestAlertException("A new accountDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountDetails result = accountDetailsRepository.save(accountDetails);
        return ResponseEntity
            .created(new URI("/api/account-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-details/:id} : Updates an existing accountDetails.
     *
     * @param id the id of the accountDetails to save.
     * @param accountDetails the accountDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountDetails,
     * or with status {@code 400 (Bad Request)} if the accountDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
   // @PutMapping("/account-details/{id}")
    public ResponseEntity<AccountDetails> updateAccountDetails(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody AccountDetails accountDetails
    ) throws URISyntaxException {
        log.debug("REST request to update AccountDetails : {}, {}", id, accountDetails);
        if (accountDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccountDetails result = accountDetailsRepository.save(accountDetails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /account-details/:id} : Partial updates given fields of an existing accountDetails, field will ignore if it is null
     *
     * @param id the id of the accountDetails to save.
     * @param accountDetails the accountDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountDetails,
     * or with status {@code 400 (Bad Request)} if the accountDetails is not valid,
     * or with status {@code 404 (Not Found)} if the accountDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the accountDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/account-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AccountDetails> partialUpdateAccountDetails(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody AccountDetails accountDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update AccountDetails partially : {}, {}", id, accountDetails);
        if (accountDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccountDetails> result = accountDetailsRepository
            .findById(accountDetails.getId())
            .map(existingAccountDetails -> {
                if (accountDetails.getAccountCurrency() != null) {
                    existingAccountDetails.setAccountCurrency(accountDetails.getAccountCurrency());
                }
                if (accountDetails.getAccountNumber() != null) {
                    existingAccountDetails.setAccountNumber(accountDetails.getAccountNumber());
                }
                if (accountDetails.getAccountStatus() != null) {
                    existingAccountDetails.setAccountStatus(accountDetails.getAccountStatus());
                }
                if (accountDetails.getAccountLifecycleStatusCode() != null) {
                    existingAccountDetails.setAccountLifecycleStatusCode(accountDetails.getAccountLifecycleStatusCode());
                }
                if (accountDetails.getAccrualStatusCode() != null) {
                    existingAccountDetails.setAccrualStatusCode(accountDetails.getAccrualStatusCode());
                }
                if (accountDetails.getCasaAccountStatus() != null) {
                    existingAccountDetails.setCasaAccountStatus(accountDetails.getCasaAccountStatus());
                }
                if (accountDetails.getMinorAccountStatusCode() != null) {
                    existingAccountDetails.setMinorAccountStatusCode(accountDetails.getMinorAccountStatusCode());
                }
                if (accountDetails.getAccountTitle() != null) {
                    existingAccountDetails.setAccountTitle(accountDetails.getAccountTitle());
                }
                if (accountDetails.getAdvanceAgainstUnclearedFunds() != null) {
                    existingAccountDetails.setAdvanceAgainstUnclearedFunds(accountDetails.getAdvanceAgainstUnclearedFunds());
                }
                if (accountDetails.getAdHocStatementFlag() != null) {
                    existingAccountDetails.setAdHocStatementFlag(accountDetails.getAdHocStatementFlag());
                }
                if (accountDetails.getAdditionalAddressFlag() != null) {
                    existingAccountDetails.setAdditionalAddressFlag(accountDetails.getAdditionalAddressFlag());
                }
                if (accountDetails.getAtmFacilityFlag() != null) {
                    existingAccountDetails.setAtmFacilityFlag(accountDetails.getAtmFacilityFlag());
                }
                if (accountDetails.getCheckReorderThresholdNumber() != null) {
                    existingAccountDetails.setCheckReorderThresholdNumber(accountDetails.getCheckReorderThresholdNumber());
                }
                if (accountDetails.getDeferredStmtGenerationDayOfMonth() != null) {
                    existingAccountDetails.setDeferredStmtGenerationDayOfMonth(accountDetails.getDeferredStmtGenerationDayOfMonth());
                }
                if (accountDetails.getGenerateRateChangeIntimationFlag() != null) {
                    existingAccountDetails.setGenerateRateChangeIntimationFlag(accountDetails.getGenerateRateChangeIntimationFlag());
                }
                if (accountDetails.getGroupBonusInteresFlag() != null) {
                    existingAccountDetails.setGroupBonusInteresFlag(accountDetails.getGroupBonusInteresFlag());
                }
                if (accountDetails.getHoldMailFlag() != null) {
                    existingAccountDetails.setHoldMailFlag(accountDetails.getHoldMailFlag());
                }
                if (accountDetails.getInterestWaiverFlag() != null) {
                    existingAccountDetails.setInterestWaiverFlag(accountDetails.getInterestWaiverFlag());
                }
                if (accountDetails.getInternetBankingAccessFlag() != null) {
                    existingAccountDetails.setInternetBankingAccessFlag(accountDetails.getInternetBankingAccessFlag());
                }
                if (accountDetails.getInwardDirectDebitAuthorizationFlag() != null) {
                    existingAccountDetails.setInwardDirectDebitAuthorizationFlag(accountDetails.getInwardDirectDebitAuthorizationFlag());
                }
                if (accountDetails.getJointAccountFlag() != null) {
                    existingAccountDetails.setJointAccountFlag(accountDetails.getJointAccountFlag());
                }
                if (accountDetails.getLeadDaysIntimation() != null) {
                    existingAccountDetails.setLeadDaysIntimation(accountDetails.getLeadDaysIntimation());
                }
                if (accountDetails.getMailAddressControlFlag() != null) {
                    existingAccountDetails.setMailAddressControlFlag(accountDetails.getMailAddressControlFlag());
                }
                if (accountDetails.getMobileFacilityFlag() != null) {
                    existingAccountDetails.setMobileFacilityFlag(accountDetails.getMobileFacilityFlag());
                }
                if (accountDetails.getNumberOfCheckWithdrawals() != null) {
                    existingAccountDetails.setNumberOfCheckWithdrawals(accountDetails.getNumberOfCheckWithdrawals());
                }
                if (accountDetails.getNumberOfPastDueChecks() != null) {
                    existingAccountDetails.setNumberOfPastDueChecks(accountDetails.getNumberOfPastDueChecks());
                }
                if (accountDetails.getNumberOfStatementCopies() != null) {
                    existingAccountDetails.setNumberOfStatementCopies(accountDetails.getNumberOfStatementCopies());
                }
                if (accountDetails.getOverdraftLimitAmount() != null) {
                    existingAccountDetails.setOverdraftLimitAmount(accountDetails.getOverdraftLimitAmount());
                }
                if (accountDetails.getPointOfSaleFacilityFlag() != null) {
                    existingAccountDetails.setPointOfSaleFacilityFlag(accountDetails.getPointOfSaleFacilityFlag());
                }
                if (accountDetails.getStandingInstructionFlag() != null) {
                    existingAccountDetails.setStandingInstructionFlag(accountDetails.getStandingInstructionFlag());
                }
                if (accountDetails.getSweepoutInstructionFlag() != null) {
                    existingAccountDetails.setSweepoutInstructionFlag(accountDetails.getSweepoutInstructionFlag());
                }
                if (accountDetails.getAvailableBalance() != null) {
                    existingAccountDetails.setAvailableBalance(accountDetails.getAvailableBalance());
                }
                if (accountDetails.getBranchCode() != null) {
                    existingAccountDetails.setBranchCode(accountDetails.getBranchCode());
                }
                if (accountDetails.getBranchName() != null) {
                    existingAccountDetails.setBranchName(accountDetails.getBranchName());
                }
                if (accountDetails.getBranchShortName() != null) {
                    existingAccountDetails.setBranchShortName(accountDetails.getBranchShortName());
                }
                if (accountDetails.getCreditsMonthTillDateAmount() != null) {
                    existingAccountDetails.setCreditsMonthTillDateAmount(accountDetails.getCreditsMonthTillDateAmount());
                }
                if (accountDetails.getCurrentBalance() != null) {
                    existingAccountDetails.setCurrentBalance(accountDetails.getCurrentBalance());
                }
                if (accountDetails.getCustomerNumber() != null) {
                    existingAccountDetails.setCustomerNumber(accountDetails.getCustomerNumber());
                }
                if (accountDetails.getIsAbsaCustomer() != null) {
                    existingAccountDetails.setIsAbsaCustomer(accountDetails.getIsAbsaCustomer());
                }
                if (accountDetails.getFullName() != null) {
                    existingAccountDetails.setFullName(accountDetails.getFullName());
                }
                if (accountDetails.getAccountOpeningDate() != null) {
                    existingAccountDetails.setAccountOpeningDate(accountDetails.getAccountOpeningDate());
                }
                if (accountDetails.getStatementPeriodStartDate() != null) {
                    existingAccountDetails.setStatementPeriodStartDate(accountDetails.getStatementPeriodStartDate());
                }
                if (accountDetails.getStatementPeriodEndDate() != null) {
                    existingAccountDetails.setStatementPeriodEndDate(accountDetails.getStatementPeriodEndDate());
                }
                if (accountDetails.getDebitsLastDate() != null) {
                    existingAccountDetails.setDebitsLastDate(accountDetails.getDebitsLastDate());
                }
                if (accountDetails.getCreditLastDate() != null) {
                    existingAccountDetails.setCreditLastDate(accountDetails.getCreditLastDate());
                }
                if (accountDetails.getDebitsMonthTillDateAmount() != null) {
                    existingAccountDetails.setDebitsMonthTillDateAmount(accountDetails.getDebitsMonthTillDateAmount());
                }
                if (accountDetails.getDebitsYearTillDateAmount() != null) {
                    existingAccountDetails.setDebitsYearTillDateAmount(accountDetails.getDebitsYearTillDateAmount());
                }
                if (accountDetails.getCreditInterestAccruedAmount() != null) {
                    existingAccountDetails.setCreditInterestAccruedAmount(accountDetails.getCreditInterestAccruedAmount());
                }
                if (accountDetails.getDebitInterestAccruedAmount() != null) {
                    existingAccountDetails.setDebitInterestAccruedAmount(accountDetails.getDebitInterestAccruedAmount());
                }
                if (accountDetails.getAdjustedCreditInterestAccrued() != null) {
                    existingAccountDetails.setAdjustedCreditInterestAccrued(accountDetails.getAdjustedCreditInterestAccrued());
                }
                if (accountDetails.getAdjustedDebitInterestAccrued() != null) {
                    existingAccountDetails.setAdjustedDebitInterestAccrued(accountDetails.getAdjustedDebitInterestAccrued());
                }
                if (accountDetails.getProjectedTaxOnAccruedInterestAmount() != null) {
                    existingAccountDetails.setProjectedTaxOnAccruedInterestAmount(accountDetails.getProjectedTaxOnAccruedInterestAmount());
                }
                if (accountDetails.getInterestAccruedInCurrentFinancialYear() != null) {
                    existingAccountDetails.setInterestAccruedInCurrentFinancialYear(
                        accountDetails.getInterestAccruedInCurrentFinancialYear()
                    );
                }
                if (accountDetails.getIssuedInventoryPropertyType() != null) {
                    existingAccountDetails.setIssuedInventoryPropertyType(accountDetails.getIssuedInventoryPropertyType());
                }
                if (accountDetails.getLastIssuedCheckNumber() != null) {
                    existingAccountDetails.setLastIssuedCheckNumber(accountDetails.getLastIssuedCheckNumber());
                }
                if (accountDetails.getLanguageCode() != null) {
                    existingAccountDetails.setLanguageCode(accountDetails.getLanguageCode());
                }
                if (accountDetails.getLineNumber() != null) {
                    existingAccountDetails.setLineNumber(accountDetails.getLineNumber());
                }
                if (accountDetails.getMinimumRequiredBalanceAmount() != null) {
                    existingAccountDetails.setMinimumRequiredBalanceAmount(accountDetails.getMinimumRequiredBalanceAmount());
                }
                if (accountDetails.getMinimumRequiredTradingBalanceAmount() != null) {
                    existingAccountDetails.setMinimumRequiredTradingBalanceAmount(accountDetails.getMinimumRequiredTradingBalanceAmount());
                }
                if (accountDetails.getMtdCreditsCount() != null) {
                    existingAccountDetails.setMtdCreditsCount(accountDetails.getMtdCreditsCount());
                }
                if (accountDetails.getMtdDebitsCount() != null) {
                    existingAccountDetails.setMtdDebitsCount(accountDetails.getMtdDebitsCount());
                }
                if (accountDetails.getNetAvailableBalanceForWithdrawal() != null) {
                    existingAccountDetails.setNetAvailableBalanceForWithdrawal(accountDetails.getNetAvailableBalanceForWithdrawal());
                }
                if (accountDetails.getNetBalanceAmount() != null) {
                    existingAccountDetails.setNetBalanceAmount(accountDetails.getNetBalanceAmount());
                }
                if (accountDetails.getPassbookLifecycleStatusCode() != null) {
                    existingAccountDetails.setPassbookLifecycleStatusCode(accountDetails.getPassbookLifecycleStatusCode());
                }
                if (accountDetails.getPeriodicAverageBalanceAmount() != null) {
                    existingAccountDetails.setPeriodicAverageBalanceAmount(accountDetails.getPeriodicAverageBalanceAmount());
                }
                if (accountDetails.getPreviousDayClosingBookBalance() != null) {
                    existingAccountDetails.setPreviousDayClosingBookBalance(accountDetails.getPreviousDayClosingBookBalance());
                }
                if (accountDetails.getProductCode() != null) {
                    existingAccountDetails.setProductCode(accountDetails.getProductCode());
                }
                if (accountDetails.getProductName() != null) {
                    existingAccountDetails.setProductName(accountDetails.getProductName());
                }
                if (accountDetails.getRestrictedAccountFlag() != null) {
                    existingAccountDetails.setRestrictedAccountFlag(accountDetails.getRestrictedAccountFlag());
                }
                if (accountDetails.getStaffFlag() != null) {
                    existingAccountDetails.setStaffFlag(accountDetails.getStaffFlag());
                }
                if (accountDetails.getSweepinAmountOnLien() != null) {
                    existingAccountDetails.setSweepinAmountOnLien(accountDetails.getSweepinAmountOnLien());
                }
                if (accountDetails.getTaxCode1() != null) {
                    existingAccountDetails.setTaxCode1(accountDetails.getTaxCode1());
                }
                if (accountDetails.getTaxCode2() != null) {
                    existingAccountDetails.setTaxCode2(accountDetails.getTaxCode2());
                }
                if (accountDetails.getTdsExemptionLimitAmount1() != null) {
                    existingAccountDetails.setTdsExemptionLimitAmount1(accountDetails.getTdsExemptionLimitAmount1());
                }
                if (accountDetails.getTdsExemptionLimitAmount2() != null) {
                    existingAccountDetails.setTdsExemptionLimitAmount2(accountDetails.getTdsExemptionLimitAmount2());
                }
                if (accountDetails.getTotalCASAHoldAmount() != null) {
                    existingAccountDetails.setTotalCASAHoldAmount(accountDetails.getTotalCASAHoldAmount());
                }
                if (accountDetails.getTotalUnclearFundAmount() != null) {
                    existingAccountDetails.setTotalUnclearFundAmount(accountDetails.getTotalUnclearFundAmount());
                }
                if (accountDetails.getYtdCreditLastAmount() != null) {
                    existingAccountDetails.setYtdCreditLastAmount(accountDetails.getYtdCreditLastAmount());
                }
                if (accountDetails.getYtdCreditsCount() != null) {
                    existingAccountDetails.setYtdCreditsCount(accountDetails.getYtdCreditsCount());
                }
                if (accountDetails.getYtdDebitsCount() != null) {
                    existingAccountDetails.setYtdDebitsCount(accountDetails.getYtdDebitsCount());
                }
                if (accountDetails.getYtdDebitsLastAmount() != null) {
                    existingAccountDetails.setYtdDebitsLastAmount(accountDetails.getYtdDebitsLastAmount());
                }
                if (accountDetails.getMessage() != null) {
                    existingAccountDetails.setMessage(accountDetails.getMessage());
                }
                if (accountDetails.getSourceInfo() != null) {
                    existingAccountDetails.setSourceInfo(accountDetails.getSourceInfo());
                }
                if (accountDetails.getStatus() != null) {
                    existingAccountDetails.setStatus(accountDetails.getStatus());
                }
                if (accountDetails.getCustFreeTextField1() != null) {
                    existingAccountDetails.setCustFreeTextField1(accountDetails.getCustFreeTextField1());
                }
                if (accountDetails.getCustFreeTextField2() != null) {
                    existingAccountDetails.setCustFreeTextField2(accountDetails.getCustFreeTextField2());
                }
                if (accountDetails.getCustFreeTextField3() != null) {
                    existingAccountDetails.setCustFreeTextField3(accountDetails.getCustFreeTextField3());
                }
                if (accountDetails.getCustFreeTextField4() != null) {
                    existingAccountDetails.setCustFreeTextField4(accountDetails.getCustFreeTextField4());
                }
                if (accountDetails.getCustFreeTextField5() != null) {
                    existingAccountDetails.setCustFreeTextField5(accountDetails.getCustFreeTextField5());
                }
                if (accountDetails.getCustFreeTextField6() != null) {
                    existingAccountDetails.setCustFreeTextField6(accountDetails.getCustFreeTextField6());
                }
                if (accountDetails.getCustFreeTextField7() != null) {
                    existingAccountDetails.setCustFreeTextField7(accountDetails.getCustFreeTextField7());
                }
                if (accountDetails.getCustFreeTextField8() != null) {
                    existingAccountDetails.setCustFreeTextField8(accountDetails.getCustFreeTextField8());
                }
                if (accountDetails.getCustFreeTextField9() != null) {
                    existingAccountDetails.setCustFreeTextField9(accountDetails.getCustFreeTextField9());
                }
                if (accountDetails.getCustFreeTextField10() != null) {
                    existingAccountDetails.setCustFreeTextField10(accountDetails.getCustFreeTextField10());
                }
                if (accountDetails.getCustFreeTextField11() != null) {
                    existingAccountDetails.setCustFreeTextField11(accountDetails.getCustFreeTextField11());
                }
                if (accountDetails.getCustFreeTextField12() != null) {
                    existingAccountDetails.setCustFreeTextField12(accountDetails.getCustFreeTextField12());
                }
                if (accountDetails.getCustFreeTextField13() != null) {
                    existingAccountDetails.setCustFreeTextField13(accountDetails.getCustFreeTextField13());
                }
                if (accountDetails.getCustFreeTextField14() != null) {
                    existingAccountDetails.setCustFreeTextField14(accountDetails.getCustFreeTextField14());
                }
                if (accountDetails.getCustFreeTextField15() != null) {
                    existingAccountDetails.setCustFreeTextField15(accountDetails.getCustFreeTextField15());
                }
                if (accountDetails.getCustFreeTextField16() != null) {
                    existingAccountDetails.setCustFreeTextField16(accountDetails.getCustFreeTextField16());
                }
                if (accountDetails.getCustFreeTextField17() != null) {
                    existingAccountDetails.setCustFreeTextField17(accountDetails.getCustFreeTextField17());
                }
                if (accountDetails.getCustFreeTextField18() != null) {
                    existingAccountDetails.setCustFreeTextField18(accountDetails.getCustFreeTextField18());
                }
                if (accountDetails.getCustFreeTextField19() != null) {
                    existingAccountDetails.setCustFreeTextField19(accountDetails.getCustFreeTextField19());
                }
                if (accountDetails.getCustFreeTextField20() != null) {
                    existingAccountDetails.setCustFreeTextField20(accountDetails.getCustFreeTextField20());
                }
                if (accountDetails.getCustFreeTextField21() != null) {
                    existingAccountDetails.setCustFreeTextField21(accountDetails.getCustFreeTextField21());
                }
                if (accountDetails.getCustFreeTextField22() != null) {
                    existingAccountDetails.setCustFreeTextField22(accountDetails.getCustFreeTextField22());
                }
                if (accountDetails.getCustFreeTextField23() != null) {
                    existingAccountDetails.setCustFreeTextField23(accountDetails.getCustFreeTextField23());
                }
                if (accountDetails.getCustFreeTextField24() != null) {
                    existingAccountDetails.setCustFreeTextField24(accountDetails.getCustFreeTextField24());
                }
                if (accountDetails.getCustFreeTextField25() != null) {
                    existingAccountDetails.setCustFreeTextField25(accountDetails.getCustFreeTextField25());
                }
                if (accountDetails.getCustFreeTextField26() != null) {
                    existingAccountDetails.setCustFreeTextField26(accountDetails.getCustFreeTextField26());
                }
                if (accountDetails.getCustFreeTextField27() != null) {
                    existingAccountDetails.setCustFreeTextField27(accountDetails.getCustFreeTextField27());
                }
                if (accountDetails.getCustFreeTextField28() != null) {
                    existingAccountDetails.setCustFreeTextField28(accountDetails.getCustFreeTextField28());
                }
                if (accountDetails.getCustFreeTextField29() != null) {
                    existingAccountDetails.setCustFreeTextField29(accountDetails.getCustFreeTextField29());
                }
                if (accountDetails.getCustFreeTextField30() != null) {
                    existingAccountDetails.setCustFreeTextField30(accountDetails.getCustFreeTextField30());
                }
                if (accountDetails.getCustFreeTextField31() != null) {
                    existingAccountDetails.setCustFreeTextField31(accountDetails.getCustFreeTextField31());
                }

                return existingAccountDetails;
            })
            .map(accountDetailsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /account-details} : get all the accountDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountDetails in body.
     */
    @GetMapping("/bulk/account-details")
    public List<AccountDetails> getAllAccountDetails() {
        log.debug("REST request to get all AccountDetails");
        return accountDetailsRepository.findAll();
    }

    /**
     * {@code GET  /account-details/:id} : get the "id" accountDetails.
     *
     * @param id the id of the accountDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-details/{id}")
    public ResponseEntity<AccountDetails> getAccountDetails(@PathVariable UUID id) {
        log.debug("REST request to get AccountDetails : {}", id);
        Optional<AccountDetails> accountDetails = accountDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(accountDetails);
    }

    /**
     * {@code DELETE  /account-details/:id} : delete the "id" accountDetails.
     *
     * @param id the id of the accountDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
  //  @DeleteMapping("/account-details/{id}")
    public ResponseEntity<Void> deleteAccountDetails(@PathVariable UUID id) {
        log.debug("REST request to delete AccountDetails : {}", id);
        accountDetailsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
