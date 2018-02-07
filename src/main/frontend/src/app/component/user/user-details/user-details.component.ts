import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { User } from '../../../domain/user';
import { AuthenticationService } from '../../../service/authentication.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent {

  @Input()
  user: User;

  @Output()
  edit = new EventEmitter<any>();

  @Output()
  delete = new EventEmitter<any>();

  @Output()
  create = new EventEmitter<any>();

  auth = AuthenticationService;

  constructor() { }

}
