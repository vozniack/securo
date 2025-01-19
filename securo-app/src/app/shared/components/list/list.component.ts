import { NgForOf, NgIf } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TranslatePipe } from '@ngx-translate/core';
import { fadeInAnimation } from '../../animations/fade-in-animation';
import { InfiniteScrollDirective } from '../../directives/infinite-scroll.directive';
import { Pageable } from '../../model/pageable.interface';
import { RequestParam } from '../../model/request.interface';
import { ButtonComponent } from '../buttons/button/button.component';
import { IconButtonComponent } from '../buttons/icon-button/icon-button.component';
import { ListEntry } from './list.interface';

@Component({
  selector: 'sec-list',
  standalone: true,
  imports: [NgForOf, IconButtonComponent, InfiniteScrollDirective, NgIf, ButtonComponent, TranslatePipe],
  templateUrl: './list.component.html',
  styleUrl: './list.component.scss',
  animations: [fadeInAnimation]
})
export class ListComponent {

  @Input()
  set data(data: Pageable<any>) {
    this._data = data;
    this.totalPages = data.page?.totalPages ? data.page.totalPages : 0;

    if (this.requestParam?.page != undefined) {
      this.allElements = this.requestParam.page + 1 == this.totalPages;
    }
  }

  @Input() entry!: ListEntry;
  @Input() requestParam!: RequestParam;

  @Output() requestParamChange = new EventEmitter<RequestParam>();
  @Output() actionActive = new EventEmitter<string>();

  _data!: Pageable<any>;
  totalPages!: number;
  allElements = false;

  getLine(row: any, fields: string[]): string {
    const values = fields.map(fieldName => row[fieldName])
      .filter(value => value !== undefined && value !== null);

    return values.join(' ');
  }

  action(id: string): void {
    this.actionActive.emit(id);
  }

  nextPage(): void {
    if (!this.allElements && this.requestParam.page != undefined && this.requestParam.page < this.totalPages - 1) {
      this.requestParamChange.emit({...this.requestParam, page: (this.requestParam.page + 1)});

      if (this.requestParam.page + 2 == this.totalPages) {
        this.allElements = true;
      }
    }
  }
}
