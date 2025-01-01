import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { tap } from 'rxjs/operators';
import { fadeInOutAnimation } from '../../../shared/animations/fade-in-out-animation';
import { IconButtonComponent } from '../../../shared/components/buttons/icon-button/icon-button.component';
import { User } from '../users.interface';

@Component({
  selector: 'sec-user',
  standalone: true,
  imports: [IconButtonComponent],
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss',
  animations: [fadeInOutAnimation]
})
export class UserComponent {

  user!: User;

  constructor(private location: Location, private activatedRoute: ActivatedRoute) {
    this.activatedRoute.data.pipe(
      tap((data: any) => this.user = data.user)
    ).subscribe();
  }

  routeBack(): void {
    this.location.back();
  }
}
