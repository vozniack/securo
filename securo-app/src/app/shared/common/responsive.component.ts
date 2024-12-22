import { BreakpointObserver } from '@angular/cdk/layout';
import { tap } from 'rxjs/operators';
import { mobileWidth } from '../const/responsive.const';

export abstract class ResponsiveComponent {

  protected isMobile!: boolean;

  protected constructor(protected breakpointObserver: BreakpointObserver) {
    this.breakpointObserver.observe([mobileWidth]).pipe(
      tap(breakpoint => this.isMobile = breakpoint.matches)
    ).subscribe();
  }
}
