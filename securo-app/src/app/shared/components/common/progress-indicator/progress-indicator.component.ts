import { BreakpointObserver } from '@angular/cdk/layout';
import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { tap } from 'rxjs/operators';
import { ACTION_PROGRESS } from '../../../../store/app/app.actions';
import { SELECT_PROGRESS } from '../../../../store/app/app.selectors';
import { fadeInOutAnimation } from '../../../animations/fade-in-out-animation';
import { ResponsiveComponent } from '../../../common/responsive.component';
import { IconComponent } from '../icon/icon.component';

@Component({
  selector: 'sec-progress-indicator',
  standalone: true,
  imports: [NgIf, IconComponent],
  templateUrl: './progress-indicator.component.html',
  styleUrl: './progress-indicator.component.scss',
  animations: [fadeInOutAnimation]
})
export class ProgressIndicatorComponent extends ResponsiveComponent {

  progress: undefined | 'none' | 'loading' | 'success' | 'failure';

  constructor(private store: Store, override breakpointObserver: BreakpointObserver) {
    super(breakpointObserver);

    this.store.pipe(
      select(SELECT_PROGRESS),
      tap((progress: 'none' | 'loading' | 'success' | 'failure') => this.hideIndicator(this.progress = progress))
    ).subscribe();
  }

  hideIndicator(progress: 'none' | 'loading' | 'success' | 'failure'): void {
    if (progress == 'success' || progress == 'failure') {
      setTimeout(() => this.store.dispatch(ACTION_PROGRESS({progress: 'none'})), 1024);
    }
  }
}
