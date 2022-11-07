import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AsanaComponent } from './asana.component';

const routes: Routes = [
  {
    path: '',
    component: AsanaComponent
   },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]

})
export class AsanaRoutes {}
