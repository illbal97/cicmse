import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { User } from '../model/user.model';
import { Observable } from 'rxjs';
import { HeaderService } from './base-header.service';
import { AuthenticationService } from './authentication.service';

const API_URL = environment.ROOT_URL + "/api/v1/asana"

@Injectable({
  providedIn: 'root'
})
export class AsanaService extends HeaderService {

constructor(http: HttpClient,authenticationService: AuthenticationService) {
  super(http, authenticationService);
 }

 setPersonalAccessTokenForUser(user:User, token:String): Observable<any> {
   if(token != null && token != "") {
     user.asanaPersonalAccessToken = token;
   }
  return this.http.post<User>(API_URL + "/add-access-token", user,{headers:this.getHeader()});
  }

  getAsanaWorkspaces(user: User): Observable<any> {
    return this.http.post<any>(API_URL + "/workspaces", user, {headers:this.getHeader()})
  }
}

