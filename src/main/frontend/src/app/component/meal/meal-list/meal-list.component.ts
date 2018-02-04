import { Component, Input, OnInit } from '@angular/core';
import { User } from '../../../domain/user';
import { UserService } from '../../../service/user.service';
import { Meal } from '../../../domain/meal';

@Component({
  selector: 'app-meal-list',
  templateUrl: './meal-list.component.html',
  styleUrls: ['./meal-list.component.css']
})
export class MealListComponent implements OnInit {

  meals: Meal[] = [];
  rowGroupMetadata: any;

  _user: User;

  get user() {
    return this._user;
  }

  @Input()
  set user(user: User) {
    this._user = user;
    this.refreshMeals();
  }

  constructor(private userService: UserService) {
  }

  ngOnInit() {
  }

  refreshMeals() {
    if (this.user && this.user.id) {
      this.userService.findMeals(this.user).subscribe(meals => {
        this.meals = meals;
        this.updateRowGroupMetaData();
      });
    } else {
      this.meals = [];
      this.updateRowGroupMetaData();
    }
  }

  updateRowGroupMetaData() {
    this.rowGroupMetadata = {};
    if (this.meals) {
      for (let i = 0; i < this.meals.length; i++) {
        const meal = this.meals[i];
        if (i == 0) {
          this.rowGroupMetadata[meal.date] = {index: 0, size: 1, calories: meal.calories || 0};
        } else {
          const previousRowData = this.meals[i - 1];
          if (meal.date === previousRowData.date) {
            this.rowGroupMetadata[meal.date].size++;
            this.rowGroupMetadata[meal.date].calories += meal.calories;
          } else {
            this.rowGroupMetadata[meal.date] = {index: i, size: 1, calories: meal.calories || 0};
          }
        }
      }
    }
    console.log(this.rowGroupMetadata);
  }

  dayStyleClass(date: string): string {
    if (!this.user || !this.user.calories || !this.rowGroupMetadata[date] || !this.rowGroupMetadata[date].calories) {
      return 'style-unknown';
    } else {
      if (this.user.calories >= this.rowGroupMetadata[date].calories) {
        return 'style-ok';
      } else {
        return 'style-exceeded';
      }
    }
  }

}
