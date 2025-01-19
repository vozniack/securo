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
    loadChildren: () => import('./modules/users/users.route').then(m => m.UsersRoute),
    canActivate: [TokenGuard]
  },
  {
    path: 'teams',
    loadChildren: () => import('./modules/teams/teams.route').then(m => m.TeamsRoute),
    canActivate: [TokenGuard]
  },
  {
    path: 'systems',
    loadChildren: () => import('./modules/systems/systems.routes').then(m => m.SystemsRoutes),
    canActivate: [TokenGuard]
  },
  {
    path: 'settings',
    loadComponent: () => import('./modules/settings/settings.component').then(m => m.SettingsComponent),
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
