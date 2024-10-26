import { NgForOf, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
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
  styleUrl: './toolbar.component.scss'
})
export class ToolbarComponent {

  navigationLinks: NavLink[] = navigationLinks;

  search: Boolean = false;
  darkMode: Boolean = false;

  switchSearch(): void {
    this.search = !this.search;
  }

  switchTheme(): void {
    this.darkMode = !this.darkMode;
  }
}
