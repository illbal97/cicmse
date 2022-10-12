import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { asanaProject } from 'src/app/model/asana-project';
import { AsanaComponent } from 'src/app/pages/asana/asana.component';

@Component({
  selector: 'app-asana-project-dialog',
  templateUrl: './asana-project-dialog.component.html',
  styleUrls: ['./asana-project-dialog.component.scss']
})
export class AsanaProjectDialogComponent implements OnInit {
  creationAsanaProject: FormGroup;
  constructor(
    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<AsanaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: asanaProject
    ) {

    this.creationAsanaProject = formBuilder.group({
      name: ['', Validators.maxLength(150)],
      current_status: '',
      color: '',
      due_on: '',
      notes: '',
      owner: '',
      start_on: ''
    })
   }

  ngOnInit(): void {
  }

  createAsanaProject() {
    this.data.name = this.creationAsanaProject.value.name;
    this.data.owner = this.creationAsanaProject.value.owner;
    this.data.current_status = this.creationAsanaProject.value.current_status;
    this.data.color = this.creationAsanaProject.value.color;
    this.data.notes = this.creationAsanaProject.value.notes;
    this.data.start_on = this.creationAsanaProject.value.start_on;
    this.data.due_on = this.creationAsanaProject.value.due_on;
    // service


    this.dialogRef.close();
  }


}
