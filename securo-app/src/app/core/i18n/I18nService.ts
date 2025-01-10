import { Injectable } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { select, Store } from '@ngrx/store';
import { TranslateService } from '@ngx-translate/core';
import { tap } from 'rxjs/operators';
import { ACTION_LANGUAGE } from '../../store/app/app.actions';
import { SELECT_LANGUAGE } from '../../store/app/app.selectors';

@Injectable({
  providedIn: 'root'
})
export class I18nService {

  constructor(private store: Store, private translateService: TranslateService) {
    this.applyLanguage();
  }

  public applyLanguage() {
    this.store.pipe(
      takeUntilDestroyed(),
      select(SELECT_LANGUAGE),
      tap((language: string) => this.translateService.use(language.split('_')[0]))
    ).subscribe();
  }

  setLanguage(language: string) {
    this.store.dispatch(ACTION_LANGUAGE({language: language != null ? language : 'en_US'}));
  }
}
