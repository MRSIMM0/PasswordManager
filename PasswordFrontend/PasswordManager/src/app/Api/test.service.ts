import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LoginRequest} from '../models/LoginRequest';
import {JwtResponse} from '../models/JwtResponse';

@Injectable({
  providedIn: 'root'
})


export class TestService {

 private BASE_URL = 'http://localhost:/8089'
 private LOGIN_URL = this.BASE_URL + '/auth/login'
 private Auth = this.BASE_URL + '/auth/getClaims'

  constructor(private http:HttpClient) {}

  login(entity: LoginRequest): Observable<JwtResponse>{
       return this.http.post<JwtResponse>(this.LOGIN_URL,entity);
  }

  authenticate(token:string): Observable<JwtResponse>{
   return this.http.post<JwtResponse>(this.Auth,null,{headers:{'Token' : token}});
  }
}