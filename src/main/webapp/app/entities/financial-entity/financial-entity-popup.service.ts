import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FinancialEntity } from './financial-entity.model';
import { FinancialEntityService } from './financial-entity.service';

@Injectable()
export class FinancialEntityPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private financialEntityService: FinancialEntityService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.financialEntityService.find(id).subscribe((financialEntity) => {
                this.financialEntityModalRef(component, financialEntity);
            });
        } else {
            return this.financialEntityModalRef(component, new FinancialEntity());
        }
    }

    financialEntityModalRef(component: Component, financialEntity: FinancialEntity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.financialEntity = financialEntity;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
