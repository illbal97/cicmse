import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AsanaHomeComponent } from './asana-home.component';

const routes: Routes = [
  {
    path: '',
    component: AsanaHomeComponent
   },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]

})
export class AsanaHomeRoutingModule {}
