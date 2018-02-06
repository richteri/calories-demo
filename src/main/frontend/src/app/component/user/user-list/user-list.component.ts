import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { User } from '../../../domain/user';
import { UserService } from '../../../service/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent {

  @Input()
  users: User[] = [];

  @Input()
  selectedUser: User;

  @Output()
  userSelected = new EventEmitter<User>();

  constructor() { }

  rowSelect() {
    this.userSelected.emit(this.selectedUser);
  }

}
