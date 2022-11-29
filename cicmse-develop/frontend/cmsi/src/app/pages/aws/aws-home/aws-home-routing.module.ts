import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AwsHomeComponent } from './aws-home.component';

const routes: Routes = [
  {
    path:'',
    component: AwsHomeComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AwsHomeRoutingModule { }
