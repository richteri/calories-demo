import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { User } from '../../../domain/user';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  @Input()
  user: User;

  @Output()
  edit = new EventEmitter<any>();

  @Output()
  create = new EventEmitter<any>();

  @Output()
  delete = new EventEmitter<any>();

  constructor() { }

  ngOnInit() {
  }

}
