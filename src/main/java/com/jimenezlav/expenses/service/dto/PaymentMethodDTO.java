package com.jimenezlav.expenses.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PaymentMethod entity.
 */
public class PaymentMethodDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    private Long financialEntityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFinancialEntityId() {
        return financialEntityId;
    }

    public void setFinancialEntityId(Long financialEntityId) {
        this.financialEntityId = financialEntityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaymentMethodDTO paymentMethodDTO = (PaymentMethodDTO) o;
        if(paymentMethodDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentMethodDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentMethodDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
