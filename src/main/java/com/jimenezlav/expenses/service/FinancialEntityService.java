package com.jimenezlav.expenses.service;

import com.jimenezlav.expenses.service.dto.FinancialEntityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FinancialEntity.
 */
public interface FinancialEntityService {

    /**
     * Save a financialEntity.
     *
     * @param financialEntityDTO the entity to save
     * @return the persisted entity
     */
    FinancialEntityDTO save(FinancialEntityDTO financialEntityDTO);

    /**
     *  Get all the financialEntities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FinancialEntityDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" financialEntity.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FinancialEntityDTO findOne(Long id);

    /**
     *  Delete the "id" financialEntity.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
