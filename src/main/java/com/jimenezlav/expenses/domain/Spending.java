package com.jimenezlav.expenses.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Histórico de gastos.
 */
@ApiModel(description = "Histórico de gastos.")
@Entity
@Table(name = "spending")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Spending implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "concept", nullable = false)
    private String concept;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "expense_date", nullable = false)
    private ZonedDateTime expenseDate;

    @ManyToOne
    private PaymentMethod paymentMethod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcept() {
        return concept;
    }

    public Spending concept(String concept) {
        this.concept = concept;
        return this;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Double getAmount() {
        return amount;
    }

    public Spending amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ZonedDateTime getExpenseDate() {
        return expenseDate;
    }

    public Spending expenseDate(ZonedDateTime expenseDate) {
        this.expenseDate = expenseDate;
        return this;
    }

    public void setExpenseDate(ZonedDateTime expenseDate) {
        this.expenseDate = expenseDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Spending paymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Spending spending = (Spending) o;
        if (spending.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), spending.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Spending{" +
            "id=" + getId() +
            ", concept='" + getConcept() + "'" +
            ", amount='" + getAmount() + "'" +
            ", expenseDate='" + getExpenseDate() + "'" +
            "}";
    }
}
