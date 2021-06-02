import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ApiService } from 'src/app/Api/api.service';
import { FormControl } from '@angular/forms';
import { TokenStorageService } from '../../auth/token-storage.service';
import { PasswordEntity } from '../../models/PasswordEntity';
@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class ModalComponent implements OnInit {
  constructor(
    public activeModal: NgbActiveModal,
    private tokenStorage: TokenStorageService,
    private api: ApiService
  ) {}
  clicked = false;
  name = new FormControl('');
  uEncPassword = new FormControl('');
  url = new FormControl('');

  ngOnInit(): void {}

  public add() {
    this.api
      .addPassword(
        this.tokenStorage.getToken(),
        new PasswordEntity(
          this.uEncPassword.value,
          this.name.value,
          this.url.value
        )
      )
      .subscribe(
        (res) => {
          this.activeModal.close('Close click');
          location.reload();
        },
        (err) => {
          this.activeModal.close('Close click');
        }
      );
  }
}
