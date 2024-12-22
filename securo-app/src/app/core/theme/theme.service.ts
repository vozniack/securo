import { Injectable } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Subject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ACTION_THEME } from '../../store/app/app.actions';
import { SELECT_THEME } from '../../store/app/app.selectors';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  theme!: string;
  themeChange: Subject<string> = new Subject();

  constructor(private store: Store) {
    this.applyTheme();
  }

  public applyTheme() {
    this.store.pipe(
      select(SELECT_THEME),
      tap((theme: string) => document.body.setAttribute('theme', this.theme = theme))
    ).subscribe();
  }

  public setTheme(theme: string) {
    this.store.dispatch(ACTION_THEME({theme: this.theme = theme}));
    this.themeChange.next(this.theme);
  }

  public switchMode(darkMode: boolean) {
    if ((darkMode && this.theme.endsWith('-light')) || (!darkMode && this.theme.endsWith('-dark'))) {
      this.store.dispatch(ACTION_THEME({theme: this.theme = (this.theme.split('-')[0] + (darkMode ? '-dark' : '-light'))}));
      this.themeChange.next(this.theme);
    }
  }
}
