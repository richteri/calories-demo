import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { User } from '../domain/user';
import { Router } from '@angular/router';

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

  currentUser(): User {
    return JSON.parse(localStorage.getItem('currentUser'));
  }
}
