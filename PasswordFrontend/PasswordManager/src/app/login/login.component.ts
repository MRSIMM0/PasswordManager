import { Component, OnInit, Input } from '@angular/core';
import { LoginRequest } from '../models/LoginRequest';
import { ApiService } from '../Api/api.service';
import { TokenStorageService } from '../auth/token-storage.service';
import { Router } from '@angular/router';
import { FormControl } from '@angular/forms';
import { RegisterComponent } from './register/register.component';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  form: any = {};

  username = new FormControl('');
  password = new FormControl('');
  invalid = false;
  loginForm: LoginRequest;

  constructor(
    private api: ApiService,
    private tokenStorage: TokenStorageService,
    private router: Router,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {}

  open() {
    const modalRef = this.modalService.open(RegisterComponent, {
      centered: true,
    });
  }

  onSubmit() {
    this.loginForm = new LoginRequest(this.username.value, this.password.value);
    this.api.login(this.loginForm).subscribe(
      (data) => {
        this.tokenStorage.saveToken(data.token);
        this.router.navigate(['/dashboard']);
      },
      (err) => {
        this.invalid = true;
      }
    );
  }
}
