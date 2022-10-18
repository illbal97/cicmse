import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AsanaComponent } from './asana.component';
import { MaterialModule } from '../../moduls/material/material.module';
import { AsanaRoutes } from './asana.routing';
import { ReactiveFormsModule } from '@angular/forms';
import { AsanaProjectDialogComponent } from 'src/app/components/asana-project-dialog/asana-project-dialog.component';
import { AddingAsanaPersonalAccessTokenComponent } from 'src/app/components/adding-asana-personal-access-token/adding-asana-personal-access-token.component';
import { ConnectionTimeoutComponent } from 'src/app/components/connection-timeout/connection-timeout.component';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    AsanaRoutes,
    ReactiveFormsModule,
  ],
  declarations: [
    AsanaComponent,
    AsanaProjectDialogComponent,
    ConnectionTimeoutComponent,
    AddingAsanaPersonalAccessTokenComponent
  ]
})
export class AsanaModule { }
