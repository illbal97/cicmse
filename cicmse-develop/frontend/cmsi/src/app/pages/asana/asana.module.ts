import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AsanaComponent } from './asana.component';
import { MaterialModule } from '../../moduls/material/material.module';
import { AsanaRoutes } from './asana.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AsanaProjectDialogComponent } from 'src/app/components/asana-project-dialog/asana-project-dialog.component';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    AsanaRoutes,
    ReactiveFormsModule,
  ],
  declarations: [AsanaComponent, AsanaProjectDialogComponent]
})
export class AsanaModule { }
