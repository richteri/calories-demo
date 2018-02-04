import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';
import { User } from '../../domain/user';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  currentUser: User;
  selectedUser: User;

  constructor(private authenticationService: AuthenticationService) {
    this.currentUser = authenticationService.currentUser();
  }

  ngOnInit() {
  }

  logout() {
    this.authenticationService.logout();
  }

  selectUser(user: User) {
    this.selectedUser = user;
  }

}
