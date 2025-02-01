package org.loan.loanservice.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "loan_instalment")
public class LoanInstalment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name="loan_id")
    private Loan loan;
    private BigDecimal amount;
    private BigDecimal paidAmount;
    private LocalDate dueDate;
    @JsonIgnore
    private LocalDate paymentDate;
    private boolean isPaid;

}
