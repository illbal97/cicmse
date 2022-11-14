import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-adding-gitlab-personal-access-token',
  templateUrl: './adding-gitlab-personal-access-token.component.html',
  styleUrls: ['./adding-gitlab-personal-access-token.component.scss']
})
export class AddingGitlabPersonalAccessTokenComponent implements OnInit {

  addingToken: FormGroup;
  @Output() addingAsanaPersonalAccessTokenEvent = new EventEmitter<String>();

  constructor(formBuilder: FormBuilder) {
    this.addingToken = formBuilder.group({
      accessToken: ['', Validators.minLength(20)]
    })
  }

  addPersonalAccessToken() {
    this.addingAsanaPersonalAccessTokenEvent.emit(this.addingToken.value.accessToken)
  }

  ngOnInit(): void {
  }

}
