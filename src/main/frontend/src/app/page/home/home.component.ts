import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';
import { User } from '../../domain/user';
import { UserService } from '../../service/user.service';
import { Meal } from '../../domain/meal';
import { Observable } from 'rxjs/Observable';
import { HttpErrorResponse } from '@angular/common/http';
import { MessageService } from 'primeng/components/common/messageservice';

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
  editedMeal: Meal;

  auth = AuthenticationService;

  constructor(private authenticationService: AuthenticationService,
              private userService: UserService,
              private messageService: MessageService) {
  }

  ngOnInit() {
    if (this.auth.manager()) {
      this.userService.findAll().subscribe(users => this.users = users);
    } else {
      this.users = [this.auth.principal()];
      this.userSelect(this.auth.principal());
    }
  }

  logout() {
    this.authenticationService.logout();
  }

  userSelect(user: User) {
    this.editedUser = null;
    this.selectedUser = user;
    if ((this.auth.user() || this.auth.admin()) && user && user.id) {
      this.userService.findMeals(user).subscribe((meals: Meal[]) => {
        this.meals = meals;
      });
    } else {
      this.meals = [];
    }
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
        this.userSelect(null);
      } else if (this.users.length > index) {
        this.userSelect(this.users[index]);
      } else {
        this.userSelect(this.users[this.users.length - 1]);
      }
    });
  }

  userCancel() {
    this.editedUser = null;
  }

  userSave(user: User) {
    if (user.id) {
      this.userService.update(user).subscribe(
        retrieved => {
        const index = this.users.findIndex(u => u.id === retrieved.id);
        this.users.splice(index, 1, retrieved);
        this.selectedUser = retrieved;
        this.editedUser = null;
      }, (response: HttpErrorResponse) => {
          if (response.error.exception.indexOf('DataIntegrityViolationException')) {
            this.messageService.add({severity:'error', summary:'Update Failed', detail: 'Username already in use'});
          } else {
            this.messageService.add({severity:'error', summary:'Update Failed', detail: response.message});
          }
      });
    } else {
      this.userService.create(user).subscribe(retrieved => {
        this.users.push(retrieved);
        this.selectedUser = retrieved;
        this.editedUser = null;
      }, (response: HttpErrorResponse) => {
        if (response.error.exception.indexOf('DataIntegrityViolationException')) {
          this.messageService.add({severity:'error', summary:'Create Failed', detail: 'Username already in use'});
        } else {
          this.messageService.add({severity:'error', summary:'Create Failed', detail: response.message});
        }
      });
    }
  }

  mealEdit(meal: Meal) {
    this.editedMeal = Object.assign(new Meal(), meal);
  }

  mealCreate() {
    this.editedMeal = new Meal();
  }

  mealDelete(meal: Meal) {
    this.userService.deleteMeal(this.selectedUser, meal).subscribe(() => {
      const copy = this.meals.slice();
      const index = copy.findIndex(m => m.id === meal.id);
      copy.splice(index, 1);
      this.meals = copy;
    });
  }

  mealCancel() {
    this.editedMeal = null;
  }

  mealSave(meal: Meal) {
    if (meal.id) {
      this.userService.updateMeal(this.selectedUser, meal).subscribe(retrieved => {
        const copy = this.meals.slice();
        const index = copy.findIndex(m => m.id === meal.id);
        copy.splice(index, 1, retrieved);
        this.meals = copy;
        this.editedMeal = null;
      });
    } else {
      this.userService.createMeal(this.selectedUser, meal).subscribe(retrieved => {
        const copy = this.meals.slice();
        copy.push(retrieved);
        this.meals = copy;
        this.editedMeal = null;
      });
    }
  }
}
