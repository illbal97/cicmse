import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { lastValueFrom, Subscription } from 'rxjs';
import { User } from 'src/app/model/user.model';
import { AsanaService } from 'src/app/services/asana.service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-asana-task',
  templateUrl: './asana-task.component.html',
  styleUrls: ['./asana-task.component.scss']
})
export class AsanaTaskComponent implements OnInit, OnDestroy {

  workspaceGid: String = "";
  projectGid: String = "";
  projectName: String = "";
  private asanaTasks: Array<any> = []
  private asanaSectionByTasks: Map<String, any[]> | undefined
  asanaSection: Array<any> = [];
  user: User = new User();
  private userSubscription: Subscription | undefined;
  private routeSubscription: Subscription | undefined;

  constructor(private asanaTaskDialog: MatDialog, private route: ActivatedRoute, private asanaService: AsanaService, private authenticationService: AuthenticationService) { }

  async ngOnInit(): Promise<void> {
    this.routeSubscription = this.route.queryParams.subscribe( param => {
      this.workspaceGid = param['workspaceGid'];
      this.projectGid = param['projectGid'];
      this.projectName = param['projectName'];
      console.log(this.projectName)
    });
    console.log(this.workspaceGid + "  " + this.projectGid)
    this.userSubscription = this.authenticationService.currentUser.subscribe((data: User) => {
      this.user = data;
    });
    await this.createDefaultSection();
    this.loadAsanaSection();

  }

  ngOnDestroy(): void {
    this.userSubscription?.unsubscribe();
    this.routeSubscription?.unsubscribe();
  }

 async loadAsanaSection() {
    await lastValueFrom(this.asanaService.getAsanaSectionsByProject(this.user, this.workspaceGid, this.projectGid)).then(t => {
      this.asanaSection = t;
      console.log(this.asanaSection)
    }).catch(err => {
      console.log(err)
    });

  }

  async createDefaultSection() {
    await lastValueFrom(this.asanaService.createAsanaDefaultSectionbyProject(this.user, this.projectGid)).then(t => {
      console.log(t)
    }).catch(err => {
      console.log(err)
    });

  }

  async loadAsanaTasksFromSection(projectSectionGid: String) {
    await lastValueFrom(this.asanaService.getAsanaTasksbyProjectSection(this.user, this.workspaceGid, this.projectGid, projectSectionGid)).then(t => {
      this.asanaTasks.push(t);
    }).catch(err => {
      console.log(err)
    });

  }


}
