import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GitlabBranchCreationData } from 'src/app/model/gitlab/gitlab-branch-creation';
import { GitlabCreationData } from 'src/app/model/gitlab/gitlab-creation-data';
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

   getGitlabProject(user: User, isImmediately: boolean): Observable<any> {
    let userId = user.id?.toString()
    return this.http.post<any>(API_URL + "/projects", {userId, isImmediately}, { headers: this.getHeader(), withCredentials:true })
  }

  createProject(user: User, data: GitlabCreationData) {
    let userId = user.id?.toString();
    return this.http.post<any>(API_URL + "/project-creation", {userId, data}, { headers: this.getHeader(), withCredentials:true })
  }

  createBranch(user: User, project_id: number, data_branch: GitlabBranchCreationData) {
    let userId = user.id?.toString();
    return this.http.post<any>(API_URL + "/branch-creation", {userId, project_id, data_branch}, { headers: this.getHeader(), withCredentials:true })
  }

  setPersonalAccessTokenForUser(user: User, token: String): Observable<any> {
    if (token != null && token != "") {
      user.gitlabPersonalAccessToken = token;
    }

    return this.http.post<User>(API_URL + "/add-access-token", user, { headers: this.getHeader(), withCredentials:true });
  }
}
