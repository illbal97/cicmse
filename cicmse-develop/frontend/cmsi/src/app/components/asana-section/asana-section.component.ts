import { Component, Input, OnInit } from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import { lastValueFrom } from 'rxjs';
import { User } from 'src/app/model/user.model';
import { AsanaService } from 'src/app/services/asana.service';

@Component({
  selector: 'app-asana-section',
  templateUrl: './asana-section.component.html',
  styleUrls: ['./asana-section.component.scss']
})
export class AsanaSectionComponent implements OnInit {

  @Input("asanaSection") section: any | undefined;
  @Input("user") user: User = new User();
  @Input("workspaceGid") workspaceGid: String = '';
  @Input("projectGid") projectGid: String = '';

  asanaTask: Array<any> = [];
  constructor(private asanaService: AsanaService) { }

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
    this.asanaService.addAsanaTaskToSection(this.user, this.section.gid, event.item.data.gid).subscribe(x => {
      //console.log(x);
    })
    }
  }

}
