import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormControl } from '@angular/forms';
import { select, Store } from '@ngrx/store';
import { TranslatePipe } from '@ngx-translate/core';
import { takeWhile, throwError } from 'rxjs';
import { catchError, switchMap, tap } from 'rxjs/operators';
import { I18nService } from '../../../core/i18n/I18nService';
import { SelectInputComponent } from '../../../shared/components/forms/select-input/select-input.component';
import { SelectInputOption } from '../../../shared/components/forms/select-input/select-input.interface';
import { ACTION_PROGRESS } from '../../../store/app/app.actions';
import { SELECT_LANGUAGE } from '../../../store/app/app.selectors';
import { User } from '../../users/users.interface';
import { UsersService } from '../../users/users.service';

@Component({
  selector: 'sec-settings-language',
  standalone: true,
  imports: [TranslatePipe, SelectInputComponent],
  templateUrl: './settings-language.component.html',
  styleUrl: './settings-language.component.scss'
})
export class SettingsLanguageComponent implements OnInit {

  @Input() user!: User;

  language: FormControl = new FormControl();

  languages: SelectInputOption[] = [
    {value: 'en_US', name: 'English'},
    {value: 'pl_PL', name: 'Polski'},
  ];

  constructor(private store: Store, private i18nService: I18nService, private userService: UsersService) {
    this.store.pipe(
      takeUntilDestroyed(),
      select(SELECT_LANGUAGE),
      takeWhile((language: string) => this.language.value !== language),
      tap((language: string) => this.language.setValue(language))
    ).subscribe();

    this.language.valueChanges.pipe(
      takeUntilDestroyed(),
      tap(() => this.store.dispatch(ACTION_PROGRESS({progress: 'loading'}))),
      switchMap((language: string) => this.userService.updateUserLanguage(this.user.id, {language: language})),
      tap((user: User) => this.i18nService.setLanguage(user.language)),
      tap(() => this.store.dispatch(ACTION_PROGRESS({progress: 'success'}))),
      catchError((response: HttpErrorResponse) => this.handleError(response))
    ).subscribe();
  }

  ngOnInit(): void {
    this.i18nService.setLanguage(this.user.language);
  }

  private handleError(error: HttpErrorResponse) {
    this.store.dispatch(ACTION_PROGRESS({progress: 'failure'}));
    return throwError(() => new Error(error.message));
  }
}
