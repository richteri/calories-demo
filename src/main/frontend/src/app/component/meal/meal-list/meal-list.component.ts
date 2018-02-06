import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
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
export class MealListComponent {

  private _meals: Meal[] = [];
  private groupMeta: Map<string, GroupMeta> = new Map<string, GroupMeta>();

  get meals() {
    return this._meals;
  }

  @Input()
  user: User;

  @Input()
  set meals(meals: Meal[]) {
    this._meals = meals.sort((a, b) => {
      // date desc, time asc, nulls to the top
      const dateCompare = (a.date || 'z').localeCompare(b.date || 'z') * -1;
      return dateCompare || (a.time || '!').localeCompare(b.time || '!');
    });
    this.updateGroupMeta();
  }

  @Output()
  edit = new EventEmitter<Meal>();

  @Output()
  delete = new EventEmitter<Meal>();

  constructor() {
  }

  private updateGroupMeta() {
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
