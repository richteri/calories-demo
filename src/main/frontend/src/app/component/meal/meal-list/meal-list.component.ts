import { Component, Input, OnInit } from '@angular/core';
import { User } from '../../../domain/user';
import { UserService } from '../../../service/user.service';
import { Meal } from '../../../domain/meal';

/**
 * Group structure description
 */
class GroupMeta {
  constructor(public index: number,
              public size: number,
              public calories: number) {
  }
}

@Component({
  selector: 'app-meal-list',
  templateUrl: './meal-list.component.html',
  styleUrls: ['./meal-list.component.css']
})
export class MealListComponent implements OnInit {

  meals: Meal[] = [];
  private groupMeta: Map<string, GroupMeta> = new Map<string, GroupMeta>();

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
      this.userService.findMeals(this.user).subscribe((meals: Meal[]) => {
        // make sure meals are sorted
        this.meals = meals.sort((a, b) => {
          // date desc, time asc, nulls to the top
          const dateCompare = (a.date || 'z').localeCompare(b.date || 'z') * -1;
          return dateCompare || (a.time || '!').localeCompare(b.time || '!');
        });
        this.updateGroupMeta();
      });
    } else {
      this.meals = [];
      this.updateGroupMeta();
    }
  }

  updateGroupMeta() {
    this.groupMeta.clear();
    for (let i = 0; i < this.meals.length; i++) {
      const date = this.groupKey(this.meals[i].date);
      const calories = this.meals[i].calories || 0;
      if (i == 0) {
        this.groupMeta.set(date, new GroupMeta(0, 1, calories));
      } else {
        const previousRowData = this.meals[i - 1];
        if (date === this.groupKey(previousRowData.date)) {
          this.groupMeta.get(date).size++;
          this.groupMeta.get(date).calories += calories;
        } else {
          this.groupMeta.set(date, new GroupMeta(i, 1, calories));
        }
      }
    }
  }

  private groupKey(date: string) {
    return date || '_NULL';
  }

  group(date: string): GroupMeta {
    return this.groupMeta.get(this.groupKey(date));
  }

}
