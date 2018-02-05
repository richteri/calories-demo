import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserListComponent } from './user-list/user-list.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { UserEditComponent } from './user-edit/user-edit.component';
import { ServiceModule } from '../../service/service.module';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { CalendarModule, DropdownModule, InputTextModule, KeyFilterModule, SpinnerModule } from 'primeng/primeng';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ServiceModule,
    TableModule,
    ButtonModule,
    CalendarModule,
    SpinnerModule,
    InputTextModule,
    DropdownModule,
    KeyFilterModule
  ],
  declarations: [
    UserListComponent,
    UserDetailsComponent,
    UserEditComponent
  ],
  exports: [
    UserListComponent,
    UserDetailsComponent,
    UserEditComponent
  ]
})
export class UserModule { }
