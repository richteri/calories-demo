import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { routing } from './app.routing';
import { ServiceModule } from './service/service.module';
import { HttpClientModule } from '@angular/common/http';
import { HomeModule } from './page/home/home.module';
import { LoginModule } from './page/login/login.module';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { GrowlModule } from 'primeng/growl';
import { MessageService } from 'primeng/components/common/messageservice';


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
    GrowlModule
  ],
  providers: [MessageService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
