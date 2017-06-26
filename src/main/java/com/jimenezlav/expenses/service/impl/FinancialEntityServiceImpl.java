package com.jimenezlav.expenses.service.impl;

import com.jimenezlav.expenses.service.FinancialEntityService;
import com.jimenezlav.expenses.domain.FinancialEntity;
import com.jimenezlav.expenses.repository.FinancialEntityRepository;
import com.jimenezlav.expenses.service.dto.FinancialEntityDTO;
import com.jimenezlav.expenses.service.mapper.FinancialEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing FinancialEntity.
 */
@Service
@Transactional
public class FinancialEntityServiceImpl implements FinancialEntityService{

    private final Logger log = LoggerFactory.getLogger(FinancialEntityServiceImpl.class);

    private final FinancialEntityRepository financialEntityRepository;

    private final FinancialEntityMapper financialEntityMapper;

    public FinancialEntityServiceImpl(FinancialEntityRepository financialEntityRepository, FinancialEntityMapper financialEntityMapper) {
        this.financialEntityRepository = financialEntityRepository;
        this.financialEntityMapper = financialEntityMapper;
    }

    /**
     * Save a financialEntity.
     *
     * @param financialEntityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FinancialEntityDTO save(FinancialEntityDTO financialEntityDTO) {
        log.debug("Request to save FinancialEntity : {}", financialEntityDTO);
        FinancialEntity financialEntity = financialEntityMapper.toEntity(financialEntityDTO);
        financialEntity = financialEntityRepository.save(financialEntity);
        return financialEntityMapper.toDto(financialEntity);
    }

    /**
     *  Get all the financialEntities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FinancialEntityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FinancialEntities");
        return financialEntityRepository.findAll(pageable)
            .map(financialEntityMapper::toDto);
    }

    /**
     *  Get one financialEntity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FinancialEntityDTO findOne(Long id) {
        log.debug("Request to get FinancialEntity : {}", id);
        FinancialEntity financialEntity = financialEntityRepository.findOne(id);
        return financialEntityMapper.toDto(financialEntity);
    }

    /**
     *  Delete the  financialEntity by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FinancialEntity : {}", id);
        financialEntityRepository.delete(id);
    }
}
