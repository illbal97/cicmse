import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { LoggerModule, NgxLoggerLevel } from "ngx-logger";
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthenInterceptor } from './interceptor/authentication.interceptor';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RefreshTokenInvalidComponent } from './components/refresh-token-invalid/refresh-token-invalid.component';
import {DatePipe} from '@angular/common';
@NgModule({
  declarations: [
    AppComponent,
    RefreshTokenInvalidComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    LoggerModule.forRoot({
      serverLoggingUrl: '/api/logs',
      level: NgxLoggerLevel.DEBUG,
      serverLogLevel: NgxLoggerLevel.ERROR
    }),
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS, useClass: AuthenInterceptor, multi: true
    },
    {
      provide: DatePipe
    }
],

  bootstrap: [AppComponent]
})
export class AppModule { }
