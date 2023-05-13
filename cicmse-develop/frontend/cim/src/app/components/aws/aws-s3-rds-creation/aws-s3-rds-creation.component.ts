import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { lastValueFrom } from 'rxjs';
import { RDSConfig } from 'src/app/model/aws/rds-config';
import { User } from 'src/app/model/user.model';
import { AwsHomeComponent } from 'src/app/pages/aws/aws-home/aws-home.component';
import { AwsService } from 'src/app/services/aws/aws.service';


@Component({
  selector: 'app-aws-s3-rds-creation-dialog',
  templateUrl: './aws-s3-rds-creation.component.html',
  styleUrls: ['./aws-s3-rds-creation.component.scss']
})
export class AwsS3RdsCreationComponent implements OnInit {
  S3RDSCreationDialog: FormGroup;
  isS3Created = false;
  isRDSCreated = false;
  RDSConfig = new RDSConfig();
  databaseEngines = [
    'postgres', "mysql", "mariadb", "oracle"
  ]

  constructor(private formBuilder: FormBuilder,
    private awsService: AwsService,
    public dialogRef: MatDialogRef<AwsHomeComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { user: User }) {
    this.S3RDSCreationDialog = formBuilder.group({
      bucketName: ['', Validators.pattern('^[a-z0-9]*$')],
      dbIdentifier: ['', Validators.pattern('^[a-zA-Z]*$')],
      dbInstanceType: ['db.t3.micro'],
      engine: [this.databaseEngines[1]],
      password: ['', Validators.minLength(10)],
      username: [data.user.username],
      dbName: [''],
      allocatedStorage: [20, [Validators.min(20), Validators.max(100)]]


    })
  }

  async createS3AndRDS() {
    this.RDSConfig.dbIdentifier = this.S3RDSCreationDialog.value.dbIdentifier;
    this.RDSConfig.dbInstanceType = this.S3RDSCreationDialog.value.dbInstanceType;
    this.RDSConfig.dbName = this.S3RDSCreationDialog.value.dbName;
    this.RDSConfig.engine = this.S3RDSCreationDialog.value.engine;
    this.RDSConfig.username = this.S3RDSCreationDialog.value.username;
    this.RDSConfig.password = this.S3RDSCreationDialog.value.password;
    this.RDSConfig.allocatedStorage = this.S3RDSCreationDialog.value.allocatedStorage;

    await lastValueFrom(this.awsService.createS3(this.data.user, this.S3RDSCreationDialog.value.bucketName)).then(s3 => {
      this.isS3Created = true;
    }).catch(err => {
      //console.log(err)
    });

    await lastValueFrom(this.awsService.createRDS(this.data.user, this.RDSConfig)).then(rds => {
      this.isRDSCreated = true;
    }).catch(err => {
      //console.log(err)
    });
    if (this.isRDSCreated && this.isS3Created) {
      this.dialogRef.close(true);
    }else {
      this.dialogRef.close(false);
    }


  }

  ngOnInit(): void {
  }

}



