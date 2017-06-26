package com.jimenezlav.expenses.service.mapper;

import com.jimenezlav.expenses.domain.*;
import com.jimenezlav.expenses.service.dto.SpendingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Spending and its DTO SpendingDTO.
 */
@Mapper(componentModel = "spring", uses = {PaymentMethodMapper.class, })
public interface SpendingMapper extends EntityMapper <SpendingDTO, Spending> {

    @Mapping(source = "paymentMethod.id", target = "paymentMethodId")
    SpendingDTO toDto(Spending spending); 

    @Mapping(source = "paymentMethodId", target = "paymentMethod")
    Spending toEntity(SpendingDTO spendingDTO); 
    default Spending fromId(Long id) {
        if (id == null) {
            return null;
        }
        Spending spending = new Spending();
        spending.setId(id);
        return spending;
    }
}
