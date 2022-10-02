import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AsanaComponent } from './asana.component';
import { MaterialModule } from '../../moduls/material/material.module';
import { AsanaRoutes } from './asana.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    AsanaRoutes,
    ReactiveFormsModule, 
  ],
  declarations: [AsanaComponent]
})
export class AsanaModule { }
