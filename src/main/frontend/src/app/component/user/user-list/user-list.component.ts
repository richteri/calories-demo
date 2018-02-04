import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { User } from '../../../domain/user';
import { UserService } from '../../../service/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[] = [];
  selectedUser: User;

  @Output()
  userSelected = new EventEmitter<User>();

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.userService.findAll().subscribe(users => this.users = users);
  }

  rowSelect() {
    this.userSelected.emit(this.selectedUser);
  }

}
