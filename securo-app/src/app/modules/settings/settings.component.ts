import { BreakpointObserver } from '@angular/cdk/layout';
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';
import { ResponsiveComponent } from '../../shared/common/responsive.component';
import { SettingsProfileComponent } from './settings-profile/settings-profile.component';
import { SettingsThemeComponent } from './settings-theme/settings-theme.component';

@Component({
  selector: 'sec-settings',
  standalone: true,
  imports: [CommonModule, SettingsThemeComponent, SettingsProfileComponent],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss',
  animations: [fadeInOutAnimation]
})
export class SettingsComponent extends ResponsiveComponent {

  constructor(override breakpointObserver: BreakpointObserver) {
    super(breakpointObserver);
  }
}
