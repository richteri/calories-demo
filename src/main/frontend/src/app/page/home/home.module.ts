import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ServiceModule } from '../../service/service.module';
import { HomeComponent } from './home.component';
import { UserModule } from '../../component/user/user.module';
import { MealModule } from '../../component/meal/meal.module';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';

@NgModule({
  imports: [
    CommonModule,
    ServiceModule,
    UserModule,
    MealModule,
    ButtonModule,
    DialogModule
  ],
  declarations: [HomeComponent]
})
export class HomeModule { }
