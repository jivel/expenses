import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { FinancialEntity } from './financial-entity.model';
import { FinancialEntityPopupService } from './financial-entity-popup.service';
import { FinancialEntityService } from './financial-entity.service';

@Component({
    selector: 'jhi-financial-entity-delete-dialog',
    templateUrl: './financial-entity-delete-dialog.component.html'
})
export class FinancialEntityDeleteDialogComponent {

    financialEntity: FinancialEntity;

    constructor(
        private financialEntityService: FinancialEntityService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.financialEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'financialEntityListModification',
                content: 'Deleted an financialEntity'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('expensesApp.financialEntity.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-financial-entity-delete-popup',
    template: ''
})
export class FinancialEntityDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private financialEntityPopupService: FinancialEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.financialEntityPopupService
                .open(FinancialEntityDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
