import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RDSConfig } from 'src/app/model/aws/rds-config';
import { User } from 'src/app/model/user.model';
import { environment } from 'src/environments/environment';
import { AuthenticationService } from '../authentication.service';
import { HeaderService } from '../base-header.service';

const API_URL = environment.ROOT_URL + '/api/v1/aws';

@Injectable({
  providedIn: 'root'
})
export class AwsService extends HeaderService {

  constructor(http: HttpClient, authenticationService: AuthenticationService) {
    super(http, authenticationService);
   }

   setAWSAccessKeysForUser(user: User, keys: Array<String>): Observable<any> {
    if (keys.length != 0 || keys != null) {
      user.awsAccessKey = keys[0];
      user.awsAccessSecretKey = keys[1];
    }

    return this.http.post<User>(API_URL + "/add-access-token", {user}, { headers: this.getHeader() });
  }

   getEC2Instances(user: User, statusChanged: boolean): Observable<any> {

    return this.http.post<any>(API_URL + "/ec2-instance", {user, statusChanged}, { headers: this.getHeader() })
  }

  startEC2Instance(user: User, instanceId: String): Observable<any> {

    return this.http.post<any>(API_URL + "/ec2-instance-start", {user, instanceId}, { headers: this.getHeader() })
  }

  createEC2Instance(user: User, imageId: String, keyName: String, tagName: String): Observable<any> {

    return this.http.post<any>(API_URL + "/ec2-instance-creation", {user, imageId, keyName, tagName}, { headers: this.getHeader() })
  }

  stopEC2Instance(user: User, instanceId: String): Observable<any> {

    return this.http.post<any>(API_URL + "/ec2-instance-stop", {user, instanceId}, { headers: this.getHeader() })
  }

  createS3(user: User, bucketName: String) {
    return this.http.post<any>(API_URL + "/S3-creation", {user, bucketName}, { headers: this.getHeader() })

  }

  createRDS(user: User, rdsConfig: RDSConfig) {
    return this.http.post<any>(API_URL + "/RDS-creation", {user, rdsConfig}, { headers: this.getHeader() })

  }


}
