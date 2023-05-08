import { Subscription } from 'rxjs/internal/Subscription';
import { MatDialog } from '@angular/material/dialog';
import { AsanaProjectDialogComponent } from 'src/app/components/asana/asana-project-dialog/asana-project-dialog.component';
import { asanaProject } from 'src/app/model/asana/asana-project';
import { lastValueFrom } from 'rxjs';
import { User } from 'src/app/model/user.model';
import { AsanaService } from 'src/app/services/asana/asana.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { GitlabService } from 'src/app/services/gitlab/gitlab.service';
import { AwsService } from 'src/app/services/aws/aws.service';

@Component({
  selector: 'app-asana',
  templateUrl: './asana-home.component.html',
  styleUrls: ['./asana-home.component.scss']
})
export class AsanaHomeComponent implements OnInit, OnDestroy {
  user: User = new User();
  project: asanaProject | undefined;
  workspaces: Array<any> = [];
  projects: Array<any> = [];
  isLoaded: boolean = false;
  subscriptionWorkspaces: Subscription | undefined;
  subscriptionUser: Subscription | undefined;
  asanaStatus: String = "";
  selectedWorkspaceGid: String = "";
  asanaUser: Array<any> = [];

  constructor(
    private router:Router,
    private asanaProjecCreationDialog: MatDialog,
    private authenticationService: AuthenticationService,
    private asanaService: AsanaService) {
      this.project = new asanaProject();


  }
  ngOnDestroy(): void {
    this.subscriptionWorkspaces?.unsubscribe();
    this.subscriptionUser?.unsubscribe();
    this.asanaProjecCreationDialog.closeAll();
  }

  ngOnInit() {
    this.isLoaded = false;
    this.subscriptionUser = this.authenticationService.currentUser.subscribe((data: User) => {
      this.user = data;
    });

    this.subscriptionWorkspaces = this.asanaService.getAsanaWorkspaces(this.user).subscribe({
      next: (asanaWorkspace: any) => {
        switch (asanaWorkspace.toString()) {
          case "Bad asana personal access token": {
            this.asanaStatus = "Bad asana personal access token"
            break;
          }
          case "Connection timeout": {
            this.asanaStatus = "Connection timeout"
            break;
          }
          case "AsanaPersonalAccessToken is null": {
            this.asanaStatus = "AsanaPersonalAccessToken is null";
            break;
          }
          default: {
            this.asanaStatus = "Asana account is active";
            this.workspaces = asanaWorkspace;
            break;
          }
        }
      },
      error: (err: string) => {
        //console.error(err);
        this.isLoaded = true;
      },
      complete: () => { this.isLoaded = true }
    });

  }

  async addPersonalAccessToken(token: String) {
     await lastValueFrom( this.asanaService.setPersonalAccessTokenForUser(this.user, token)).then( data => {
        this.authenticationService.setCurrentUser(data);

      })
      .catch(error => {
        console.log(error);
      })

      this.isLoaded = false;
      this.ngOnInit();
  }

  loadAsanaProjects(gid: any, isImmediately = false) {
    this.asanaService.getAsanaProjectbyWorkspace(this.user, gid, isImmediately).subscribe(p => {
      this.projects = p;
    })

  }

  loadAsanaTasksForProject(projectGid: String, projectName: String) {
    if (this.selectedWorkspaceGid !== "") {
      this.router.navigate(['/home/asanaTask'], {queryParams:{workspaceGid: this.selectedWorkspaceGid, projectGid: projectGid, projectName: projectName}});
    }
  }

  async openAsanaProjectCreationDialog() {
    if (this.selectedWorkspaceGid !== "") {
     await lastValueFrom(this.asanaService.getAsanaUser(this.user, this.selectedWorkspaceGid)).then(users => {
        this.asanaUser = users;
      }).catch( errr => {
        //console.log(errr);
      });
      const dialogRef = this.asanaProjecCreationDialog.open(AsanaProjectDialogComponent, {
        width: "800px",
        height: "450px",
        data: { project: this.project, gid: this.selectedWorkspaceGid, user: this.user, asanaUsers: this.asanaUser }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result != undefined) {
          this.loadAsanaProjects(this.selectedWorkspaceGid, true)
        }
      });
    }
  }
}
