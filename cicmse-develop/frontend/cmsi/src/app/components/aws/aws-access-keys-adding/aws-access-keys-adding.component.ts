import { keyframes } from '@angular/animations';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-aws-access-keys-adding',
  templateUrl: './aws-access-keys-adding.component.html',
  styleUrls: ['./aws-access-keys-adding.component.scss']
})
export class AwsAccessKeysAddingComponent implements OnInit {
  addingAccessKeys: FormGroup;
  @Output() addingAwsAccessKeyEvent = new EventEmitter<String[]>();
  private keys: String[] = []

  ngOnInit(): void {
  }

  constructor(formBuilder: FormBuilder) {
    this.addingAccessKeys = formBuilder.group({
      accessKey: [''],
      accessSecretKey: ['']
    })
  }

  emitAccessKeys() {
    this.keys.push(this.addingAccessKeys.value.accessKey, this.addingAccessKeys.value.accessSecretKey) ;
    this.addingAwsAccessKeyEvent.emit(this.keys);

  }

}
