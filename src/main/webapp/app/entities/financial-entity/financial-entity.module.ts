import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ExpensesSharedModule } from '../../shared';
import {
    FinancialEntityService,
    FinancialEntityPopupService,
    FinancialEntityComponent,
    FinancialEntityDetailComponent,
    FinancialEntityDialogComponent,
    FinancialEntityPopupComponent,
    FinancialEntityDeletePopupComponent,
    FinancialEntityDeleteDialogComponent,
    financialEntityRoute,
    financialEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...financialEntityRoute,
    ...financialEntityPopupRoute,
];

@NgModule({
    imports: [
        ExpensesSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FinancialEntityComponent,
        FinancialEntityDetailComponent,
        FinancialEntityDialogComponent,
        FinancialEntityDeleteDialogComponent,
        FinancialEntityPopupComponent,
        FinancialEntityDeletePopupComponent,
    ],
    entryComponents: [
        FinancialEntityComponent,
        FinancialEntityDialogComponent,
        FinancialEntityPopupComponent,
        FinancialEntityDeleteDialogComponent,
        FinancialEntityDeletePopupComponent,
    ],
    providers: [
        FinancialEntityService,
        FinancialEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ExpensesFinancialEntityModule {}
