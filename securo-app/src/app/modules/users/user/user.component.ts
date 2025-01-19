import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ActivatedRoute } from '@angular/router';
import { tap } from 'rxjs/operators';
import { fadeInAnimation } from '../../../shared/animations/fade-in-animation';
import { IconButtonComponent } from '../../../shared/components/buttons/icon-button/icon-button.component';
import { User } from '../users.interface';

@Component({
  selector: 'sec-user',
  standalone: true,
  imports: [IconButtonComponent],
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss',
  animations: [fadeInAnimation]
})
export class UserComponent {

  user!: User;

  constructor(private location: Location, private activatedRoute: ActivatedRoute) {
    this.activatedRoute.data.pipe(
      takeUntilDestroyed(),
      tap((data: any) => this.user = data.user)
    ).subscribe();
  }

  routeBack(): void {
    this.location.back();
  }
}
