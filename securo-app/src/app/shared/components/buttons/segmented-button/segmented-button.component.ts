import { NgForOf, NgIf } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TranslatePipe } from '@ngx-translate/core';
import { fadeInAnimation } from '../../../animations/fade-in-animation';
import { IconComponent } from '../../common/icon/icon.component';
import { SegmentedButton } from './segmented-button.interface';

@Component({
  selector: 'sec-segmented-button',
  standalone: true,
  imports: [NgForOf, IconComponent, NgIf, TranslatePipe],
  templateUrl: './segmented-button.component.html',
  styleUrl: './segmented-button.component.scss',
  animations: [fadeInAnimation]
})
export class SegmentedButtonComponent implements OnInit {

  @Input() segments!: SegmentedButton[];
  @Output() segmentChange = new EventEmitter<SegmentedButton>();

  active!: SegmentedButton;

  ngOnInit(): void {
    this.active = this.segments[0];
  }

  setActive(active: SegmentedButton): void {
    this.segmentChange.emit(this.active = active);
  }
}
