import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { User } from '../../model/user.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AsanaService } from '../../services/asana.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/internal/Subscription';
import { timeout } from 'rxjs';
import { data } from 'jquery';
import { ArrayDataSource } from '@angular/cdk/collections';
import { MatDialog } from '@angular/material/dialog';
import { AsanaProjectDialogComponent } from 'src/app/components/asana-project-dialog/asana-project-dialog.component';
import { asanaProject } from 'src/app/model/asana-project';

@Component({
  selector: 'app-asana',
  templateUrl: './asana.component.html',
  styleUrls: ['./asana.component.scss']
})
export class AsanaComponent implements OnInit, OnDestroy {
  user: User = new User();
  project: asanaProject | undefined;
  workspaces: Array<any> = [];
  projects: Array<any> = [];
  isLoading: boolean = false;
  subscriptionWorkspaces: Subscription | undefined;
  subscriptionUser: Subscription | undefined;
  asanaStatus: String = "";
  selectedWorkspaceGid: String = "";

  constructor(
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
    this.isLoading = false;
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
        console.error(err);
        this.isLoading = true;
      },
      complete: () => {this.isLoading = true}
    });

  }

  addPersonalAccessToken(token: String) {
    this.asanaService.setPersonalAccessTokenForUser(this.user, token)
      .subscribe({
        next: (data: User) => {
          this.authenticationService.setCurrentUser(data);
        },
        error: (err: String) => {
          console.log(err);
        },
        complete: () => {
          this.isLoading = false;
          this.ngOnInit();

        }
      });
  }

  loadAsanaProjects(gid: any, isImmideatly = false) {
    this.asanaService.getAsanaProjectbyWorkspace(this.user, gid, isImmideatly).subscribe(p => {
      this.projects = p;
    })
  }

  openAsanaProjectCreationDialog() {
    if(this.selectedWorkspaceGid !== "") {
      const dialogRef = this.asanaProjecCreationDialog.open(AsanaProjectDialogComponent, {
        width: "800px",
        height: "450px",
        data: {project: this.project, gid: this.selectedWorkspaceGid, user: this.user}
      });

      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed' + result);
        if (result != undefined) {
          this.loadAsanaProjects(this.selectedWorkspaceGid, true)
        }
      });
    }
  }
}
