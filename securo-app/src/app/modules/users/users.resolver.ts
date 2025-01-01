import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, MaybeAsync, Resolve } from '@angular/router';
import { User } from './users.interface';
import { UsersService } from './users.service';

@Injectable({
  providedIn: 'root'
})
export class UsersResolver implements Resolve<User> {

  constructor(private usersService: UsersService) {
  }

  resolve(route: ActivatedRouteSnapshot): MaybeAsync<User> {
    return this.usersService.getUser(route.params['id']);
  }
}
