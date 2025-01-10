import { DatePipe, NgForOf, NgIf } from '@angular/common';
import { Component, DestroyRef, EventEmitter, inject, Input, OnInit, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { TranslatePipe } from '@ngx-translate/core';
import { tap } from 'rxjs/operators';
import { Pageable } from '../../model/pageable.interface';
import { RequestParam, SortDirection } from '../../model/request.interface';
import { IconButtonComponent } from '../buttons/icon-button/icon-button.component';
import { IconComponent } from '../common/icon/icon.component';
import { TablePaginationComponent } from './table-pagination/table-pagination.component';
import { ColumnType, TableAction, TableColumn } from './table.interface';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'sec-table',
  standalone: true,
  imports: [NgIf, NgForOf, IconComponent, DatePipe, TablePaginationComponent, TranslatePipe, IconButtonComponent],
  templateUrl: './table.component.html',
  styleUrl: './table.component.scss'
})
export class TableComponent implements OnInit {

  @Input()
  set data(data: Pageable<any>) {
    if (data.content && data.page) {
      this._data = data;

      this.totalPages = data.page!!.totalPages;
      this.totalElements = data.page!!.totalElements;

      this.pageSizeForm.setValue(data.page!!.size);
    }
  }

  @Input() columns: TableColumn[] = [];
  @Input() actions: TableAction[] = [];
  @Input() requestParam!: RequestParam;
  @Input() searchFormControl?: FormControl;

  @Output() requestParamChange = new EventEmitter<RequestParam>();
  @Output() actionActive = new EventEmitter<TableAction>();

  pageSizeForm: FormControl = new FormControl();
  paginationReset = new Subject();

  _data!: Pageable<any>;

  totalPages!: number;
  totalElements!: number;

  columnType = ColumnType;

  private destroyRef = inject(DestroyRef);

  ngOnInit(): void {
    const destroyed = new Subject<void>();

    this.destroyRef.onDestroy(() => {
      destroyed.next();
      destroyed.complete();
    });

    this.pageSizeForm.valueChanges.pipe(
      takeUntil(destroyed),
      tap((size: number) => {
        if (this.requestParam.size != size) {
          this.requestParamChange.emit({...this.requestParam, page: 0, size: size});
          this.paginationReset.next(true);
        }
      })
    ).subscribe();
  }

  /* Changes */

  onSortChange(tableColumn: TableColumn): void {
    if (tableColumn.sortable) {
      if (this.requestParam?.sort != null && this.requestParam.sort.field === tableColumn.field) {
        switch (this.requestParam.sort.direction) {
          case SortDirection.ASC:
            this.requestParam.sort.direction = SortDirection.DESC;
            break;

          case SortDirection.DESC:
            this.requestParam.sort = undefined;
            break;
        }
      } else {
        this.requestParam.sort = {field: tableColumn.field, direction: SortDirection.ASC};
      }

      this.requestParamChange.emit(this.requestParam);
    }
  }

  onPageChange(page: number): void {
    this.requestParamChange.emit({...this.requestParam, page: page});
  }

  onActionActive(action: TableAction, id: string): void {
    action.data = id;
    this.actionActive.emit(action);
  }

  /* Getter */

  getFieldValue(row: any, field?: string): string {
    return field?.split('.').reduce((o, key) => o[key], row);
  }

  getBooleanFieldValue(row: any, field?: string): boolean {
    return field?.split('.').reduce((o, key) => o[key], row) == true;
  }
}
