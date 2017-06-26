package com.jimenezlav.expenses.service;

import com.jimenezlav.expenses.service.dto.PaymentMethodDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PaymentMethod.
 */
public interface PaymentMethodService {

    /**
     * Save a paymentMethod.
     *
     * @param paymentMethodDTO the entity to save
     * @return the persisted entity
     */
    PaymentMethodDTO save(PaymentMethodDTO paymentMethodDTO);

    /**
     *  Get all the paymentMethods.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PaymentMethodDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" paymentMethod.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PaymentMethodDTO findOne(Long id);

    /**
     *  Delete the "id" paymentMethod.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
