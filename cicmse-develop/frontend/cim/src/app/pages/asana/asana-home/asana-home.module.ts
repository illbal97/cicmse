import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AsanaProjectDialogComponent } from 'src/app/components/asana/asana-project-dialog/asana-project-dialog.component';
import { AddingAsanaPersonalAccessTokenComponent } from 'src/app/components/asana/adding-asana-personal-access-token/adding-asana-personal-access-token.component';
import { ConnectionTimeoutComponent } from 'src/app/components/connection-timeout/connection-timeout.component';
import { MaterialModule } from 'src/app/moduls/material/material.module';
import { AsanaHomeComponent } from './asana-home.component';
import { AsanaHomeRoutingModule } from './asana-home.routing.module';


@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    AsanaHomeRoutingModule,
    ReactiveFormsModule,

  ],
  declarations: [
    AsanaHomeComponent,
    AsanaProjectDialogComponent,
    ConnectionTimeoutComponent,
    AddingAsanaPersonalAccessTokenComponent
  ]
})
export class AsanaModule { }
