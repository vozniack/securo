import { NgForOf } from '@angular/common';
import { Component, Input } from '@angular/core';
import { NavLink } from '../../../../core/navigation/navigation.interface';
import { NavButtonComponent } from './nav-button/nav-button.component';

@Component({
  selector: 'sec-nav-bar',
  standalone: true,
  imports: [
    NavButtonComponent,
    NgForOf
  ],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.scss'
})
export class NavBarComponent {

  @Input()
  navLinks!: NavLink[];

  @Input()
  label: Boolean = true;
}
