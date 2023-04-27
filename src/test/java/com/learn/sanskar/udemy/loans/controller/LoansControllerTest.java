package com.learn.sanskar.udemy.loans.controller;

import com.learn.sanskar.udemy.loans.model.Customer;
import com.learn.sanskar.udemy.loans.model.Loan;
import com.learn.sanskar.udemy.loans.service.LoansService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoansController.class)
class LoansControllerTest {
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
    private MockMvc mockMvc;

    @MockBean
    private LoansService loansService;


    @Test
    void givenCustomerId_whenGetLoansByCustomer_thenReturnLoans() throws Exception {
        List<Loan> loans = List.of(createLoan());
        when(loansService.getLoansByCustomer(any(Customer.class), anyBoolean())).thenReturn(loans);
        this.mockMvc.perform(post(GET_LOANS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "customerId": 1
                        }
                        """))
                .andExpect(status().isOk());

    }

    @Test
    void givenCustomerIdAsEmpty_whenGetLoansByCustomer_thenReturnBadRequest() throws Exception {
        List<Loan> loans = List.of(createLoan());
        when(loansService.getLoansByCustomer(any(Customer.class), anyBoolean())).thenReturn(loans);
        this.mockMvc.perform(post(GET_LOANS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "customerId": null
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Validation issues. Check in 'errors' field in the response."))
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[*]").isNotEmpty())
                .andExpect(jsonPath("$.errors[0].field").value("customerId"))
                .andExpect(jsonPath("$.errors[0].message").value("must not be null"));
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