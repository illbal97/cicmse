import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AsanaProjectDialogComponent } from 'src/app/components/asana/asana-project-dialog/asana-project-dialog.component';
import { AddingAsanaPersonalAccessTokenComponent } from 'src/app/components/asana/adding-asana-personal-access-token/adding-asana-personal-access-token.component';
import { ConnectionTimeoutComponent } from 'src/app/components/connection-timeout/connection-timeout.component';
import { MaterialModule } from 'src/app/moduls/material/material.module';
import { AsanaComponent } from './asana.component';
import { AsanaRoutes } from './asana.routing';


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
