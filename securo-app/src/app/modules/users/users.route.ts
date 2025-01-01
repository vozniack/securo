import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserComponent } from './user/user.component';
import { UsersComponent } from './users.component';
import { UsersResolver } from './users.resolver';

const routes: Routes = [
  {
    path: '',
    component: UsersComponent
  },
  {
    path: ':id',
    component: UserComponent,
    resolve: {
      user: UsersResolver
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoute {
}
