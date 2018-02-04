import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserListComponent } from './user-list/user-list.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { UserEditComponent } from './user-edit/user-edit.component';
import { ServiceModule } from '../../service/service.module';

@NgModule({
  imports: [
    CommonModule,
    ServiceModule
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
