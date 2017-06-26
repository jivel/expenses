import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SpendingComponent } from './spending.component';
import { SpendingDetailComponent } from './spending-detail.component';
import { SpendingPopupComponent } from './spending-dialog.component';
import { SpendingDeletePopupComponent } from './spending-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class SpendingResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const spendingRoute: Routes = [
    {
        path: 'spending',
        component: SpendingComponent,
        resolve: {
            'pagingParams': SpendingResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'expensesApp.spending.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'spending/:id',
        component: SpendingDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'expensesApp.spending.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const spendingPopupRoute: Routes = [
    {
        path: 'spending-new',
        component: SpendingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'expensesApp.spending.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'spending/:id/edit',
        component: SpendingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'expensesApp.spending.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'spending/:id/delete',
        component: SpendingDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'expensesApp.spending.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
