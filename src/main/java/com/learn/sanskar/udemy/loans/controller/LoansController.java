package com.learn.sanskar.udemy.loans.controller;

import com.learn.sanskar.udemy.loans.model.Customer;
import com.learn.sanskar.udemy.loans.model.Loan;
import com.learn.sanskar.udemy.loans.service.LoansService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {

    private final LoansService loansService;

    @Autowired
    public LoansController(LoansService loansService) {
        this.loansService = loansService;
    }

    @PostMapping(path = "/loan", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Loan> getLoansByCustomer(@RequestBody @Valid Customer customer, @RequestParam(name = "asc", required = false) Boolean asc) {
        return loansService.getLoansByCustomer(customer, asc != null && asc);
    }
}
