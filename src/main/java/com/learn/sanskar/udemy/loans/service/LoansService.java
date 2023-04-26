package com.learn.sanskar.udemy.loans.service;

import com.learn.sanskar.udemy.loans.model.Customer;
import com.learn.sanskar.udemy.loans.model.Loan;
import com.learn.sanskar.udemy.loans.repository.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoansService {

    private final LoansRepository loansRepository;

    @Autowired
    public LoansService(LoansRepository loansRepository) {
        this.loansRepository = loansRepository;
    }

    public List<Loan> getLoansByCustomer(Customer customer, boolean isAscending) {
        if (isAscending)
            return loansRepository.findLoansByCustomerIdOrderByStartDtAsc(customer.getCustomerId());
        else
            return loansRepository.findLoansByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
    }

}
