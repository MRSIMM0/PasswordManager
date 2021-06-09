import { Component, OnInit, Input } from '@angular/core';
import { ApiService } from '../Api/api.service';
import { TokenStorageService } from '../auth/token-storage.service';
import { JwtResponse } from '../models/JwtResponse';
import { PasswordEntity } from '../models/PasswordEntity';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from './Modal/modal.component';
import { ErrorComponent } from './error/error.component';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  isLoggedIn: boolean = false;

  listOfPass: PasswordEntity[] = [];

  isLoading = true;

  constructor(
    private api: ApiService,
    private tokenStorage: TokenStorageService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.api.authenticate(this.tokenStorage.getToken()).subscribe(
      (res) => {
        this.isLoggedIn = false;
        this.getAllPasswords();
      },
      (err) => {
        this.isLoggedIn = true;
      }
    );
  }

  public getAllPasswords() {
    this.api.getPasswords(this.tokenStorage.getToken()).subscribe(
      (res) => {
        this.listOfPass = res.passwords;
        this.isLoading = false;
      },
      (err) => {
        console.log(err);
        this.isLoading = false;
      }
    );
  }
  public delete(id: string) {
    this.api.delete(this.tokenStorage.getToken(), id).subscribe((res) => {
      location.reload();
    });
  }


  open() {
    const modalRef = this.modalService.open(ModalComponent, { centered: true });
  }

  openInNewTab(url) {
    window.open(url, '_blank').focus();
  }

  validate(password) {
    this.api.authenticate(this.tokenStorage.getToken()).subscribe(
      (res) => {
        if(res.id!==null){
        password.view = true;
        setTimeout(() => {
          password.view = false;
        }, 5000);}else{
           password.view = false;
        }
      },
      (err) => {
        password.view = false;
      }
    );
  }

  logout() {
    this.tokenStorage.signOut();
  }
}
