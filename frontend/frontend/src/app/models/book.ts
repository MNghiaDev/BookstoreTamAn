import { Category } from "./category";
import { CategoryNames } from "./categoryNames";

export class Book{
    id : 0;
    title: string;
    format : string;
    pages : 0;
    width : 0;
    height : 0;
    length : 0;
    weight : 0;
    publicationDate : string;
    publisher : string;
    language : string;
    illustrationsNote: string;
    isbn10: string;
    isbn13 : string;
    promotion : 0;
    promotionEndDate : string;
    description: string;
    sellerReview : string;
    reviewVideo : string;
    price : 0;
    stock: 0;
    imageUrl: string;
    rating: 0;
    numReviews: 0;
    selling: 0;
    createdBy: string;
    modifyBy: string;
    createdAt: string;
    modifyAt: string;
    nameAuthor : string;
    authorId : 0;
    categoryNames: Category[] = [];
    active : boolean =  true;

    constructor(){
        this.id = 0;
        this.title = "";
        this.format = "";
        this.pages = 0;
        this.width = 0;
        this.height = 0;
        this.length = 0;
        this.weight = 0;
        this.publicationDate = "";
        this.publisher = "";
        this.language = "";
        this.illustrationsNote = "";
        this.isbn10 = "";
        this.isbn13 = "";
        this.promotion = 0,
        this.promotionEndDate = "";
        this.description = "";
        this.sellerReview = "";
        this.reviewVideo = "",
        this.price = 0;
        this.stock = 0;
        this.imageUrl = "";
        this.rating = 0;
        this.numReviews = 0;
        this.selling = 0;
        this.createdBy = "";
        this.modifyBy = "";
        this.createdAt = "";
        this.modifyAt = "";
        this.nameAuthor = "";
        this.authorId = 0;
    }
}

export interface IBookList{
    id : 0;
    title: string;
    format : string;
    pages : 0;
    width : 0;
    height : 0;
    length : 0;
    weight : 0;
    publicationDate : string;
    publisher : string;
    language : string;
    illustrationsNote: string;
    isbn10: string;
    isbn13 : string;
    promotion : 0;
    promotionEndDate : string;
    description: string;
    sellerReview : string;
    reviewVideo : string;
    price : 0;
    stock: 0;
    imageUrl: string;
    rating: 0;
    numReviews: 0;
    selling: 0;
    createdBy: string;
    modifyBy: string;
    createdAt: string;
    modifyAt: string;
    nameAuthor : string;
    authorId : 0;
    categoryNames : Category[];
    active : boolean;
}