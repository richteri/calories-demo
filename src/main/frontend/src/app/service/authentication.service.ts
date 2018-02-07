import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { User } from '../domain/user';
import { Router } from '@angular/router';
import { Role } from '../domain/role';

@Injectable()
export class AuthenticationService {
  constructor(private http: HttpClient, private router: Router) { }

  login(username: string, password: string): Observable<User> {
    return this.http.get<User>('/api/users/current-user', {
      headers: {
        'Authorization': 'Basic ' + btoa(username + ':' + password)
      }
    }).map(user => {
        // login successful if there's a jwt token in the response
        if (user && user.id) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify(user));
        }

        return user;
      });
  }

  logout() {
    // remove user from local storage to log user out
    this.http.get<any>('/logout')
      .subscribe(() => {
        localStorage.removeItem('currentUser');
        this.router.navigateByUrl('/login?returnUrl=%2F');
      });
  }

  static principal(): User {
    return JSON.parse(localStorage.getItem('currentUser'));
  }

  static admin(): boolean {
    const user = this.principal();
    return user && user.role === Role.ADMIN;
  }

  static manager(): boolean {
    const user = this.principal();
    return user && (user.role === Role.ADMIN || user.role === Role.MANAGER);
  }

  static user(): boolean {
    const user = this.principal();
    return user && user.role === Role.USER;
  }
}
