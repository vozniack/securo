import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, Input } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { TranslatePipe } from '@ngx-translate/core';
import { ResponsiveComponent } from '../../../../shared/common/responsive.component';
import { IconComponent } from '../../../../shared/components/common/icon/icon.component';
import { NavigationLink } from '../../navigation.interface';

@Component({
  selector: 'sec-navigation-button',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, IconComponent, TranslatePipe],
  templateUrl: './navigation-button.component.html',
  styleUrl: './navigation-button.component.scss'
})
export class NavigationButtonComponent extends ResponsiveComponent {

  @Input() navigationLink!: NavigationLink;

  constructor(override breakpointObserver: BreakpointObserver) {
    super(breakpointObserver);
  }
}
