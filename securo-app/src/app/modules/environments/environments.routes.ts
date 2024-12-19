import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EnvironmentsComponent } from './environments.component';

const routes: Routes = [
  {
    path: '',
    component: EnvironmentsComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EnvironmentsRoutes {
}
