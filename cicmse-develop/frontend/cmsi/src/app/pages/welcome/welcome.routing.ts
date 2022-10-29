import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { WelcomeComponent } from './welcome.component';

const routes: Routes = [
  {
    path:'',
    component: WelcomeComponent,
  },

  {
    path: 'home/asana',
    loadChildren: () => import('../asana/asana.module').then(m => m.AsanaModule)
  }

];
@NgModule({
  imports: [

    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class WelcomeRoutes{}
