import { Component } from '@angular/core';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';
import { InputComponent } from '../../shared/components/forms/input/input.component';

@Component({
  selector: 'sec-users',
  standalone: true,
  imports: [InputComponent],
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss',
  animations: [fadeInOutAnimation]
})
export class UsersComponent {

}
