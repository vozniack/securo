import { NgForOf } from '@angular/common';
import { Component } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormControl } from '@angular/forms';
import { TranslatePipe } from '@ngx-translate/core';
import { tap } from 'rxjs/operators';
import { Theme } from '../../../../core/theme/theme.interface';
import { ThemeService } from '../../../../core/theme/theme.service';
import { IconComponent } from '../../../../shared/components/common/icon/icon.component';
import { SwitchComponent } from '../../../../shared/components/forms/switch/switch.component';
import { themes } from '../../../../core/theme/theme.const';

@Component({
  selector: 'sec-settings-theme',
  standalone: true,
  imports: [NgForOf, IconComponent, SwitchComponent, TranslatePipe],
  templateUrl: './settings-theme.component.html',
  styleUrl: './settings-theme.component.scss'
})
export class SettingsThemeComponent {

  theme!: string;
  themes!: Theme[];
  darkMode: FormControl = new FormControl(true);

  constructor(public themeService: ThemeService) {
    this.themeService.themeChange.pipe(
      takeUntilDestroyed(),
      tap((theme: string) => this.readTheme(theme))
    ).subscribe();

    this.readTheme(this.themeService.theme);

    this.darkMode.valueChanges.pipe(
      takeUntilDestroyed(),
      tap((value: boolean) => this.themeService.switchMode(value))
    ).subscribe();
  }

  readTheme(theme: string) {
    this.theme = theme;
    this.darkMode.setValue(this.theme.endsWith('-dark'));
    this.themes = themes.filter((theme: Theme) => theme.name.endsWith(this.darkMode.value ? '-dark' : '-light'));
  }

  setTheme(theme: string) {
    this.themeService.setTheme(theme);
  }
}
