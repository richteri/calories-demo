import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MealListComponent } from './meal-list/meal-list.component';
import { MealEditComponent } from './meal-edit/meal-edit.component';
import { ServiceModule } from '../../service/service.module';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { CalendarModule, DropdownModule, InputTextModule, SpinnerModule } from 'primeng/primeng';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    ServiceModule,
    TableModule,
    FormsModule,
    ButtonModule,
    CalendarModule,
    SpinnerModule,
    InputTextModule,
    DropdownModule
  ],
  declarations: [
    MealListComponent,
    MealEditComponent
  ],
  exports: [
    MealListComponent,
    MealEditComponent
  ]
})
export class MealModule {
}
