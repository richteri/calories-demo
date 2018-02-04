import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ServiceModule } from '../../service/service.module';
import { HomeComponent } from './home.component';
import { UserModule } from '../../component/user/user.module';
import { MealModule } from '../../component/meal/meal.module';

@NgModule({
  imports: [
    CommonModule,
    ServiceModule,
    UserModule,
    MealModule
  ],
  declarations: [HomeComponent]
})
export class HomeModule { }
