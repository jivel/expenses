package com.jimenezlav.expenses.service;

import com.jimenezlav.expenses.service.dto.SpendingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Spending.
 */
public interface SpendingService {

    /**
     * Save a spending.
     *
     * @param spendingDTO the entity to save
     * @return the persisted entity
     */
    SpendingDTO save(SpendingDTO spendingDTO);

    /**
     *  Get all the spendings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SpendingDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" spending.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SpendingDTO findOne(Long id);

    /**
     *  Delete the "id" spending.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
