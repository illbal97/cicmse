import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home.component';


const routes: Routes = [
  {
    path:'',
    component: HomeComponent, children: [
      {
        path:'asana',
        loadChildren: () => import("../asana/asana-home/asana-home.module").then(m => m.AsanaModule)
      },
      {
        path:'asanaTask',
        loadChildren: () => import("../asana/asana-task/asana-task.module").then(m => m.AsanaTaskModule)
      },
      {
        path:'aws',
        loadChildren: () => import("../aws/aws-home/aws-home.module").then(m => m.AwsHomeModule)
      },
      {
        path:'gitlab',
        loadChildren: () => import("../gitlab/gitlab-home/gitlab-home.module").then(m => m.GitlabHomeModule)
      },
    ]
  }
];
@NgModule( {
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class HomeRoutingModule{}
