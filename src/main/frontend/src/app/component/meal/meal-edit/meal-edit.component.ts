import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild } from '@angular/core';
import { Meal } from '../../../domain/meal';
import { NgForm } from '@angular/forms';
import { SimpleChanges } from '@angular/core/src/metadata/lifecycle_hooks';

@Component({
  selector: 'app-meal-edit',
  templateUrl: './meal-edit.component.html',
  styleUrls: ['./meal-edit.component.css']
})
export class MealEditComponent implements OnChanges {

  private static readonly TIME_RE: RegExp = /^\d\d:\d\d:\d\d$/;
  @Input()
  meal: Meal;

  @ViewChild('form')
  form: NgForm;

  @Output()
  cancel = new EventEmitter<void>();

  @Output()
  save = new EventEmitter<Meal>();

  date: Date;
  time: Date;
  constructor() { }

  ngOnChanges(changes: SimpleChanges) {
    if (this.meal.time && MealEditComponent.TIME_RE.test(this.meal.time)) {
      const t = this.meal.time.split(':').map(t => parseInt(t));
      this.time = new Date(Date.UTC(1970, 0, 1, t[0], t[1], t[2]));
    }
  }

  timeSelect() {
    this.meal.time = JSON.stringify(this.time).substr(12, 8);
  }

  saveClick() {
    if (this.form.valid) {
      this.save.emit(this.meal);
    }
  }
}
