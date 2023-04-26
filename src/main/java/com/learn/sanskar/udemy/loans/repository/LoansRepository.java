package com.learn.sanskar.udemy.loans.repository;

import com.learn.sanskar.udemy.loans.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoansRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findLoansByCustomerIdOrderByStartDtDesc(Integer customerId);
    List<Loan> findLoansByCustomerIdOrderByStartDtAsc(Integer customerId);
}
