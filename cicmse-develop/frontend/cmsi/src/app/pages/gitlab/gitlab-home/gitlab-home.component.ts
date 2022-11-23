import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { lastValueFrom, Subscription } from 'rxjs';
import { ProjectCreationDialogComponent } from 'src/app/components/gitlab/project-creation-dialog/project-creation-dialog.component';
import { User } from 'src/app/model/user.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { GitlabService } from 'src/app/services/gitlab/gitlab.service';

@Component({
  selector: 'app-gitlab-home',
  templateUrl: './gitlab-home.component.html',
  styleUrls: ['./gitlab-home.component.scss']
})
export class GitlabHomeComponent implements OnInit, OnDestroy {

  isLoading: boolean = false;
  gitlabStatus: String = "";
  projects: Array<any> = [];
  subscriptionUser: Subscription | undefined;
  user: User = new User();
  subscriptionGitlabProjects: Subscription | undefined


  constructor(private authenticationService: AuthenticationService, private gitlabService: GitlabService,  private gitlabProjectCreationDialog: MatDialog) { }
  ngOnDestroy(): void {
    this.subscriptionGitlabProjects?.unsubscribe;
    this.subscriptionUser?.unsubscribe;
    this.gitlabProjectCreationDialog.closeAll();
  }

  ngOnInit(): void {
    this.isLoading = false;
    this.subscriptionUser = this.authenticationService.currentUser.subscribe((data: User) => {
      this.user = data;
    });

    this.loadGitlabProjects();

  }

  openGitlabProjectCreationDialog() {

    const dialogRef = this.gitlabProjectCreationDialog.open(ProjectCreationDialogComponent, {
      width: "800px",
      height: "800px",
      panelClass: "custom-modalbox",
      data: {user: this.user, gitlab_projects: this.projects}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed' + result);
      if (result != undefined) {
        console.log(result)
        this.ngOnInit();
        }
    });
  }

  loadGitlabProjects(isImmediate = false) {
    this.subscriptionGitlabProjects = this.gitlabService.getGitlabProject(this.user, isImmediate).subscribe({
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


}
