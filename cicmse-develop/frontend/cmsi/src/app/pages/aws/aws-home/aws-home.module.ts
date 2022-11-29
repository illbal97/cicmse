import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AwsHomeRoutingModule } from './aws-home-routing.module';
import { AwsHomeComponent } from './aws-home.component';
import { MaterialModule } from 'src/app/moduls/material/material.module';
import { AwsAccessKeysAddingComponent } from 'src/app/components/aws/aws-access-keys-adding/aws-access-keys-adding.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [AwsHomeComponent, AwsAccessKeysAddingComponent],
  imports: [
    CommonModule,
    AwsHomeRoutingModule,
    MaterialModule,
    ReactiveFormsModule
  ]
})
export class AwsHomeModule { }
