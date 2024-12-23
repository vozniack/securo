import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';
import { select, Store } from '@ngrx/store';
import { TranslatePipe } from '@ngx-translate/core';
import { tap } from 'rxjs/operators';
import { I18nService } from '../../../core/i18n/I18nService';
import { SelectInputComponent } from '../../../shared/components/forms/select-input/select-input.component';
import { SelectInputOption } from '../../../shared/components/forms/select-input/select-input.interface';
import { SELECT_LANGUAGE } from '../../../store/app/app.selectors';

@Component({
  selector: 'sec-settings-language',
  standalone: true,
  imports: [TranslatePipe, SelectInputComponent],
  templateUrl: './settings-language.component.html',
  styleUrl: './settings-language.component.scss'
})
export class SettingsLanguageComponent {

  language: FormControl = new FormControl();
  languages: SelectInputOption[] = [
    {value: 'en', name: 'English'},
    {value: 'pl', name: 'Polski'},
  ];

  constructor(private store: Store, private i18nService: I18nService) {
    this.store.pipe(
      select(SELECT_LANGUAGE),
      tap((language: string) => this.language.setValue(language))
    ).subscribe();

    this.language.valueChanges.pipe(
      tap((language: string) => this.i18nService.setLanguage(language))
    ).subscribe();
  }
}
