import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {retry, catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Category } from '../models/category';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})

export class RestService {

  categoryApiUrl: string;
  productApiUrl: string;

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json; charset=utf-8'
    })
  };

  constructor(private http: HttpClient) {
    this.categoryApiUrl = environment.apiUrl + 'categories/';
    this.productApiUrl = environment.apiUrl + 'products/';
  }


  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.categoryApiUrl)
      .pipe(
        retry(1),
        catchError(this.errorHandler)
      );
  }

  getCategory(categoryId: number): Observable<Category> {
    return this.http.get<Category>(this.categoryApiUrl + categoryId)
      .pipe(
        retry(1),
        catchError(this.errorHandler)
      );
  }

  saveCategory(category: Category): Observable<Category> {
    return this.http.post<Category>(this.categoryApiUrl, JSON.stringify(category), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandler)
      );
  }

  updateCategory(categoryId: number, category: any): Observable<Category> {
    return this.http.put<Category>(this.categoryApiUrl + categoryId, JSON.stringify(category), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandler)
      );
  }

  deleteCategory(categoryId: number): Observable<Category> {
    return this.http.delete<Category>(this.categoryApiUrl + categoryId)
      .pipe(
        retry(1),
        catchError(this.errorHandler)
      );
  }

  // ******   Product methods section   ******//

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.productApiUrl)
      .pipe(
        retry(1),
        catchError(this.errorHandler)
      );
  }

  getProduct(productId: number): Observable<Product> {
    return this.http.get<Product>(this.productApiUrl + productId)
      .pipe(
        retry(1),
        catchError(this.errorHandler)
      );
  }

  saveProduct(product: any): Observable<Product> {
    return this.http.post<Product>(this.productApiUrl, JSON.stringify(product), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandler)
      );
  }

  updateProduct(productId: number, product: any): Observable<Product> {
    return this.http.put<Product>(this.productApiUrl + productId, JSON.stringify(product), this.httpOptions)

      .pipe(
        retry(1),
        catchError(this.errorHandler)
      );
  }

  deleteProduct(productId: number): Observable<Product> {
    return this.http.delete<Product>(this.productApiUrl + productId)
      .pipe(
        retry(1),
        catchError(this.errorHandler)
      );
  }

  // ******   Error handler section   ******//

  errorHandler(error: { error: { message: string; }; status: any; message: any; }) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
  }
}

