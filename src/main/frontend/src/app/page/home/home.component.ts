import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';
import { User } from '../../domain/user';
import { UserService } from '../../service/user.service';
import { Meal } from '../../domain/meal';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  /**
   * Logged in user
   */
  currentUser: User;

  users: User[] = [];

  selectedUser: User;
  editedUser: User;

  meals: Meal[] = [];

  constructor(private authenticationService: AuthenticationService,
              private userService: UserService) {
    this.currentUser = authenticationService.currentUser();
  }

  ngOnInit() {
    this.userService.findAll().subscribe(users => this.users = users);
  }

  logout() {
    this.authenticationService.logout();
  }

  userSelect(user: User) {
    this.editedUser = null;
    this.selectedUser = user;
    this.userService.findMeals(user).subscribe((meals: Meal[]) => {
      this.meals = meals;
    });
  }

  userCreate() {
    this.editedUser = new User();
  }

  userEdit() {
    this.editedUser = Object.assign(new User(), this.selectedUser);
  }

  userDelete() {
    this.userService.delete(this.selectedUser.id).subscribe(() => {
      const index = this.users.findIndex(u => u.id === this.selectedUser.id);
      this.users.splice(index, 1);
      if (!this.users.length) {
        this.selectedUser = null;
      } else if (this.users.length > index) {
        this.selectedUser = this.users[index];
      } else {
        this.selectedUser = this.users[this.users.length - 1];
      }
    });
  }

  userCancel() {
    this.editedUser = null;
  }

  userSave(user: User) {
    if (user.id) {
      this.userService.update(user).subscribe(retrieved => {
        const index = this.users.findIndex(u => u.id === retrieved.id);
        this.users.splice(index, 1, retrieved);
        this.selectedUser = retrieved;
        this.editedUser = null;
      });
    } else {
      this.userService.create(user).subscribe(retrieved => {
        this.users.push(retrieved);
        this.selectedUser = retrieved;
        this.editedUser = null;
      });
    }
  }

  mealEdit(meal: Meal) {

  }

  mealCreate(meal: Meal) {

  }

  mealDelete(meal: Meal) {

  }
}
