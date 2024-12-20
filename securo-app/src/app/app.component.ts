import { BreakpointObserver } from '@angular/cdk/layout';
import { NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { select, Store } from '@ngrx/store';
import { tap } from 'rxjs/operators';
import { BottomBarComponent } from './core/bottom-bar/bottom-bar.component';
import { ContentComponent } from './core/content/content.component';
import { LoginComponent } from './core/login/login.component';
import { ThemeService } from './core/theme/theme.service';
import { TopBar } from './core/top-bar/top-bar.component';
import { ResponsiveComponent } from './shared/common/responsive.component';
import { SELECT_USER_STATE } from './store/app/app.selectors';
import { UserState } from './store/app/app.state';

@Component({
  selector: 'sec-root',
  standalone: true,
  imports: [TopBar, ContentComponent, NgIf, LoginComponent, BottomBarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent extends ResponsiveComponent implements OnInit {

  logged: boolean = true;

  constructor(private store: Store, private router: Router, private themeService: ThemeService, override breakpointObserver: BreakpointObserver) {
    super(breakpointObserver);
    this.themeService.applyTheme();
  }

  ngOnInit(): void {
    this.store.pipe(
      select(SELECT_USER_STATE),
      tap((state: UserState) => this.logged = !!state.token),
      tap(() => this.redirectToLogin())
    ).subscribe();
  }

  private redirectToLogin(): void {
    if (!this.logged) {
      this.router.navigate(['/login']).then();
    }
  }
}
