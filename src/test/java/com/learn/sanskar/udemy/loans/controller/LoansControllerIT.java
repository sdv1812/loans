package com.learn.sanskar.udemy.loans.controller;

import com.learn.sanskar.udemy.loans.exception.ErrorResponse;
import com.learn.sanskar.udemy.loans.model.Customer;
import com.learn.sanskar.udemy.loans.model.Loan;
import com.learn.sanskar.udemy.loans.service.LoansService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoansControllerIT {
    private static final String GET_LOANS_URL = "/loan";
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

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private LoansService loansService;

    @Test
    void givenCustomerId_whenGetLoansByCustomer_thenReturnLoans() {
        List<Loan> loans = List.of(createLoan());
        Customer customer = new Customer(CUSTOMER_ID);
        when(loansService.getLoansByCustomer(any(Customer.class), anyBoolean())).thenReturn(loans);

        ResponseEntity<Loan[]> loansRespEntity = this.testRestTemplate.postForEntity(GET_LOANS_URL, customer, Loan[].class);

        assertThat(loansRespEntity).isNotNull();
        assertThat(loansRespEntity.getBody()).isNotNull();
        Loan[] loansResp = loansRespEntity.getBody();
        assertThat(loansResp.length).isEqualTo(loans.size());
        assertThat(loansResp[0].getTotalLoan()).isEqualTo(TOTAL_LOAN);
    }

    @Test
    void givenCustomerIdAsEmpty_whenGetLoansByCustomer_thenReturnBadRequest() {
        Customer customer = new Customer();

//        when(loansService.getLoansByCustomer(any(Customer.class), anyBoolean())).thenReturn(new ArrayList<>());
        ResponseEntity<ErrorResponse> loansRespEntity = this.testRestTemplate
                .postForEntity(GET_LOANS_URL, customer, ErrorResponse.class);

        assertThat(loansRespEntity.getStatusCode().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(loansRespEntity).isNotNull();
        assertThat(loansRespEntity.getBody()).isNotNull();
        ErrorResponse errorResponse = loansRespEntity.getBody();
        assertThat(errorResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenCustomerIdNotExists_whenGetLoansByCustomer_thenReturnEmptyList() {
        Customer customer = new Customer();
        customer.setCustomerId(111);
        when(loansService.getLoansByCustomer(any(Customer.class), anyBoolean())).thenReturn(new ArrayList<>());

        ResponseEntity<Loan[]> loansRespEntity = this.testRestTemplate.postForEntity(GET_LOANS_URL, customer, Loan[].class);

        assertThat(loansRespEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loansRespEntity.getBody()).isNotNull();
        Loan[] loans = loansRespEntity.getBody();
        assertThat(loans).isEmpty();
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
