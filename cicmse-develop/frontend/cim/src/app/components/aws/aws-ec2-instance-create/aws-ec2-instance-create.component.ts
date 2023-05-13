import { Component, Inject, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { lastValueFrom } from 'rxjs';
import { User } from 'src/app/model/user.model';
import { AwsHomeComponent } from 'src/app/pages/aws/aws-home/aws-home.component';
import { AwsService } from 'src/app/services/aws/aws.service';

const IMAGES = [
  {
    id: "ami-076309742d466ad69",
    name: "Amazon Linux"
  },
  {
    id: "ami-0caef02b518350c8b",
    name: "Ubuntu"
  },
  {
    id: "ami-05a60358d5cda31c5",
    name: "Windows"
  }

]


@Component({
  selector: 'app-aws-ec2-instance-create',
  templateUrl: './aws-ec2-instance-create.component.html',
  styleUrls: ['./aws-ec2-instance-create.component.scss']
})
export class AwsEc2InstanceCreateComponent implements OnInit, OnDestroy {
  images = IMAGES;
  ec2CreationDialog: FormGroup;
  instance: any;
  constructor(
    private awsService: AwsService,
    private formBuilder: FormBuilder,
    private ec2InstanceDialog: MatDialog,
    public dialogRef: MatDialogRef<AwsHomeComponent>,
    @Inject(MAT_DIALOG_DATA) public data:{user: User}
  ) {
    this.ec2CreationDialog = formBuilder.group({
      tagName: [''],
      keyName: [''],
      imageIds: ['']

    })
  }
  ngOnDestroy(): void {
  }

  ngOnInit(): void {

  }


  async createEC2Instance() {
    this.ec2CreationDialog.value.keyName =
      this.generateSecretKeyPrefix() + '-' + this.ec2CreationDialog.value.keyName;
    await lastValueFrom(this.awsService.createEC2Instance(this.data.user,
                                        this.ec2CreationDialog.value.imageIds,
                                        this.ec2CreationDialog.value.keyName,
                                        this.ec2CreationDialog.value.tagName)).then(i => {
                                            this.instance = i;
                                        }).catch( e => {
                                          //console.log(e)
                                        })
    this.dialogRef.close(this.instance);
  }

  private generateSecretKeyPrefix() {
    return new Date().getMilliseconds().toString();

  }

}


