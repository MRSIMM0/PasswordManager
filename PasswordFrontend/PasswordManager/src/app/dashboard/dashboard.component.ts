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

  en = new PasswordEntity('test', 'test', 'test');
  en2 = new PasswordEntity('test', 'test', 'test');
  en3 = new PasswordEntity('test', 'test', 'test');
  listOfPass: PasswordEntity[] = [this.en, this.en2, this.en3];

  isLoading = true;

  constructor(
    private api: ApiService,
    private tokenStorage: TokenStorageService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.api.authenticate(this.tokenStorage.getToken()).subscribe(
      (res) => {
        this.isLoggedIn = true;
        this.getAllPasswords();
      },
      (err) => {
        this.isLoggedIn = false;
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

  //Dopisac PasswordID jako doswolony header
  open() {
    const modalRef = this.modalService.open(ModalComponent, { centered: true });
  }

  openInNewTab(url) {
    window.open(url, '_blank').focus();
  }

  validate(password) {
    password.view = true;
    setTimeout(() => {
      password.view = false;
    }, 5000);
  }

  logout() {
    this.tokenStorage.signOut();
  }
}
