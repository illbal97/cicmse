import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WelcomeComponent } from './welcome.component';
import { WelcomeRoutes } from './welcome.routing.module';
import { MaterialModule } from '../../moduls/material/material.module';

@NgModule({
  imports: [
    CommonModule,
    WelcomeRoutes,
    MaterialModule
  ],
  declarations: [WelcomeComponent]
})
export class WelcomeModule { }
