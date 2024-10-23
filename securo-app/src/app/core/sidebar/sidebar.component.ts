import { Component } from '@angular/core';
import { NavigationLink } from '../navigation/navigation.interface';
import { navigationLinks } from '../navigation/navigation.const';

@Component({
  selector: 'sec-sidebar',
  standalone: true,
  imports: [],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {

  navigationLinks: NavigationLink[] = navigationLinks;
}
