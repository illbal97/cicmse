import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { WelcomeComponent } from './welcome.component';

const routes: Routes = [
  {
    path:'',
    component: WelcomeComponent, children: [
      {
        path: 'home/asana',
        loadChildren: () => import('../asana/asana-home/asana.module').then(m => m.AsanaModule)
      },

      {
        path: 'home/gitlab',
        loadChildren: () => import('../gitlab/gitlab-home/gitlab-home.module').then(m => m.GitlabHomeModule)
      }
    ],
  },


];
@NgModule({
  imports: [

    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class WelcomeRoutes{}
