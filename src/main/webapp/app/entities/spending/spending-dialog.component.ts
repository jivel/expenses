import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Spending } from './spending.model';
import { SpendingPopupService } from './spending-popup.service';
import { SpendingService } from './spending.service';
import { PaymentMethod, PaymentMethodService } from '../payment-method';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-spending-dialog',
    templateUrl: './spending-dialog.component.html'
})
export class SpendingDialogComponent implements OnInit {

    spending: Spending;
    authorities: any[];
    isSaving: boolean;

    paymentmethods: PaymentMethod[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private spendingService: SpendingService,
        private paymentMethodService: PaymentMethodService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.paymentMethodService.query()
            .subscribe((res: ResponseWrapper) => { this.paymentmethods = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.spending.id !== undefined) {
            this.subscribeToSaveResponse(
                this.spendingService.update(this.spending), false);
        } else {
            this.subscribeToSaveResponse(
                this.spendingService.create(this.spending), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Spending>, isCreated: boolean) {
        result.subscribe((res: Spending) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Spending, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'expensesApp.spending.created'
            : 'expensesApp.spending.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'spendingListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackPaymentMethodById(index: number, item: PaymentMethod) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-spending-popup',
    template: ''
})
export class SpendingPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private spendingPopupService: SpendingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.spendingPopupService
                    .open(SpendingDialogComponent, params['id']);
            } else {
                this.modalRef = this.spendingPopupService
                    .open(SpendingDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
