import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/model/user.model';
import { environment } from 'src/environments/environment';
import { AuthenticationService } from '../authentication.service';
import { HeaderService } from '../base-header.service';

const API_URL = environment.ROOT_URL + "/api/v1/gitlab"

@Injectable({
  providedIn: 'root'
})
export class GitlabService extends HeaderService {

  constructor(http: HttpClient, authenticationService: AuthenticationService) {
    super(http, authenticationService);
   }

   getGitlabProject(user: User): Observable<any> {
    let userId = user.id?.toString()
    return this.http.post<any>(API_URL + "/projects", {userId}, { headers: this.getHeader() })
  }

  setPersonalAccessTokenForUser(user: User, token: String): Observable<any> {
    if (token != null && token != "") {
      user.gitlabPersonalAccessToken = token;
    }

    return this.http.post<User>(API_URL + "/add-access-token", user, { headers: this.getHeader() });
  }
}
