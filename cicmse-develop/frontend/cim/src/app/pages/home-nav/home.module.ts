import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { MaterialModule } from '../../moduls/material/material.module';
import { HomeRoutes } from './home.routing';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    HomeRoutes
  ],
  declarations: [HomeComponent]
})
export class HomeModule { }
