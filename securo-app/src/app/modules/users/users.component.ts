import { Component } from '@angular/core';
import { TranslatePipe } from '@ngx-translate/core';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';

@Component({
  selector: 'sec-users',
  standalone: true,
  imports: [TranslatePipe],
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss',
  animations: [fadeInOutAnimation]
})
export class UsersComponent {

}
