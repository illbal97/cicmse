import { keyframes } from '@angular/animations';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-aws-access-keys-adding',
  templateUrl: './aws-access-keys-adding.component.html',
  styleUrls: ['./aws-access-keys-adding.component.scss']
})
export class AwsAccessKeysAddingComponent implements OnInit {


  ngOnInit(): void {
  }
  addingAccessKeys: FormGroup;
  @Output() addingAwsAccessKey = new EventEmitter<String[]>();
  @Output() addingAwsSecretAccessKey = new EventEmitter<String>();


  constructor(formBuilder: FormBuilder) {
    this.addingAccessKeys = formBuilder.group({
      accessKey: [''],
      accessSecretKey: ['']
    })
  }

  addPersonalAccessToken() {
    let keys: Array<String> = [];
    keys.push(this.addingAccessKeys.value.accessKey, this.addingAccessKeys.value.accessSecretKey)
    this.addingAwsAccessKey.emit()

  }

}
