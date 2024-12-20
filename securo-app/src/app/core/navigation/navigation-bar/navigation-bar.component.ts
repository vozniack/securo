import { NgForOf } from '@angular/common';
import { Component, Input } from '@angular/core';
import { NavigationLink } from '../navigation.interface';
import { NavigationButtonComponent } from './navigation-button/navigation-button.component';
import { navigationLinks } from '../navigation.const';

@Component({
  selector: 'sec-navigation-bar',
  standalone: true,
  imports: [NavigationButtonComponent, NgForOf],
  templateUrl: './navigation-bar.component.html',
  styleUrl: './navigation-bar.component.scss'
})
export class NavigationBarComponent {

  @Input() navigationLinks: NavigationLink[] = navigationLinks;

  @Input() label: Boolean = true;
}
