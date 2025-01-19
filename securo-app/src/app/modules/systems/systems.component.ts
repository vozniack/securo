import { BreakpointObserver } from '@angular/cdk/layout';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { TranslatePipe } from '@ngx-translate/core';
import { fadeInAnimation } from '../../shared/animations/fade-in-animation';
import { ResponsiveComponent } from '../../shared/common/responsive.component';
import { TextInputComponent } from '../../shared/components/forms/text-input/text-input.component';

@Component({
  selector: 'sec-systems',
  standalone: true,
  imports: [TranslatePipe, TextInputComponent],
  templateUrl: './systems.component.html',
  styleUrl: './systems.component.scss',
  animations: [fadeInAnimation]
})
export class SystemsComponent extends ResponsiveComponent {

  filters!: FormGroup;

  constructor(private formBuilder: FormBuilder, override breakpointObserver: BreakpointObserver) {
    super(breakpointObserver);

    this.filters = this.formBuilder.group({
      search: new FormControl(null),
    });
  }

  getControl(name: string): FormControl {
    return this.filters.get(name) as FormControl;
  }

}
