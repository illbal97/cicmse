import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WelcomeComponent } from './welcome.component';
import { WelcomeRoutes } from './welcome.routing';
import { MaterialModule } from '../../moduls/material/material.module';
import { AsanaComponent } from '../asana/asana.component';

@NgModule({
  imports: [
    CommonModule,
    WelcomeRoutes,
    MaterialModule
  ],
  declarations: [WelcomeComponent]
})
export class WelcomeModule { }
