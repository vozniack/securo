import { BreakpointObserver } from '@angular/cdk/layout';
import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { TranslatePipe } from '@ngx-translate/core';
import { tap } from 'rxjs/operators';
import { fadeInAnimation } from '../../shared/animations/fade-in-animation';
import { ResponsiveComponent } from '../../shared/common/responsive.component';
import { SegmentedButtonComponent } from '../../shared/components/buttons/segmented-button/segmented-button.component';
import { SegmentedButton } from '../../shared/components/buttons/segmented-button/segmented-button.interface';
import { User } from '../../domain/entity/user.interface';
import { UserService } from '../../domain/services/user.service';
import { SettingsAdminComponent } from './settings-admin/settings-admin.component';
import { SettingsUserComponent } from './settings-user/settings-user.component';
import { settingsSegments } from './settings.const';

@Component({
  selector: 'sec-settings',
  standalone: true,
  imports: [TranslatePipe, NgIf, SettingsUserComponent, SegmentedButtonComponent, SettingsAdminComponent],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss',
  animations: [fadeInAnimation]
})
export class SettingsComponent extends ResponsiveComponent {

  user!: User;

  settingsSegments = settingsSegments;
  activeSegment = settingsSegments[1];

  constructor(private userService: UserService, override breakpointObserver: BreakpointObserver) {
    super(breakpointObserver);

    this.userService.getLoggedUser().pipe(
      takeUntilDestroyed(),
      tap((response: User) => this.user = response)
    ).subscribe();
  }

  onSettingsChange(activeSegment: SegmentedButton) {
    this.activeSegment = activeSegment;
  }
}
