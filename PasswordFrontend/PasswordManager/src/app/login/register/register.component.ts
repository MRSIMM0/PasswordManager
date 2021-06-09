import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ApiService } from 'src/app/Api/api.service';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { TokenStorageService } from '../../auth/token-storage.service';
import { LoginRequest } from 'src/app/models/LoginRequest';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  constructor(
    private router: Router,
    public activeModal: NgbActiveModal,
    private tokenStorage: TokenStorageService,
    private api: ApiService,
    private fb: FormBuilder
  ) {}
  invalid = false;
  clicked = false;
  username = new FormControl('');
  password = new FormControl('');

  ngOnInit(): void {}

  public add() {
    this.api
      .register(new LoginRequest(this.username.value, this.password.value))
      .subscribe(
        (res) => {
          this.api
            .login(new LoginRequest(this.username.value, this.password.value))
            .subscribe(
              (data) => {
                this.activeModal.close('Close click');
                this.tokenStorage.saveToken(data.token);
                this.router.navigate(['/dashboard']);
              },
              (err) => {
                this.invalid = true;
              }
            );
        },
        (err) => {
          this.invalid = true;
        }
      );
  }
}
