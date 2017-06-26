import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Spending } from './spending.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SpendingService {

    private resourceUrl = 'api/spendings';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(spending: Spending): Observable<Spending> {
        const copy = this.convert(spending);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(spending: Spending): Observable<Spending> {
        const copy = this.convert(spending);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Spending> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.expenseDate = this.dateUtils
            .convertDateTimeFromServer(entity.expenseDate);
    }

    private convert(spending: Spending): Spending {
        const copy: Spending = Object.assign({}, spending);

        copy.expenseDate = this.dateUtils.toDate(spending.expenseDate);
        return copy;
    }
}
