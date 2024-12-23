import { Component } from '@angular/core';
import { TranslatePipe } from '@ngx-translate/core';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';
import { InputComponent } from '../../shared/components/forms/input/input.component';

@Component({
  selector: 'sec-users',
  standalone: true,
  imports: [TranslatePipe, InputComponent],
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss',
  animations: [fadeInOutAnimation]
})
export class UsersComponent {

}
