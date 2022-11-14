import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GitlabHomeRoutingModule } from './gitlab-home-routing.module';
import { GitlabHomeComponent } from './gitlab-home.component';
import { MaterialModule } from 'src/app/moduls/material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { AddingGitlabPersonalAccessTokenComponent } from 'src/app/components/gitlab/adding-gitlab-personal-access-token/adding-gitlab-personal-access-token.component';


@NgModule({
  declarations: [
    GitlabHomeComponent,
    AddingGitlabPersonalAccessTokenComponent

  ],
  imports: [
    CommonModule,
    GitlabHomeRoutingModule,
    MaterialModule,
    ReactiveFormsModule
  ]
})
export class GitlabHomeModule { }
