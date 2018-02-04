import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';
import { User } from '../../domain/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;
  error: string;

  constructor(private authenticationService: AuthenticationService, private router: Router) { }

  ngOnInit() {
  }

  login() {
    this.authenticationService.login(this.username, this.password).subscribe(
      (user: User) => {
        this.router.navigateByUrl('/');
      },
      (response: any) => {
        console.log(response);
        this.error = `Error ${response.status}: Bad Credentials`;
      });
  }

}
