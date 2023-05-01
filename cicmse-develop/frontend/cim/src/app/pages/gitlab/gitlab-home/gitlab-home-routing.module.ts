import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GitlabHomeComponent } from './gitlab-home.component';

const routes: Routes = [
  {
    path: '',
    component: GitlabHomeComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GitlabHomeRoutingModule { }
