import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  apiUrl : string = "http://localhost:8080/api/bookstore/book";

  constructor(private http : HttpClient) { }

  listBook(page : number, size : number){
    const params = new HttpParams()
    .set('page', page.toString())
    .set('size', size.toString())
    return this.http.get(this.apiUrl, {params});
  }

  bookDetail(bookId : number){
    return this.http.get(this.apiUrl + '/' + bookId);
  }

  bookMaxSale(){
    return this.http.get(this.apiUrl + '/max-sale');
  }

  topSelling(){
    return this.http.get(this.apiUrl + "/top-selling")
  }

  bookByPublicationDate(){
    return this.http.get(this.apiUrl + "/publication-date");
  }

  topBooksByTopAuthors(){
    return this.http.get(this.apiUrl + "/top-books-by-top-authors");
  }
  searchBooks(keyword: string, page: number, size: number) {
    return this.http.get<any>(`${this.apiUrl}/search?keyword=${keyword}&page=${page}&size=${size}`);
  }
  getSuggestions(keyword: string) {
    const params = new HttpParams()
      .set('keyword', keyword);
    return this.http.get<any>(this.apiUrl + '/suggestions', { params });
  }
  
}
