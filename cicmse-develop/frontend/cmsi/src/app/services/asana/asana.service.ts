import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { User } from '../../model/user.model';
import { Observable } from 'rxjs';
import { HeaderService } from '../base-header.service';
import { AuthenticationService } from '../authentication.service';
import { asanaProject } from '../../model/asana/asana-project';

const API_URL = environment.ROOT_URL + "/api/v1/asana"

@Injectable({
  providedIn: 'root'
})
export class AsanaService extends HeaderService {

  constructor(http: HttpClient, authenticationService: AuthenticationService) {
    super(http, authenticationService);
  }

  setPersonalAccessTokenForUser(user: User, token: String): Observable<any> {
    console.log()
    if (token != null && token != "") {
      user.asanaPersonalAccessToken = token;
    }

    return this.http.post<User>(API_URL + "/add-access-token", user, { headers: this.getHeader() });
  }

  getAsanaUser(user: User, workspaceGid: String): Observable<any> {
    return this.http.post<any>(API_URL + "/asanaUser", {user, workspaceGid} , { headers: this.getHeader() })
  }

  getAsanaWorkspaces(user: User): Observable<any> {
    return this.http.post<any>(API_URL + "/workspaces", user, { headers: this.getHeader() })
  }

  getAsanaProjectbyWorkspace(user: User, workspaceGid: String, isImmideatly: boolean): Observable<any> {
    return this.http.post<any>(API_URL + "/projects", { user, workspaceGid, isImmideatly }, { headers: this.getHeader() })
  }

  getAsanaTasksbyProjectSection(user: User, workspaceGid: String, projectGid: String, sectionGid: String ): Observable<any> {
    return this.http.post<any>(API_URL + "/projectTasksBySection", { user, projectGid, workspaceGid, sectionGid }, { headers: this.getHeader() })
  }

  getAsanaSectionsByProject(user: User, workspaceGid: String, projectGid: String): Observable<any> {
    return this.http.post<any>(API_URL + "/projectSection", { user, projectGid, workspaceGid}, { headers: this.getHeader() })
  }


  createAsanaProjectbyWorkspace(user: User, workspaceGid: String, asanaProject: asanaProject): Observable<any> {
    return this.http.post<any>(API_URL + "/createProject", { user, workspaceGid, asanaProject }, { headers: this.getHeader() })
  }

  createAsanaDefaultSectionbyProject(user: User, projectGid: String): Observable<any> {
    return this.http.post<any>(API_URL + "/createSection", { user, projectGid }, { headers: this.getHeader() })
  }

  getAsanaTask(user: User, sectionGid: String, taskGid: String) {
    return this.http.post<any>(API_URL + "/task", {user, sectionGid, taskGid}, { headers: this.getHeader() })
  }

  addAsanaTaskToSection(user: User, sectionGid: String, taskGid: String): Observable<any> {
    return this.http.post<any>(API_URL + "/addTaskToSection", { user, sectionGid, taskGid }, { headers: this.getHeader() })
  }

}

