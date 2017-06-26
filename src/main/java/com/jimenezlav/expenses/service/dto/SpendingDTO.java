package com.jimenezlav.expenses.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Spending entity.
 */
public class SpendingDTO implements Serializable {

    private Long id;

    @NotNull
    private String concept;

    @NotNull
    private Double amount;

    @NotNull
    private ZonedDateTime expenseDate;

    private Long paymentMethodId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ZonedDateTime getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(ZonedDateTime expenseDate) {
        this.expenseDate = expenseDate;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SpendingDTO spendingDTO = (SpendingDTO) o;
        if(spendingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), spendingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SpendingDTO{" +
            "id=" + getId() +
            ", concept='" + getConcept() + "'" +
            ", amount='" + getAmount() + "'" +
            ", expenseDate='" + getExpenseDate() + "'" +
            "}";
    }
}
