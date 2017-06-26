import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FinancialEntity } from './financial-entity.model';
import { FinancialEntityPopupService } from './financial-entity-popup.service';
import { FinancialEntityService } from './financial-entity.service';

@Component({
    selector: 'jhi-financial-entity-dialog',
    templateUrl: './financial-entity-dialog.component.html'
})
export class FinancialEntityDialogComponent implements OnInit {

    financialEntity: FinancialEntity;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private financialEntityService: FinancialEntityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.financialEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.financialEntityService.update(this.financialEntity), false);
        } else {
            this.subscribeToSaveResponse(
                this.financialEntityService.create(this.financialEntity), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<FinancialEntity>, isCreated: boolean) {
        result.subscribe((res: FinancialEntity) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: FinancialEntity, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'expensesApp.financialEntity.created'
            : 'expensesApp.financialEntity.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'financialEntityListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-financial-entity-popup',
    template: ''
})
export class FinancialEntityPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private financialEntityPopupService: FinancialEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.financialEntityPopupService
                    .open(FinancialEntityDialogComponent, params['id']);
            } else {
                this.modalRef = this.financialEntityPopupService
                    .open(FinancialEntityDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
