import { NgIf } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { select, Store } from '@ngrx/store';
import { tap } from 'rxjs/operators';
import { ContentComponent } from './core/content/content.component';
import { LoginComponent } from './core/login/login.component';
import { ThemeService } from './core/theme/theme.service';
import { ToolbarComponent } from './core/toolbar/toolbar.component';
import { SELECT_USER_STATE } from './store/app/app.selectors';
import { UserState } from './store/app/app.state';

@Component({
  selector: 'sec-root',
  standalone: true,
  imports: [ToolbarComponent, ContentComponent, NgIf, LoginComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {

  logged: boolean = true;

  constructor(private store: Store, private router: Router, private themeService: ThemeService) {
  }

  ngOnInit(): void {
    this.store.pipe(
      select(SELECT_USER_STATE),
      tap((state: UserState) => this.logged = !!state.token),
      tap(() => this.redirectToLogin())
    ).subscribe();
  }

  // temp solution for fun
  @HostListener('window:keydown.control.b', ['$event'])
  switchTheme(event: KeyboardEvent) {
    event.preventDefault();
    this.themeService.switchTheme();
  }

  redirectToLogin(): void {
    if (!this.logged) {
      this.router.navigate(['/login']).then();
    }
  }
}
