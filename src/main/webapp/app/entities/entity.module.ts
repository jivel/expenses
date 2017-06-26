import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ExpensesSpendingModule } from './spending/spending.module';
import { ExpensesPaymentMethodModule } from './payment-method/payment-method.module';
import { ExpensesFinancialEntityModule } from './financial-entity/financial-entity.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ExpensesSpendingModule,
        ExpensesPaymentMethodModule,
        ExpensesFinancialEntityModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ExpensesEntityModule {}
