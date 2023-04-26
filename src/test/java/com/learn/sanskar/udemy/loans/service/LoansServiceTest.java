package com.learn.sanskar.udemy.loans.service;

import com.learn.sanskar.udemy.loans.model.Customer;
import com.learn.sanskar.udemy.loans.model.Loan;
import com.learn.sanskar.udemy.loans.repository.LoansRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoansServiceTest {

    private static final Integer LOAN_NUMBER = 1;
    private static final String LOAN_TYPE = "loan type";
    private static final Integer TOTAL_LOAN = 1000;
    private static final Integer CUSTOMER_ID = 1;
    private static final Integer AMOUNT_PAID = 100;
    private static final Integer OUTSTANDING_AMOUNT = 900;
    private static final Date START_DATE = new Date(LocalDate
            .of(2022, 1, 1)
            .atStartOfDay(ZoneId.of("Australia/Sydney"))
            .toInstant()
            .toEpochMilli());

    private static final Date CREATE_DATE = new Date(LocalDate
            .now()
            .atStartOfDay(ZoneId.of("Australia/Sydney"))
            .toInstant()
            .toEpochMilli());



    @Mock
    private LoansRepository loansRepository;

    @InjectMocks
    private LoansService loansService;

    @Test
    void givenCustomerId_whenGetLoansByCustomerIdAsc_thenReturnLoans() {
        //given
        List<Loan> loans = List.of(createLoan());
        Customer customer = new Customer();
        customer.setCustomerId(CUSTOMER_ID);
        when(loansRepository.findLoansByCustomerIdOrderByStartDtAsc(anyInt())).thenReturn(loans);
        //when
        List<Loan> loansResponse
                = loansService.getLoansByCustomer(customer, true);
        //then
        assertThat(loansResponse).isNotNull();
        assertThat(loansResponse.get(0)).isNotNull();
        assertThat(loansResponse.get(0).getCustomerId()).isEqualTo(CUSTOMER_ID);
        assertThat(loansResponse.get(0).getLoanType()).isEqualTo(LOAN_TYPE);
        assertThat(loansResponse.get(0).getTotalLoan()).isEqualTo(TOTAL_LOAN);
    }

    @Test
    void givenCustomerId_whenGetLoansByCustomerIdDesc_thenReturnLoans() {
        //given
        List<Loan> loans = List.of(createLoan());
        Customer customer = new Customer();
        customer.setCustomerId(CUSTOMER_ID);
        when(loansRepository.findLoansByCustomerIdOrderByStartDtDesc(anyInt())).thenReturn(loans);
        //when
        List<Loan> optionalLoans = loansService.getLoansByCustomer(customer, false);
        //then
        assertThat(optionalLoans).isNotEmpty();
        assertThat(optionalLoans.get(0)).isNotNull();
        assertThat(optionalLoans.get(0).getCustomerId()).isEqualTo(CUSTOMER_ID);
        assertThat(optionalLoans.get(0).getLoanType()).isEqualTo(LOAN_TYPE);
        assertThat(optionalLoans.get(0).getTotalLoan()).isEqualTo(TOTAL_LOAN);
    }

    private Loan createLoan() {
        Loan loan = new Loan();
        loan.setLoanNumber(LOAN_NUMBER);
        loan.setLoanType(LOAN_TYPE);
        loan.setTotalLoan(TOTAL_LOAN);
        loan.setCustomerId(CUSTOMER_ID);
        loan.setAmountPaid(AMOUNT_PAID);
        loan.setOutstandingAmount(OUTSTANDING_AMOUNT);
        loan.setStartDt(START_DATE);
        loan.setCreateDt(CREATE_DATE);
        return loan;
    }
}