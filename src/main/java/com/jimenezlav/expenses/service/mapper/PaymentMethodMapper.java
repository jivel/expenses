package com.jimenezlav.expenses.service.mapper;

import com.jimenezlav.expenses.domain.*;
import com.jimenezlav.expenses.service.dto.PaymentMethodDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PaymentMethod and its DTO PaymentMethodDTO.
 */
@Mapper(componentModel = "spring", uses = {FinancialEntityMapper.class, })
public interface PaymentMethodMapper extends EntityMapper <PaymentMethodDTO, PaymentMethod> {

    @Mapping(source = "financialEntity.id", target = "financialEntityId")
    PaymentMethodDTO toDto(PaymentMethod paymentMethod); 
    @Mapping(target = "spendings", ignore = true)

    @Mapping(source = "financialEntityId", target = "financialEntity")
    PaymentMethod toEntity(PaymentMethodDTO paymentMethodDTO); 
    default PaymentMethod fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(id);
        return paymentMethod;
    }
}
