import { IBookList } from "./book";

export class Category{
    id : 0;
    name: string;
    desciption : string;
    book : IBookList[] = [];

    constructor(){
        this.id = 0;
        this.name = "";
        this.desciption = "";
    }
}

export interface IListCategory{
    id : 0;
    name: string;
    desciption : string;
    book : IBookList[];
}