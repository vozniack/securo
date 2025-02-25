import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormControl } from '@angular/forms';
import { select, Store } from '@ngrx/store';
import { TranslatePipe } from '@ngx-translate/core';
import { takeWhile } from 'rxjs';
import { catchError, switchMap, tap } from 'rxjs/operators';
import { I18nService } from '../../../../core/i18n/I18nService';
import { SelectInputComponent } from '../../../../shared/components/forms/select-input/select-input.component';
import { SelectInputOption } from '../../../../shared/components/forms/select-input/select-input.interface';
import { handleError } from '../../../../shared/utils/request.utils';
import { ACTION_PROGRESS } from '../../../../store/app/app.actions';
import { SELECT_LANGUAGE } from '../../../../store/app/app.selectors';
import { User } from '../../../../domain/entity/user.interface';
import { UserService } from '../../../../domain/services/user.service';

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

  constructor(private store: Store, private i18nService: I18nService, private userService: UserService) {
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
      catchError((response: HttpErrorResponse) => handleError(this.store, response))
    ).subscribe();
  }

  ngOnInit(): void {
    this.i18nService.setLanguage(this.user.language);
  }
}
