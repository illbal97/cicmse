import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-adding-asana-personal-access-token',
  templateUrl: './adding-asana-personal-access-token.component.html',
  styleUrls: ['./adding-asana-personal-access-token.component.scss']
})
export class AddingAsanaPersonalAccessTokenComponent {
  addingToken: FormGroup;
  @Output() addingAsanaPersonalAccessTokenEvent = new EventEmitter<String>();

  constructor(formBuilder: FormBuilder) {
    this.addingToken = formBuilder.group({
      accessToken: ['', Validators.minLength(50)]
    })
  }

  addPersonalAccessToken() {
    this.addingAsanaPersonalAccessTokenEvent.emit(this.addingToken.value.accessToken)
  }

}
