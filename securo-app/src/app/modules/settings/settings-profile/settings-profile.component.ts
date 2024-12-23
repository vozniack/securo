import { Component } from '@angular/core';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'sec-settings-profile',
  standalone: true,
  imports: [TranslatePipe],
  templateUrl: './settings-profile.component.html',
  styleUrl: './settings-profile.component.scss'
})
export class SettingsProfileComponent {

}
