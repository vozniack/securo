import { BreakpointObserver } from '@angular/cdk/layout';
import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Store } from '@ngrx/store';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';
import { ResponsiveComponent } from '../../shared/common/responsive.component';
import { IconButtonComponent } from '../../shared/components/buttons/icon-button/icon-button.component';
import { ACTION_USER_LOGOUT } from '../../store/app/app.actions';
import { NavigationBarComponent } from '../navigation/navigation-bar/navigation-bar.component';

@Component({
  selector: 'sec-top-bar',
  standalone: true,
  imports: [NavigationBarComponent, IconButtonComponent, NgIf, RouterLink],
  templateUrl: './top-bar.component.html',
  styleUrl: './top-bar.component.scss',
  animations: [fadeInOutAnimation]
})
export class TopBar extends ResponsiveComponent {

  constructor(private store: Store, private router: Router, override breakpointObserver: BreakpointObserver) {
    super(breakpointObserver);
  }

  logout(): void {
    this.store.dispatch(ACTION_USER_LOGOUT());
    this.router.navigate(['login']).then();
  }
}
