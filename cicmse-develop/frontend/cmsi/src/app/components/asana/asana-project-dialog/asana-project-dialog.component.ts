import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { asanaProject } from 'src/app/model/asana/asana-project';
import { User } from 'src/app/model/user.model';
import { AsanaComponent } from 'src/app/pages/asana/asana-home/asana.component';
import { AsanaService } from 'src/app/services/asana/asana.service';

@Component({
  selector: 'app-asana-project-dialog',
  templateUrl: './asana-project-dialog.component.html',
  styleUrls: ['./asana-project-dialog.component.scss']
})
export class AsanaProjectDialogComponent implements OnInit {
  creationAsanaProject: FormGroup;
  asanaProject: asanaProject = new asanaProject();

  constructor(
    private asanaService: AsanaService,
    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<AsanaComponent>,
    @Inject(MAT_DIALOG_DATA) public data:{project: asanaProject, gid: String, user: User, asanaUsers: Array<any>}
    ) {

    this.creationAsanaProject = formBuilder.group({
      name: ['', Validators.maxLength(150)],
      current_status: '',
      color: '',
      due_on: '',
      notes: '',
      owner: '',
    })
   }

  ngOnInit(): void {

  }

  createAsanaProject() {
    this.data.project.name = this.creationAsanaProject.value.name;
    this.data.project.owner = this.creationAsanaProject.value.owner;
    this.data.project.currentStatus = this.creationAsanaProject.value.current_status;
    this.data.project.color = this.creationAsanaProject.value.color;
    this.data.project.notes = this.creationAsanaProject.value.notes;
    let dueOn: Date =  this.creationAsanaProject.value.due_on;
    this.data.project.dueOn = this.dateFormatter(dueOn.getFullYear(), dueOn.getMonth() + 1, dueOn.getDate());

    this.asanaService.createAsanaProjectbyWorkspace(this.data.user, this.data.gid, this.data.project).subscribe(
      {
        next: (value) => {
            this.asanaProject = value;
        },
        error: (err) => {
          console.log(err)
        },
        complete: () => {
          this.dialogRef.close(this.asanaProject);
        }
      }
    )
  }

  dateFormatter(year: number, month: number, day: number): String {
    return "" + year + "-" + month + "-" + day;
  }


}
