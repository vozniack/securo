import { BreakpointObserver } from '@angular/cdk/layout';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { tap } from 'rxjs/operators';
import { mobileWidth } from '../const/responsive.const';

export abstract class ResponsiveComponent {

  protected isMobile!: boolean;

  protected constructor(protected breakpointObserver: BreakpointObserver) {
    this.breakpointObserver.observe([mobileWidth]).pipe(
      takeUntilDestroyed(),
      tap(breakpoint => this.isMobile = breakpoint.matches)
    ).subscribe();
  }
}
