import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { User } from '../../../domain/user';
import { Meal } from '../../../domain/meal';
import { Table } from 'primeng/table';

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

  @ViewChild('table')
  table: Table;

  yearOptions: {value: string; label: string}[];
  monthOptions: {value: string; label: string}[];
  categoryOptions: {value: string; label: string}[];

  filterYear = '';
  filterMonth = '';
  filterCategory = '';

  constructor() {
    this.yearOptions = [{value: '', label: ''}];
    this.monthOptions = [{value: '', label: ''}];
    for (let i = 1; i <= 12; i++) {
      const month = i < 10 ? '0' + i : '' + i;
      this.monthOptions.push({value: '-' + month + '-', label: month});
      const currentYear = new Date().getFullYear();
      const year = '' + (currentYear - 6 + i);
      this.yearOptions.push({value: year, label: year})
    }
    this.categoryOptions = [
      {value: '', label: ''},
      {value: 'breakfast', label: 'breakfast'},
      {value: 'lunch', label: 'lunch'},
      {value: 'dinner', label: 'dinner'}
    ];
  }

  private category(time: string): string {
    if (!time) {
      return 'unknown';
    }
    if (time < '12:00:00') {
      return 'breakfast';
    }

    if (time > '15:00:00') {
      return 'dinner';
    }

    return 'lunch';
  }

  private updateGroupMeta() {
    this.groupMeta.clear();
    for (let i = 0; i < this.meals.length; i++) {
      const date = this.groupKey(this.meals[i].date);
      const calories = this.meals[i].calories || 0;
      this.meals[i]['_$category'] = this.category(this.meals[i].time);
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

  filtered(): boolean {
    return !!this.filterYear || !!this.filterMonth || !!this.filterCategory;
  }

  filterDate() {
    this.table.filter(this.filterYear + this.filterMonth, 'date', 'contains');
  }

  filterTime() {
    this.table.filter(this.filterCategory, '_$category', 'equals');
  }

}
