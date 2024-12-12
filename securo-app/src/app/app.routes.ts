import { Routes } from '@angular/router';
import { TokenGuard } from './core/auth/guard/token.guard';

export const routes: Routes = [
  {
    path: 'home',
    loadComponent: () => import('./modules/home/home.component').then(m => m.HomeComponent),
    canActivate: [TokenGuard]
  },
  {
    path: 'users',
    loadComponent: () => import('./modules/users/users.component').then(m => m.UsersComponent),
    canActivate: [TokenGuard]
  },
  {
    path: 'systems',
    loadComponent: () => import('./modules/systems/systems.component').then(m => m.SystemsComponent),
    canActivate: [TokenGuard]
  },
  {
    path: 'profile',
    loadComponent: () => import('./modules/profile/profile.component').then(m => m.ProfileComponent),
    canActivate: [TokenGuard]
  },
  {
    path: 'login',
    loadComponent: () => import('./core/login/login.component').then(m => m.LoginComponent),
  },
  {
    path: '**',
    redirectTo: 'home',
  },
];
