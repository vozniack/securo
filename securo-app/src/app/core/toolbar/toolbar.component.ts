import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';
import { IconButtonComponent } from '../../shared/components/buttons/icon-button/icon-button.component';
import { NavBarComponent } from '../../shared/components/collection/nav-bar/nav-bar.component';
import { ACTION_USER_LOGOUT } from '../../store/app/app.actions';
import { NavLink } from '../navigation/navigation.interface';
import { navigationLinks } from '../navigation/navigation.const';
import { ThemeService } from '../theme/theme.service';

@Component({
  selector: 'sec-toolbar',
  standalone: true,
  imports: [
    NavBarComponent,
    IconButtonComponent
  ],
  templateUrl: './toolbar.component.html',
  styleUrl: './toolbar.component.scss',
  animations: [fadeInOutAnimation]
})
export class ToolbarComponent {

  navigationLinks: NavLink[] = navigationLinks;

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
