import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, MaybeAsync, Resolve } from '@angular/router';
import { User } from '../../domain/entity/user.interface';
import { UserService } from '../../domain/services/user.service';

@Injectable({
  providedIn: 'root'
})
export class UsersResolver implements Resolve<User> {

  constructor(private usersService: UserService) {
  }

  resolve(route: ActivatedRouteSnapshot): MaybeAsync<User> {
    return this.usersService.getUser(route.params['id']);
  }
}
