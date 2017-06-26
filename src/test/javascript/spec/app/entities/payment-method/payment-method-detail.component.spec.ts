import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ExpensesTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PaymentMethodDetailComponent } from '../../../../../../main/webapp/app/entities/payment-method/payment-method-detail.component';
import { PaymentMethodService } from '../../../../../../main/webapp/app/entities/payment-method/payment-method.service';
import { PaymentMethod } from '../../../../../../main/webapp/app/entities/payment-method/payment-method.model';

describe('Component Tests', () => {

    describe('PaymentMethod Management Detail Component', () => {
        let comp: PaymentMethodDetailComponent;
        let fixture: ComponentFixture<PaymentMethodDetailComponent>;
        let service: PaymentMethodService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ExpensesTestModule],
                declarations: [PaymentMethodDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PaymentMethodService,
                    JhiEventManager
                ]
            }).overrideTemplate(PaymentMethodDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaymentMethodDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentMethodService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PaymentMethod(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.paymentMethod).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
