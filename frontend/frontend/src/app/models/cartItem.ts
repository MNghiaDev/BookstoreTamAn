import { IBookList } from "./book";

export class CartItem{
    id : 0;
    quantity: 0;
    active : boolean;
    book : IBookList[] = [];
    

    constructor(){
        this.id = 0;
        this.quantity = 0;
        this.active = true;
    }
}

export interface IListCartItem{
    id : 0;
    quantity: 0;
    book : IBookList[];
    active : boolean;
}