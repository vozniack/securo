import { BreakpointObserver } from '@angular/cdk/layout';
import { NgIf } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ResponsiveComponent } from '../../../shared/common/responsive.component';
import { User } from '../../users/users.interface';
import { SettingsLanguageComponent } from './settings-language/settings-language.component';
import { SettingsPasswordComponent } from './settings-password/settings-password.component';
import { SettingsProfileComponent } from './settings-profile/settings-profile.component';
import { SettingsThemeComponent } from './settings-theme/settings-theme.component';

@Component({
  selector: 'sec-settings-user',
  standalone: true,
  imports: [NgIf, SettingsLanguageComponent, SettingsPasswordComponent, SettingsProfileComponent, SettingsThemeComponent],
  templateUrl: './settings-user.component.html',
  styleUrl: './settings-user.component.scss'
})
export class SettingsUserComponent extends ResponsiveComponent {

  @Input() user!: User;

  constructor(override breakpointObserver: BreakpointObserver) {
    super(breakpointObserver);
  }
}
