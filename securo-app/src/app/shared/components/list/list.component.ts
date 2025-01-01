import { NgForOf, NgIf } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Pageable } from '../../model/pageable.interface';
import { RequestParam } from '../../model/request.interface';
import { IconButtonComponent } from '../buttons/icon-button/icon-button.component';
import { IconComponent } from '../common/icon/icon.component';
import { ListEntry } from './list.interface';

@Component({
  selector: 'sec-list',
  standalone: true,
  imports: [NgForOf, IconButtonComponent, NgIf, IconComponent],
  templateUrl: './list.component.html',
  styleUrl: './list.component.scss'
})
export class ListComponent {

  @Input()
  set data(data: Pageable<any>) {
    this._data = data;
  }

  @Input() entry!: ListEntry;
  @Input() requestParam!: RequestParam;

  @Output() viewActive = new EventEmitter<string>();

  _data!: Pageable<any>;

  getLine(row: any, fields: string[]): string {
    const values = fields.map(fieldName => row[fieldName])
      .filter(value => value !== undefined && value !== null);

    return values.join(' ');
  }

  onActionActive(id: string): void {
    this.viewActive.emit(id);
  }
}
