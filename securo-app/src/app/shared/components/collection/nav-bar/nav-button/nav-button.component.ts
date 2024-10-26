import { NgIf } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { NavLink } from '../../../../../core/navigation/navigation.interface';
import { IconComponent } from '../../../common/icon/icon.component';

@Component({
  selector: 'sec-nav-button',
  standalone: true,
  imports: [
    RouterLink,
    RouterLinkActive,
    IconComponent,
    NgIf
  ],
  templateUrl: './nav-button.component.html',
  styleUrl: './nav-button.component.scss'
})
export class NavButtonComponent {

  @Input()
  link!: NavLink;
}
