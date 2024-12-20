import { Component } from '@angular/core';
import { NavigationBarComponent } from '../navigation/navigation-bar/navigation-bar.component';

@Component({
  selector: 'sec-bottom-bar',
  standalone: true,
  imports: [NavigationBarComponent],
  templateUrl: './bottom-bar.component.html',
  styleUrl: './bottom-bar.component.scss',
})
export class BottomBarComponent {
}
