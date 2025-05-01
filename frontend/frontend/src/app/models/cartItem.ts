import { IBookList } from "./book";

export class CartItem{
    id : 0;
    quantity: 0;
    book : IBookList[] = [];
    

    constructor(){
        this.id = 0;
        this.quantity = 0;
    }
}

export interface IListCartItem{
    id : 0;
    quantity: 0;
    book : IBookList[];
}