import { BaseEntity } from './../../shared';

export class FinancialEntity implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public imageUrl?: string,
        public paymentMethods?: BaseEntity[],
    ) {
    }
}
