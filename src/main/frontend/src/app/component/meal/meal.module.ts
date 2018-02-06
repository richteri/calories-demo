import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MealListComponent } from './meal-list/meal-list.component';
import { MealDetailsComponent } from './meal-details/meal-details.component';
import { MealEditComponent } from './meal-edit/meal-edit.component';
import { ServiceModule } from '../../service/service.module';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';

@NgModule({
  imports: [
    CommonModule,
    ServiceModule,
    TableModule,
    ButtonModule
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
