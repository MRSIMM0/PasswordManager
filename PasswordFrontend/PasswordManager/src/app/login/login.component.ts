import { Component, OnInit } from '@angular/core';
import { LoginRequest } from '../models/LoginRequest';
import {ApiService} from '../Api/api.service';
import {TokenStorageService} from '../auth/token-storage.service'
import { Router } from '@angular/router';
import { FormControl } from '@angular/forms';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

     form: any = {};

     username = new FormControl('');
     password = new FormControl('');

   loginForm: LoginRequest;

  constructor(private api: ApiService, private tokenStorage: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
  }



  onSubmit(){
      this.loginForm = new LoginRequest(this.username.value,this.password.value)
    this.api.login( this.loginForm ).subscribe(
        data=>{
         this.tokenStorage.saveToken(data.token);
         this.router.navigate(['/dashboard'])
        }
    )
  } 


}
