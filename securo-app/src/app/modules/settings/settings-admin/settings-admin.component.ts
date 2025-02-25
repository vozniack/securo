import { BreakpointObserver } from '@angular/cdk/layout';
import { Component } from '@angular/core';
import { ResponsiveComponent } from '../../../shared/common/responsive.component';
import { SettingsEnvironmentsComponent } from './settings-environments/settings-environments.component';

@Component({
  selector: 'sec-settings-admin',
  standalone: true,
  imports: [SettingsEnvironmentsComponent],
  templateUrl: './settings-admin.component.html',
  styleUrl: './settings-admin.component.scss'
})
export class SettingsAdminComponent extends ResponsiveComponent {

  constructor(override breakpointObserver: BreakpointObserver) {
    super(breakpointObserver);
  }
}
