import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AsanaTaskRoutingModule } from './asana-task-routing.module';
import { AsanaTaskComponent } from './asana-task.component';
import { MaterialModule } from 'src/app/moduls/material/material.module';
import { AsanaSectionComponent } from 'src/app/components/asana-section/asana-section.component';
import { AsanaTaskDialogComponent } from 'src/app/components/asana-task-dialog/asana-task-dialog.component';
import { DateTimePipe } from 'src/app/pipe/date-time.pipe';
import { DateTimeFormatterPipe } from 'src/app/pipe/date-time-formatter.pipe';


@NgModule({
  declarations: [
    AsanaTaskComponent,
    AsanaTaskDialogComponent,
    AsanaSectionComponent,
    DateTimePipe,
    DateTimeFormatterPipe
  ],
  imports: [
    CommonModule,
    AsanaTaskRoutingModule,
    MaterialModule
  ],
})
export class AsanaTaskModule { }
