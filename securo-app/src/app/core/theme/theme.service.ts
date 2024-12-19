import { Injectable } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { tap } from 'rxjs/operators';
import { ACTION_THEME } from '../../store/app/app.actions';
import { SELECT_THEME } from '../../store/app/app.selectors';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  theme!: string;

  themes: string[] = [
    'brown-light',
    'brown-dark',
    'purple-light',
    'purple-dark',
    'green-light',
    'green-dark',
    'blue-light',
    'blue-dark'
  ];

  constructor(private store: Store) {
    this.store.pipe(
      select(SELECT_THEME),
      tap((theme: string) => document.body.setAttribute('data-theme', this.theme = theme))
    ).subscribe();
  }

  switchTheme(): void {
    this.store.dispatch(ACTION_THEME({theme: this.themes[(this.themes.indexOf(this.theme) + 1) % this.themes.length]}));
  }
}
