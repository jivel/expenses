import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PaymentMethod } from './payment-method.model';
import { PaymentMethodPopupService } from './payment-method-popup.service';
import { PaymentMethodService } from './payment-method.service';
import { FinancialEntity, FinancialEntityService } from '../financial-entity';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-payment-method-dialog',
    templateUrl: './payment-method-dialog.component.html'
})
export class PaymentMethodDialogComponent implements OnInit {

    paymentMethod: PaymentMethod;
    authorities: any[];
    isSaving: boolean;

    financialentities: FinancialEntity[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private paymentMethodService: PaymentMethodService,
        private financialEntityService: FinancialEntityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.financialEntityService.query()
            .subscribe((res: ResponseWrapper) => { this.financialentities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.paymentMethod.id !== undefined) {
            this.subscribeToSaveResponse(
                this.paymentMethodService.update(this.paymentMethod), false);
        } else {
            this.subscribeToSaveResponse(
                this.paymentMethodService.create(this.paymentMethod), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<PaymentMethod>, isCreated: boolean) {
        result.subscribe((res: PaymentMethod) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PaymentMethod, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'expensesApp.paymentMethod.created'
            : 'expensesApp.paymentMethod.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'paymentMethodListModification', content: 'OK'});
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

    trackFinancialEntityById(index: number, item: FinancialEntity) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-payment-method-popup',
    template: ''
})
export class PaymentMethodPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paymentMethodPopupService: PaymentMethodPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.paymentMethodPopupService
                    .open(PaymentMethodDialogComponent, params['id']);
            } else {
                this.modalRef = this.paymentMethodPopupService
                    .open(PaymentMethodDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
