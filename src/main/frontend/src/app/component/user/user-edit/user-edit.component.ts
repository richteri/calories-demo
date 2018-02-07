import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { User } from '../../../domain/user';
import { Role } from '../../../domain/role';
import { NgForm } from '@angular/forms';
import { UserService } from '../../../service/user.service';
import { AuthenticationService } from '../../../service/authentication.service';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent {

  readonly USERNAME_REG_EXP: RegExp = /^[a-zA-Z0-9._-]{1,50}$/;

  @Input()
  user: User;

  @ViewChild('form')
  form: NgForm;

  @Output()
  save = new EventEmitter<any>();

  @Output()
  cancel = new EventEmitter<any>();

  roleOptions: {value: Role; label: string}[] = [
    {value: Role.USER, label: 'User'},
    {value: Role.MANAGER, label: 'Manager'},
    {value: Role.ADMIN, label: 'Admin'}
  ];

  auth = AuthenticationService;

  constructor() { }

  saveClick() {
    if (this.form.valid) {
      this.save.emit(this.user);
    }
  }

}
