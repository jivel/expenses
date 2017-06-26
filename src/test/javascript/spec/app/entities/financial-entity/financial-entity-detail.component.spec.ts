import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ExpensesTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FinancialEntityDetailComponent } from '../../../../../../main/webapp/app/entities/financial-entity/financial-entity-detail.component';
import { FinancialEntityService } from '../../../../../../main/webapp/app/entities/financial-entity/financial-entity.service';
import { FinancialEntity } from '../../../../../../main/webapp/app/entities/financial-entity/financial-entity.model';

describe('Component Tests', () => {

    describe('FinancialEntity Management Detail Component', () => {
        let comp: FinancialEntityDetailComponent;
        let fixture: ComponentFixture<FinancialEntityDetailComponent>;
        let service: FinancialEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ExpensesTestModule],
                declarations: [FinancialEntityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FinancialEntityService,
                    JhiEventManager
                ]
            }).overrideTemplate(FinancialEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FinancialEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinancialEntityService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FinancialEntity(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.financialEntity).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
