import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AwsHomeRoutingModule } from './aws-home-routing.module';
import { AwsHomeComponent } from './aws-home.component';
import { MaterialModule } from 'src/app/moduls/material/material.module';
import { AwsAccessKeysAddingComponent } from 'src/app/components/aws/aws-access-keys-adding/aws-access-keys-adding.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AwsEc2InstanceCreateComponent } from 'src/app/components/aws/aws-ec2-instance-create/aws-ec2-instance-create.component';
import { AwsS3RdsCreationComponent } from 'src/app/components/aws/aws-s3-rds-creation/aws-s3-rds-creation.component';


@NgModule({
  declarations: [AwsHomeComponent, AwsAccessKeysAddingComponent, AwsEc2InstanceCreateComponent, AwsS3RdsCreationComponent],
  imports: [
    CommonModule,
    AwsHomeRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class AwsHomeModule { }
