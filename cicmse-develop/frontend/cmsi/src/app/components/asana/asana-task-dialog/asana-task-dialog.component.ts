import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AsanaTaskComponent } from 'src/app/pages/asana/asana-task/asana-task.component';

@Component({
  selector: 'app-asana-task',
  templateUrl: './asana-task-dialog.component.html',
  styleUrls: ['./asana-task-dialog.component.scss']
})
export class AsanaTaskDialogComponent implements OnInit {

  public dialogRef: MatDialogRef<AsanaTaskComponent> | undefined

  constructor(private asanaTaskDialog: MatDialog, @Inject(MAT_DIALOG_DATA) public data:{task: any}) { }

  ngOnInit(): void {
  }




}
