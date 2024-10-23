import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'home',
    loadComponent: () => import('./modules/home/home.component').then(m => m.HomeComponent),
    // canActivate: [TokenGuard]
  },
  {
    path: 'profile',
    loadComponent: () => import('./modules/profile/profile.component').then(m => m.ProfileComponent),
    // canActivate: [TokenGuard]
  },
  {
    path: 'systems',
    loadComponent: () => import('./modules/systems/systems.component').then(m => m.SystemsComponent),
    // canActivate: [TokenGuard]
  },
  {
    path: 'environments',
    loadComponent: () => import('./modules/environments/environments.component').then(m => m.EnvironmentsComponent),
    // canActivate: [TokenGuard]
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
