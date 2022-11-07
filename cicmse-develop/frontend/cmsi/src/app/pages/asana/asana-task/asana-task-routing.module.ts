import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AsanaTaskComponent } from './asana-task.component';

const routes: Routes = [ {
   path:'',
  component: AsanaTaskComponent,
}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AsanaTaskRoutingModule { }
