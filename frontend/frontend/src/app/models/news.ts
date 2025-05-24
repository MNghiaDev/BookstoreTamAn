export class News{
    id : 0;
    title : string;
    content : string;
    imageUrl : string;
    createdBy : string;
    createdAt : string;
    modifyBy : string;
    modifyAt : string;

    constructor(){
        this.id = 0;
        this.title = "",
        this.content = "",
        this.imageUrl = "",
        this.createdBy = "",
        this.createdAt = "",
        this.modifyBy = "",
        this.modifyAt = ""
    }
}
export interface INewsList{
        id : 0;
    title : string;
    content : string;
    imageUrl : string;
    createdBy : string;
    createdAt : string;
    modifyBy : string;
    modifyAt : string;
}