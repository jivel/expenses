package com.jimenezlav.expenses.repository;

import com.jimenezlav.expenses.domain.Spending;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Spending entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpendingRepository extends JpaRepository<Spending,Long> {
    
}
