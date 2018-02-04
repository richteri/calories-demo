import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MealListComponent } from './meal-list/meal-list.component';
import { MealDetailsComponent } from './meal-details/meal-details.component';
import { MealEditComponent } from './meal-edit/meal-edit.component';
import { ServiceModule } from '../../service/service.module';

@NgModule({
  imports: [
    CommonModule,
    ServiceModule
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
