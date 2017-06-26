import { BaseEntity } from './../../shared';

export class PaymentMethod implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string,
        public spendings?: BaseEntity[],
        public financialEntityId?: number,
    ) {
    }
}
