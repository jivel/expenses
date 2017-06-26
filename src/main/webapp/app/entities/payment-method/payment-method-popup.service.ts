import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PaymentMethod } from './payment-method.model';
import { PaymentMethodService } from './payment-method.service';

@Injectable()
export class PaymentMethodPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private paymentMethodService: PaymentMethodService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.paymentMethodService.find(id).subscribe((paymentMethod) => {
                this.paymentMethodModalRef(component, paymentMethod);
            });
        } else {
            return this.paymentMethodModalRef(component, new PaymentMethod());
        }
    }

    paymentMethodModalRef(component: Component, paymentMethod: PaymentMethod): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.paymentMethod = paymentMethod;
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
