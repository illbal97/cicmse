import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import { lastValueFrom } from 'rxjs';
import { User } from 'src/app/model/user.model';
import { AsanaService } from 'src/app/services/asana/asana.service';
import { MatDialog } from '@angular/material/dialog';
import { AsanaTaskDialogComponent } from '../asana-task-dialog/asana-task-dialog.component';

@Component({
  selector: 'app-asana-section',
  templateUrl: './asana-section.component.html',
  styleUrls: ['./asana-section.component.scss']
})
export class AsanaSectionComponent implements OnInit, OnDestroy {

  @Input("asanaSection") section: any | undefined;
  @Input("user") user: User = new User();
  @Input("workspaceGid") workspaceGid: String = '';
  @Input("projectGid") projectGid: String = '';

  asanaTask: Array<any> = [];
  asanaTaskDetails: any;
  constructor(private asanaService: AsanaService, private asanaTaskDialog: MatDialog) { }

  ngOnDestroy(): void {
   this.asanaTaskDialog.closeAll();
   this.asanaTask = [];
  }

  ngOnInit(): void {
    this.loadAsanaTasksFromSection();
  }

  async loadAsanaTasksFromSection() {
    await lastValueFrom(this.asanaService.getAsanaTasksbyProjectSection(this.user, this.workspaceGid, this.projectGid, this.section.gid)).then(t => {
      this.asanaTask = t;
    }).catch(err => {
      console.log(err)
    });

  }
  drop(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);


    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex,
      );
      console.log(event.previousContainer.data)
    this.asanaService.addAsanaTaskToSection(this.user, this.section.gid, event.item.data.gid).subscribe(x => {
      //console.log(x);
   })


    }
  }
  async openTaskDialog(asanaTask:any) {
   await lastValueFrom(this.asanaService.getAsanaTask(this.user, this.section.gid, asanaTask.gid )).then(t => {
      this.asanaTaskDetails = t;
      console.log(this.asanaTaskDetails)
     })
    const dialogRef = this.asanaTaskDialog.open(AsanaTaskDialogComponent, {
      width: "648px",
      height: "250px",
      data: { task: this.asanaTaskDetails }
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed' + result);

    });
  }

}
