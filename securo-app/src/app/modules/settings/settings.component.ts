import { BreakpointObserver } from '@angular/cdk/layout';
import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { TranslatePipe } from '@ngx-translate/core';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';
import { ResponsiveComponent } from '../../shared/common/responsive.component';
import { IconButtonComponent } from '../../shared/components/buttons/icon-button/icon-button.component';
import { SettingsLanguageComponent } from './settings-language/settings-language.component';
import { SettingsProfileComponent } from './settings-profile/settings-profile.component';
import { SettingsThemeComponent } from './settings-theme/settings-theme.component';

@Component({
  selector: 'sec-settings',
  standalone: true,
  imports: [SettingsThemeComponent, SettingsProfileComponent, TranslatePipe, SettingsLanguageComponent, IconButtonComponent],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss',
  animations: [fadeInOutAnimation]
})
export class SettingsComponent extends ResponsiveComponent {

  constructor(private location: Location, override breakpointObserver: BreakpointObserver) {
    super(breakpointObserver);
  }

  routeBack(): void {
    this.location.back();
  }
}
