import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ExpensesTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SpendingDetailComponent } from '../../../../../../main/webapp/app/entities/spending/spending-detail.component';
import { SpendingService } from '../../../../../../main/webapp/app/entities/spending/spending.service';
import { Spending } from '../../../../../../main/webapp/app/entities/spending/spending.model';

describe('Component Tests', () => {

    describe('Spending Management Detail Component', () => {
        let comp: SpendingDetailComponent;
        let fixture: ComponentFixture<SpendingDetailComponent>;
        let service: SpendingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ExpensesTestModule],
                declarations: [SpendingDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SpendingService,
                    JhiEventManager
                ]
            }).overrideTemplate(SpendingDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SpendingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpendingService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Spending(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.spending).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
