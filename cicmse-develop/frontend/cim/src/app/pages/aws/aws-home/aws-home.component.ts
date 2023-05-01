import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { lastValueFrom, Subscription } from 'rxjs';
import { AwsEc2InstanceCreateComponent } from 'src/app/components/aws/aws-ec2-instance-create/aws-ec2-instance-create.component';
import { AwsS3RdsCreationComponent } from 'src/app/components/aws/aws-s3-rds-creation/aws-s3-rds-creation.component';
import { User } from 'src/app/model/user.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { AwsService } from 'src/app/services/aws/aws.service';

@Component({
  selector: 'app-aws-home',
  templateUrl: './aws-home.component.html',
  styleUrls: ['./aws-home.component.scss']
})
export class AwsHomeComponent implements OnInit {

  awsStatus: String = "";
  isLoading: boolean = false;
  awsEC2InstancesActive: any = [];
  awsEC2InstancesInactive: any = [];
  user = new User();
  subscriptionUser: Subscription | undefined;
  subscriptionAwsEC2Instances: Subscription | undefined;
  constructor(private authenticationService: AuthenticationService, private awsService: AwsService,  private awsEC2CreationDialog: MatDialog,  private awsRDSAndS3CreationDialog: MatDialog ) { }

  ngOnInit(): void {
    this.isLoading = false;
    this.subscriptionUser = this.authenticationService.currentUser.subscribe((data: User) => {
      this.user = data;

    });

    this.subscriptionAwsEC2Instances = this.loadEC2instances()
  }

  async addAccessKeys(keys: Array<String>) {
   await lastValueFrom(this.awsService.setAWSAccessKeysForUser(this.user, keys )).then( u => {
      this.authenticationService.setCurrentUser(u);
    }).catch(error => {
      console.log(error)
    })
    this.isLoading = false;
    this.ngOnInit()

  }

loadEC2instances(statusChanged = false) {
  return this.awsService.getEC2Instances(this.user, statusChanged).subscribe({
    next: (instance: any) => {
      switch (instance.toString()) {
        case "Bad AWS access keys": {
          this.awsStatus = "Bad AWS access keys"
          break;
        }
        case "There are not any AWS account": {
          this.awsStatus = "There are not any AWS account";
          break;
        }
        default: {
          this.awsStatus = "AWS account is active";
          this.awsEC2InstancesActive = instance.filter((i: any) => i.state == "running" || i.state == "pending" || i.state == "initializing");
          this.awsEC2InstancesInactive = instance.filter((i: any) => i.state == "stopped" || i.state == "stopping");
          break;
        }
      }
    },
    error: (err: string) => {
      console.error(err);
      this.isLoading = true;
    },
    complete: () => { this.isLoading = true;}
  });
}

 async drop(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);


    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex,
      );

        if(event.item.data.state == "running" || event.item.data.state == "pending") {
          await lastValueFrom(this.awsService.stopEC2Instance(this.user, event.item.data.instanceId)).then(message => {}).catch(error => {console.log(error)})
          this.loadEC2instances(true)
        }else if(event.item.data.state == "stopped" || event.item.data.state == "stopping") {
          await lastValueFrom(this.awsService.startEC2Instance(this.user, event.item.data.instanceId)).then(message => {}).catch(error => {console.log(error)})
          this.loadEC2instances(true)
        }

  }
}

  openEC2ProjectCreationDialog() {
    const dialogRef = this.awsEC2CreationDialog.open(AwsEc2InstanceCreateComponent, {
      width: "600px",
      height: "600px",
      panelClass: "custom-modalbox",
      data: {user: this.user}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed' + result);
      if (result != undefined) {
        console.log(result)
        this.loadEC2instances(true);
        }
    });
  }

  openS3AndRDSCreationDialog() {
    const dialogRef = this.awsRDSAndS3CreationDialog.open(AwsS3RdsCreationComponent, {
      width: "600px",
      panelClass: "custom-modalbox",
      data: {user: this.user}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed' + result);
      if (result != undefined) {
        console.log(result)
        this.loadEC2instances(true);
        }
    });
  }

}
