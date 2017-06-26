import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PaymentMethod } from './payment-method.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PaymentMethodService {

    private resourceUrl = 'api/payment-methods';

    constructor(private http: Http) { }

    create(paymentMethod: PaymentMethod): Observable<PaymentMethod> {
        const copy = this.convert(paymentMethod);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(paymentMethod: PaymentMethod): Observable<PaymentMethod> {
        const copy = this.convert(paymentMethod);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PaymentMethod> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(paymentMethod: PaymentMethod): PaymentMethod {
        const copy: PaymentMethod = Object.assign({}, paymentMethod);
        return copy;
    }
}
