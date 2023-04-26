package com.learn.sanskar.udemy.loans.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Entity
@Getter @Setter @ToString
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loan_number")
    private Integer loanNumber;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "start_dt")
    private Date startDt;
    @Column(name = "loan_type")
    private String loanType;
    @Column(name = "total_loan")
    private Integer totalLoan;
    @Column(name = "amount_paid")
    private Integer amountPaid;
    @Column(name = "outstanding_amount")
    private Integer outstandingAmount;
    @Column(name = "create_dt")
    private Date createDt;

}
