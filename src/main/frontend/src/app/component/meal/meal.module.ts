import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MealListComponent } from './meal-list/meal-list.component';
import { MealDetailsComponent } from './meal-details/meal-details.component';
import { MealEditComponent } from './meal-edit/meal-edit.component';
import { ServiceModule } from '../../service/service.module';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { CalendarModule, InputTextModule, SpinnerModule } from 'primeng/primeng';
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
    InputTextModule
  ],
  declarations: [
    MealListComponent,
    MealDetailsComponent,
    MealEditComponent
  ],
  exports: [
    MealListComponent,
    MealDetailsComponent,
    MealEditComponent
  ]
})
export class MealModule {
}
