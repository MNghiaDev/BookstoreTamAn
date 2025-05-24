export class User{
    id : 0;
    username : string;
    password : string;
    email : string;
    full_name : string;
    phone: string;
    address: string;
    role : string;
    active : boolean;

    constructor(){
        this.id = 0;
        this.username = "";
        this.password = "";
        this.email = "";
        this.full_name = "";
        this.phone = "";
        this.address = "";
        this.role = "";
        this.active = true;
    }
}

export interface IUser{
    id : 0;
    username : string;
    password : string;
    email : string;
    full_name : string;
    phone: string;
    address: string;
    role : string;
    active : boolean;
}