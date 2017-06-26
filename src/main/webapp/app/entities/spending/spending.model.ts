import { BaseEntity } from './../../shared';

export class Spending implements BaseEntity {
    constructor(
        public id?: number,
        public concept?: string,
        public amount?: number,
        public expenseDate?: any,
        public paymentMethodId?: number,
    ) {
    }
}
