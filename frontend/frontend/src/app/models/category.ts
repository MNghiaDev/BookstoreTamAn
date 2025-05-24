import { IBookList } from "./book";

export class Category{
    id : 0;
    name: string;
    description : string;
    book : IBookList[] = [];
    active : boolean;

    constructor(){
        this.id = 0;
        this.name = "";
        this.description = "";
        this.active = true;
    }
}

export interface IListCategory{
    id : 0;
    name: string;
    description : string;
    book : IBookList[];
    active : boolean;
}