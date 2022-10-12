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

@Component({
  selector: 'app-asana',
  templateUrl: './asana.component.html',
  styleUrls: ['./asana.component.scss']
})
export class AsanaComponent implements OnInit, OnDestroy {
  user: User = new User();
  addingToken: FormGroup;
  workspaces: Array<any> = [];
  projects: Array<any> = [];
  isLoading = false;
  subscriptionWorkspaces: Subscription | undefined;
  subscriptionUser: Subscription | undefined;

  constructor(
    private formBuilder: FormBuilder,
    private asanaProjecCreationDialog: MatDialog,
    private router: Router,
    private authenticationService: AuthenticationService,
    private asanaService: AsanaService) {

    this.addingToken = formBuilder.group({
      accessToken: ['', Validators.minLength(50)]
    })
  }
  ngOnDestroy(): void {
    this.subscriptionWorkspaces?.unsubscribe();
    this.subscriptionUser?.unsubscribe();
  }

  ngOnInit() {
    this.subscriptionUser = this.authenticationService.currentUser.subscribe((data: User) => {
      this.user = data;
    });

    this.subscriptionWorkspaces = this.asanaService.getAsanaWorkspaces(this.user).subscribe({
      next: (asanaWorkspace: any) => {
        switch (asanaWorkspace.toString()) {
          case "Bad asana personal access token": {
            this.user.asanaPersonalAccessToken = null;
            break;
          }
          case "Not exist any Asana account for this user: " + this.user.name: {
            this.user.asanaPersonalAccessToken = null;
            break;
          }
          default: {
            this.workspaces = asanaWorkspace;
            break;
          }
        }
      },
      error: (err: string) => {
        console.error(err);
        this.isLoading = true;
      },
      complete: () => this.isLoading = true,
    });

  }

  addPersonalAccessToken() {
    this.asanaService.setPersonalAccessTokenForUser(this.user, this.addingToken.value.accessToken)
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

  loadAsanaProjects(gid: any) {
    this.asanaService.getAsanaProjectbyWorkspace(this.user, gid).subscribe(p => {
      this.projects = p;
    })
  }

  openAsanaProjectCreationDialog() {
    const dialogRef = this.asanaProjecCreationDialog.open(AsanaProjectDialogComponent, {
      width: "800px",
      height: "450px"
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed' + result);
      //this.projects.push(result);
    });
  }

}
