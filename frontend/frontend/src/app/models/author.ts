
export class Author{
    id : 0;
    name: string;
    bio : string;
    imageUrl : string;
    active : boolean;

    constructor(){
        this.id = 0;
        this.name = "";
        this.bio = "";
        this.imageUrl = "";
        this.active = true;
    }
}

export interface IAuthorList{
    id : 0;
    name: string;
    bio : string;
    imageUrl : string;
    active : boolean;
}