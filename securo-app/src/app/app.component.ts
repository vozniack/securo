import { NgIf } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { select, Store } from '@ngrx/store';
import { tap } from 'rxjs/operators';
import { BottomBarComponent } from './core/bottom-bar/bottom-bar.component';
import { ContentComponent } from './core/content/content.component';
import { LoginComponent } from './core/login/login.component';
import { ThemeService } from './core/theme/theme.service';
import { TopBar } from './core/top-bar/top-bar.component';
import { SELECT_USER_STATE } from './store/app/app.selectors';
import { UserState } from './store/app/app.state';

@Component({
  selector: 'sec-root',
  standalone: true,
  imports: [TopBar, ContentComponent, NgIf, LoginComponent, BottomBarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {

  logged: boolean = true;
  mobile: boolean = false;

  constructor(private store: Store, private router: Router, private themeService: ThemeService) {
    this.detectMobile();
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
  public switchTheme(event: KeyboardEvent) {
    event.preventDefault();
    this.themeService.switchTheme();
  }

  @HostListener('window:resize', [])
  public detectMobile() {
    this.mobile = window.innerWidth <= 820;
  }

  private redirectToLogin(): void {
    if (!this.logged) {
      this.router.navigate(['/login']).then();
    }
  }
}
