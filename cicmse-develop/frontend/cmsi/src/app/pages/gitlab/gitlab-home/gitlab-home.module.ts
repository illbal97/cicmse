import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GitlabHomeRoutingModule } from './gitlab-home-routing.module';
import { GitlabHomeComponent } from './gitlab-home.component';
import { MaterialModule } from 'src/app/moduls/material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { AddingGitlabPersonalAccessTokenComponent } from 'src/app/components/gitlab/adding-gitlab-personal-access-token/adding-gitlab-personal-access-token.component';
import { ProjectCreationDialogComponent } from 'src/app/components/gitlab/project-creation-dialog/project-creation-dialog.component';


@NgModule({
  declarations: [
    GitlabHomeComponent,
    AddingGitlabPersonalAccessTokenComponent,
    ProjectCreationDialogComponent

  ],
  imports: [
    CommonModule,
    GitlabHomeRoutingModule,
    MaterialModule,
    ReactiveFormsModule
  ]
})
export class GitlabHomeModule { }
