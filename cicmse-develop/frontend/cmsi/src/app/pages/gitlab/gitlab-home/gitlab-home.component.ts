import { Component, OnInit } from '@angular/core';
import { lastValueFrom, Subscription } from 'rxjs';
import { User } from 'src/app/model/user.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { GitlabService } from 'src/app/services/gitlab/gitlab.service';

@Component({
  selector: 'app-gitlab-home',
  templateUrl: './gitlab-home.component.html',
  styleUrls: ['./gitlab-home.component.scss']
})
export class GitlabHomeComponent implements OnInit {

  isLoading: boolean = false;
  gitlabStatus: String = "";
  projects: Array<any> = [];
  subscriptionUser: Subscription | undefined
  user: User = new User();
  subscriptionGitlabProjects: Subscription | undefined


  constructor(private authenticationService: AuthenticationService, private gitlabService: GitlabService) { }

  ngOnInit(): void {
    this.isLoading = false;
    this.subscriptionUser = this.authenticationService.currentUser.subscribe((data: User) => {
      this.user = data;
    });


    this.subscriptionGitlabProjects = this.gitlabService.getGitlabProject(this.user).subscribe({
      next: (asanaProject: any) => {
        switch (asanaProject.toString()) {
          case "Bad gitlab personal access token": {
            this.gitlabStatus = "Bad gitlab personal access token"
            break;
          }
          default: {
            this.gitlabStatus = "Gitlab account is active";
            this.projects = asanaProject;
            break;
          }
        }
      },
      error: (err: string) => {
        console.error(err);
        this.isLoading = true;
      },
      complete: () => { this.isLoading = true }
    });

  }

  openGitlabProjectCreationDialog() {

  }

  async addPersonalAccessToken(token: String) {
    await lastValueFrom( this.gitlabService.setPersonalAccessTokenForUser(this.user, token)).then( data => {
      this.authenticationService.setCurrentUser(data);

    })
    .catch(error => {
      console.log(error);
    })

    this.isLoading = false;
    this.ngOnInit();
  }

  loadGitlabProjects( gid: String, project: String) {

  }

}
