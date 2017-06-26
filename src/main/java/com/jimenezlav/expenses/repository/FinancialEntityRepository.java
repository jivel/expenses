package com.jimenezlav.expenses.repository;

import com.jimenezlav.expenses.domain.FinancialEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FinancialEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinancialEntityRepository extends JpaRepository<FinancialEntity,Long> {
    
}
