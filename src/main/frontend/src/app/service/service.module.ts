import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from './user.service';
import { AuthenticationService } from './authentication.service';
import { AuthenticationGuard } from './authentication.guard';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  providers: [
    UserService,
    AuthenticationService,
    AuthenticationGuard
  ]
})
export class ServiceModule { }
