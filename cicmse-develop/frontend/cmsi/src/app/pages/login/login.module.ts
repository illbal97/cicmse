import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login.component';
import { LoginRoute } from './login.routing.module';
import { MaterialModule } from '../../moduls/material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';



@NgModule({
  declarations: [LoginComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    LoginRoute,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class LoginModule { }
