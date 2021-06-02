import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginRequest } from '../models/LoginRequest';
import { JwtResponse } from '../models/JwtResponse';
import { PasswordEntity } from '../models/PasswordEntity';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private BASE_URL = 'http://localhost:8090';
  private LOGIN_URL = this.BASE_URL + '/auth/login';
  private Auth = this.BASE_URL + '/auth/getClaims';
  private GET_ALL_PASS = this.BASE_URL + '/pass/get';
  private DELETE_ONE_PASS = this.BASE_URL + '/pass/delete';
  private ADD_PASS = this.BASE_URL + '/pass/add';

  constructor(private http: HttpClient) {}

  login(entity: LoginRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.LOGIN_URL, entity);
  }

  authenticate(token: string): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.Auth, null, {
      headers: { Token: token },
    });
  }
  getPasswords(token: string): Observable<any> {
    return this.http.get<any>(this.GET_ALL_PASS, { headers: { Token: token } });
  }
  delete(token: string, PasswordId: string): Observable<any> {
    return this.http.post(this.DELETE_ONE_PASS, PasswordId, {
      headers: { Token: token },
    });
  }

  addPassword(token: string, body: PasswordEntity): Observable<any> {
    return this.http.post(this.ADD_PASS, body, { headers: { Token: token } });
  }
}
