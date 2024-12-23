import { Component } from '@angular/core';
import { TranslatePipe } from '@ngx-translate/core';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';
import { TextInputComponent } from '../../shared/components/forms/text-input/text-input.component';

@Component({
  selector: 'sec-users',
  standalone: true,
  imports: [TranslatePipe, TextInputComponent],
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss',
  animations: [fadeInOutAnimation]
})
export class UsersComponent {

}
