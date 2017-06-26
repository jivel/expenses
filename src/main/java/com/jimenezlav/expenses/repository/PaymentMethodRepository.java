package com.jimenezlav.expenses.repository;

import com.jimenezlav.expenses.domain.PaymentMethod;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PaymentMethod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Long> {
    
}
