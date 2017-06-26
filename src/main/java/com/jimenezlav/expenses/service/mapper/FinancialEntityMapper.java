package com.jimenezlav.expenses.service.mapper;

import com.jimenezlav.expenses.domain.*;
import com.jimenezlav.expenses.service.dto.FinancialEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FinancialEntity and its DTO FinancialEntityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FinancialEntityMapper extends EntityMapper <FinancialEntityDTO, FinancialEntity> {
    
    @Mapping(target = "paymentMethods", ignore = true)
    FinancialEntity toEntity(FinancialEntityDTO financialEntityDTO); 
    default FinancialEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        FinancialEntity financialEntity = new FinancialEntity();
        financialEntity.setId(id);
        return financialEntity;
    }
}
