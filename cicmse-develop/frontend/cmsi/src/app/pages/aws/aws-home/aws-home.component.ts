import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
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
  awsEC2Instances: any = [];
  user = new User();
  subscriptionUser: Subscription | undefined;
  subscriptionAwsEC2Instances: Subscription | undefined;
  constructor(private authenticationService: AuthenticationService, private awsService: AwsService ) { }

  ngOnInit(): void {
    this.isLoading = false;
    this.subscriptionUser = this.authenticationService.currentUser.subscribe((data: User) => {
      this.user = data;

    });

    this.subscriptionAwsEC2Instances = this.awsService.getEC2Instances(this.user).subscribe({
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
            this.awsEC2Instances = instance;
            break;
          }
        }
      },
      error: (err: string) => {
        console.error(err);
        this.isLoading = true;
      },
      complete: () => { this.isLoading = true; console.log(this.awsEC2Instances)}
    });
  }

  addAccessKeys($event: Array<String>) {
    console.log($event);
  }

  addAwsSecretAccessKey($event: String) {

  }

  openEC2ProjectCreationDialog() {}

}
