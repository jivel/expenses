import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FinancialEntityComponent } from './financial-entity.component';
import { FinancialEntityDetailComponent } from './financial-entity-detail.component';
import { FinancialEntityPopupComponent } from './financial-entity-dialog.component';
import { FinancialEntityDeletePopupComponent } from './financial-entity-delete-dialog.component';

import { Principal } from '../../shared';

export const financialEntityRoute: Routes = [
    {
        path: 'financial-entity',
        component: FinancialEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'expensesApp.financialEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'financial-entity/:id',
        component: FinancialEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'expensesApp.financialEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const financialEntityPopupRoute: Routes = [
    {
        path: 'financial-entity-new',
        component: FinancialEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'expensesApp.financialEntity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'financial-entity/:id/edit',
        component: FinancialEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'expensesApp.financialEntity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'financial-entity/:id/delete',
        component: FinancialEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'expensesApp.financialEntity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
