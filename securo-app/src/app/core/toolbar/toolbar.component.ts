import { NgForOf, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';
import { IconButtonComponent } from '../../shared/components/buttons/icon-button/icon-button.component';
import { NavBarComponent } from '../../shared/components/collection/nav-bar/nav-bar.component';
import { IconComponent } from '../../shared/components/common/icon/icon.component';
import { NavLink } from '../navigation/navigation.interface';
import { navigationLinks } from '../navigation/navigation.const';

@Component({
  selector: 'sec-toolbar',
  standalone: true,
  imports: [
    NgForOf,
    RouterLink,
    RouterLinkActive,
    IconComponent,
    NavBarComponent,
    IconButtonComponent,
    NgIf
  ],
  templateUrl: './toolbar.component.html',
  styleUrl: './toolbar.component.scss',
  animations: [fadeInOutAnimation]
})
export class ToolbarComponent {

  navigationLinks: NavLink[] = navigationLinks;

  darkMode: Boolean = false;

  switchTheme(): void {
    this.darkMode = !this.darkMode;
  }
}
