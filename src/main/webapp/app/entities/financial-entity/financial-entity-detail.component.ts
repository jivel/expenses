import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { FinancialEntity } from './financial-entity.model';
import { FinancialEntityService } from './financial-entity.service';

@Component({
    selector: 'jhi-financial-entity-detail',
    templateUrl: './financial-entity-detail.component.html'
})
export class FinancialEntityDetailComponent implements OnInit, OnDestroy {

    financialEntity: FinancialEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private financialEntityService: FinancialEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFinancialEntities();
    }

    load(id) {
        this.financialEntityService.find(id).subscribe((financialEntity) => {
            this.financialEntity = financialEntity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFinancialEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'financialEntityListModification',
            (response) => this.load(this.financialEntity.id)
        );
    }
}
