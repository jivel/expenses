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
 * Catálogo de entidades financieras
 * pueden ser bancos, cajas de ahorros
 * o cooperativas de crédito.
 */
@ApiModel(description = "Catálogo de entidades financieras pueden ser bancos, cajas de ahorros o cooperativas de crédito.")
@Entity
@Table(name = "financial_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FinancialEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @OneToMany(mappedBy = "financialEntity")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public FinancialEntity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public FinancialEntity imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public FinancialEntity paymentMethods(Set<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
        return this;
    }

    public FinancialEntity addPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethods.add(paymentMethod);
        paymentMethod.setFinancialEntity(this);
        return this;
    }

    public FinancialEntity removePaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethods.remove(paymentMethod);
        paymentMethod.setFinancialEntity(null);
        return this;
    }

    public void setPaymentMethods(Set<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FinancialEntity financialEntity = (FinancialEntity) o;
        if (financialEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), financialEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FinancialEntity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
