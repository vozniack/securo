import { NgForOf } from '@angular/common';
import { Component } from '@angular/core';
import { tap } from 'rxjs/operators';
import { themes } from '../../../core/theme/theme.const';
import { Theme } from '../../../core/theme/theme.interface';
import { ThemeService } from '../../../core/theme/theme.service';
import { IconButtonComponent } from '../../../shared/components/buttons/icon-button/icon-button.component';
import { IconComponent } from '../../../shared/components/common/icon/icon.component';

@Component({
  selector: 'sec-settings-theme',
  standalone: true,
  imports: [NgForOf, IconComponent, IconButtonComponent],
  templateUrl: './settings-theme.component.html',
  styleUrl: './settings-theme.component.scss'
})
export class SettingsThemeComponent {

  theme!: string;
  themes!: Theme[];
  dark!: boolean;

  constructor(public themeService: ThemeService) {
    this.themeService.themeChange.pipe(
      tap((theme: string) => this.readTheme(theme))
    ).subscribe();

    this.readTheme(this.themeService.theme);
  }

  readTheme(theme: string) {
    this.theme = theme;
    this.dark = this.theme.endsWith('-dark');
    this.themes = themes.filter((theme: Theme) => theme.name.endsWith(this.dark ? '-dark' : '-light'));
  }

  setTheme(theme: string) {
    this.themeService.setTheme(theme);
  }

  switchMode() {
    this.themeService.switchMode();
  }
}
