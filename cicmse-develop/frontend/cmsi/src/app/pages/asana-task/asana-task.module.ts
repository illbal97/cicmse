import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AsanaTaskRoutingModule } from './asana-task-routing.module';
import { AsanaTaskComponent } from './asana-task.component';
import { MaterialModule } from 'src/app/moduls/material/material.module';
import { AsanaSectionComponent } from 'src/app/components/asana-section/asana-section.component';


@NgModule({
  declarations: [
    AsanaTaskComponent,
    AsanaSectionComponent
  ],
  imports: [
    CommonModule,
    AsanaTaskRoutingModule,
    MaterialModule
  ],
})
export class AsanaTaskModule { }
