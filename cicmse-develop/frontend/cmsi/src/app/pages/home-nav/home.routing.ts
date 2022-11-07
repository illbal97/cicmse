import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home.component';


const routes: Routes = [
  {
    path:'',
    component: HomeComponent, children: [
      {
        path:'asana',
        loadChildren: () => import("../asana/asana-home/asana.module").then(m => m.AsanaModule)
      },
      {
        path:'asanaTask',
        loadChildren: () => import("../asana/asana-task/asana-task.module").then(m => m.AsanaTaskModule)
      }
    ]
  }
];
@NgModule( {
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class HomeRoutes{}
