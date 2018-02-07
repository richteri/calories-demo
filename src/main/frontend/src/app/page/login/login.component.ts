import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';
import { User } from '../../domain/user';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/components/common/messageservice';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;

  constructor(private authenticationService: AuthenticationService,
              private router: Router,
              private messageService: MessageService) { }

  ngOnInit() {
  }

  login() {
    this.authenticationService.login(this.username, this.password).subscribe(
      (user: User) => {
        this.router.navigateByUrl('/');
      },
      (response: any) => {
        console.log(response);
        this.messageService.add({severity: 'error', summary: 'Login Failed', detail: `Error ${response.status}: Bad Credentials`})
      });
  }

}
