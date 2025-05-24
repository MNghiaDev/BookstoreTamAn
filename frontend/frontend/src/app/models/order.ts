export class Order{
    orderId: 0;
    orderCode : string;
    userId : 0;
    orderDate : string;
    status : string;
    totalPrice : 0;
    email : string;
    paymentMethod : string;
    phone : string;
    shippingAddress : string;
    recipientName : string;
    active : boolean;
    paid : boolean;
    constructor(){
        this.orderId = 0;
        this.orderCode = "";
        this.userId = 0;
        this.orderDate = "";
        this.status = "";
        this.totalPrice = 0;
        this.email = "";
        this.paymentMethod = "";
        this.phone = "";
        this.shippingAddress = "";
        this.recipientName = "";
        this.active = true;
        this.paid = false;
    }
}

export interface IListOrder{
    orderId: 0;
    orderCode : string;
    userId : 0;
    orderDate : string;
    status : string;
    totalPrice : 0;
    email : string;
    paymentMethod : string;
    phone : string;
    shippingAddress : string;
    recipientName : string;
    active : boolean;
    paid : boolean;
}