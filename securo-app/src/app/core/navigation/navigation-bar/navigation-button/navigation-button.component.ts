import { Component, Input } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { IconComponent } from '../../../../shared/components/common/icon/icon.component';
import { NavigationLink } from '../../navigation.interface';

@Component({
  selector: 'sec-navigation-button',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, IconComponent],
  templateUrl: './navigation-button.component.html',
  styleUrl: './navigation-button.component.scss'
})
export class NavigationButtonComponent {

  @Input() navigationLink!: NavigationLink;
}
