import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ExpensesSharedModule } from '../../shared';
import {
    SpendingService,
    SpendingPopupService,
    SpendingComponent,
    SpendingDetailComponent,
    SpendingDialogComponent,
    SpendingPopupComponent,
    SpendingDeletePopupComponent,
    SpendingDeleteDialogComponent,
    spendingRoute,
    spendingPopupRoute,
    SpendingResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...spendingRoute,
    ...spendingPopupRoute,
];

@NgModule({
    imports: [
        ExpensesSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SpendingComponent,
        SpendingDetailComponent,
        SpendingDialogComponent,
        SpendingDeleteDialogComponent,
        SpendingPopupComponent,
        SpendingDeletePopupComponent,
    ],
    entryComponents: [
        SpendingComponent,
        SpendingDialogComponent,
        SpendingPopupComponent,
        SpendingDeleteDialogComponent,
        SpendingDeletePopupComponent,
    ],
    providers: [
        SpendingService,
        SpendingPopupService,
        SpendingResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ExpensesSpendingModule {}
