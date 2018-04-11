import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { routing } from './app.routing';
import { ServiceModule } from './service/service.module';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { HomeModule } from './page/home/home.module';
import { LoginModule } from './page/login/login.module';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { GrowlModule } from 'primeng/growl';
import { MessageService } from 'primeng/components/common/messageservice';
import { CustomHttpInterceptor } from './service/custom-http-interceptor';
import { JwtModule } from '@auth0/angular-jwt';

export function tokenGetter() {
  return localStorage.getItem('access_token');
}

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    NoopAnimationsModule,
    HttpClientModule,
    routing,
    ServiceModule,
    HomeModule,
    LoginModule,
    GrowlModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        // only the /api needs these tokens
        whitelistedDomains: ['localhost:8080/api/', 'localhost:4200/api/', 'localhost/api/'],
        blacklistedRoutes: ['localhost:8080/oauth/', 'localhost:4200/oauth/'],
        throwNoTokenError: true,
        skipWhenExpired: true
      }
    })
  ],
  providers: [
    MessageService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CustomHttpInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
