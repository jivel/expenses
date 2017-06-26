package com.jimenezlav.expenses.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Catálogo de Métodos de Pago.
 * Efectivo.
 * Tarjeta de Crédito.
 * Tarjeta de Dédito.
 * Vales de Despensa
 * @author jimenezlav.
 */
@ApiModel(description = "Catálogo de Métodos de Pago. Efectivo. Tarjeta de Crédito. Tarjeta de Dédito. Vales de Despensa @author jimenezlav.")
@Entity
@Table(name = "payment_method")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaymentMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "paymentMethod")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Spending> spendings = new HashSet<>();

    @ManyToOne
    private FinancialEntity financialEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public PaymentMethod description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Spending> getSpendings() {
        return spendings;
    }

    public PaymentMethod spendings(Set<Spending> spendings) {
        this.spendings = spendings;
        return this;
    }

    public PaymentMethod addSpending(Spending spending) {
        this.spendings.add(spending);
        spending.setPaymentMethod(this);
        return this;
    }

    public PaymentMethod removeSpending(Spending spending) {
        this.spendings.remove(spending);
        spending.setPaymentMethod(null);
        return this;
    }

    public void setSpendings(Set<Spending> spendings) {
        this.spendings = spendings;
    }

    public FinancialEntity getFinancialEntity() {
        return financialEntity;
    }

    public PaymentMethod financialEntity(FinancialEntity financialEntity) {
        this.financialEntity = financialEntity;
        return this;
    }

    public void setFinancialEntity(FinancialEntity financialEntity) {
        this.financialEntity = financialEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentMethod paymentMethod = (PaymentMethod) o;
        if (paymentMethod.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentMethod.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
