import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Spending } from './spending.model';
import { SpendingService } from './spending.service';

@Injectable()
export class SpendingPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private spendingService: SpendingService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.spendingService.find(id).subscribe((spending) => {
                spending.expenseDate = this.datePipe
                    .transform(spending.expenseDate, 'yyyy-MM-ddThh:mm');
                this.spendingModalRef(component, spending);
            });
        } else {
            return this.spendingModalRef(component, new Spending());
        }
    }

    spendingModalRef(component: Component, spending: Spending): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.spending = spending;
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
