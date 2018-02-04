import { Routes, RouterModule } from '@angular/router';

import { AuthenticationGuard } from './service/authentication.guard';
import { HomeComponent } from './page/home/home.component';
import { LoginComponent } from './page/login/login.component';

const appRoutes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthenticationGuard] },
  { path: 'login', component: LoginComponent },

  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);
