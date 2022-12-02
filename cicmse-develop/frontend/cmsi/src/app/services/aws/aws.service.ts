import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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

  stopEC2Instance(user: User, instanceId: String): Observable<any> {

    return this.http.post<any>(API_URL + "/ec2-instance-stop", {user, instanceId}, { headers: this.getHeader() })
  }


}
