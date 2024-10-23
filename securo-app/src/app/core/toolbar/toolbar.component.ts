import { NgForOf } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { NavigationLink } from '../navigation/navigation.interface';
import { navigationLinks } from '../navigation/navigation.const';

@Component({
  selector: 'sec-toolbar',
  standalone: true,
  imports: [
    NgForOf,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './toolbar.component.html',
  styleUrl: './toolbar.component.scss'
})
export class ToolbarComponent {

  navigationLinks: NavigationLink[] = navigationLinks;
}
