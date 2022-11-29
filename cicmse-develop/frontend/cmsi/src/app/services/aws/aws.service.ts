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

   getEC2Instances(user: User): Observable<any> {

    return this.http.post<any>(API_URL + "/ec2-instance", {user}, { headers: this.getHeader() })
  }


}
