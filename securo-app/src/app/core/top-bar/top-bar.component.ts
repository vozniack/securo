import { NgIf } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';
import { IconButtonComponent } from '../../shared/components/buttons/icon-button/icon-button.component';
import { ACTION_USER_LOGOUT } from '../../store/app/app.actions';
import { NavigationBarComponent } from '../navigation/navigation-bar/navigation-bar.component';
import { ThemeService } from '../theme/theme.service';

@Component({
  selector: 'sec-top-bar',
  standalone: true,
  imports: [NavigationBarComponent, IconButtonComponent, NgIf],
  templateUrl: './top-bar.component.html',
  styleUrl: './top-bar.component.scss',
  animations: [fadeInOutAnimation]
})
export class TopBar {

  @Input() mobile!: boolean;

  constructor(private store: Store, private router: Router, private themeService: ThemeService) {
  }

  switchTheme(): void {
    this.themeService.switchTheme();
  }

  logout(): void {
    this.store.dispatch(ACTION_USER_LOGOUT());
    this.router.navigate(['login']).then();
  }
}
