import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { WelcomeComponent } from './welcome.component';

const routes: Routes = [
  {
    path:'',
    component: WelcomeComponent, children: [
      {
        path: 'home/asana',
        loadChildren: () => import('../asana/asana-home/asana-home.module').then(m => m.AsanaModule)
      },
      {
        path: 'home/aws',
        loadChildren: () => import('../aws/aws-home/aws-home.module').then(m => m.AwsHomeModule)
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
