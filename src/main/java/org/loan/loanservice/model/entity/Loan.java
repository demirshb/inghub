package org.loan.loanservice.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer customerId;
    @JsonManagedReference
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanInstalment> installment;
    private BigDecimal loanAmount;
    private int numberOfInstallments;
    private LocalDate createDate;
    private boolean isPaid;


    public void addLoan(LoanInstalment instalment) {
        installment.add(instalment);
        instalment.setLoan(this);
    }
}
