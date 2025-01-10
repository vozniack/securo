import { BreakpointObserver } from '@angular/cdk/layout';
import { Location, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { TranslatePipe } from '@ngx-translate/core';
import { tap } from 'rxjs/operators';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';
import { ResponsiveComponent } from '../../shared/common/responsive.component';
import { IconButtonComponent } from '../../shared/components/buttons/icon-button/icon-button.component';
import { User } from '../users/users.interface';
import { UsersService } from '../users/users.service';
import { SettingsLanguageComponent } from './settings-language/settings-language.component';
import { SettingsPasswordComponent } from './settings-password/settings-password.component';
import { SettingsProfileComponent } from './settings-profile/settings-profile.component';
import { SettingsThemeComponent } from './settings-theme/settings-theme.component';

@Component({
  selector: 'sec-settings',
  standalone: true,
  imports: [SettingsThemeComponent, SettingsProfileComponent, TranslatePipe, SettingsLanguageComponent, IconButtonComponent, SettingsPasswordComponent, NgIf],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss',
  animations: [fadeInOutAnimation]
})
export class SettingsComponent extends ResponsiveComponent {

  user!: User;

  constructor(private userService: UsersService, private location: Location, override breakpointObserver: BreakpointObserver) {
    super(breakpointObserver);

    this.userService.getLoggedUser().pipe(
      takeUntilDestroyed(),
      tap((response: User) => this.user = response)
    ).subscribe();
  }

  routeBack(): void {
    this.location.back();
  }
}
