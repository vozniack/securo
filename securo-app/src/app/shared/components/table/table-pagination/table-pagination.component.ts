import { NgIf } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { TranslatePipe } from '@ngx-translate/core';
import { Subject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { fadeInOutAnimation } from '../../../animations/fade-in-out-animation';
import { ButtonComponent } from '../../buttons/button/button.component';
import { SelectInputComponent } from '../../forms/select-input/select-input.component';
import { SelectInputOption } from '../../forms/select-input/select-input.interface';

@Component({
  selector: 'sec-table-pagination',
  standalone: true,
  imports: [ButtonComponent, NgIf, SelectInputComponent, TranslatePipe],
  templateUrl: './table-pagination.component.html',
  styleUrl: './table-pagination.component.scss',
  animations: [fadeInOutAnimation]
})
export class TablePaginationComponent implements OnInit {

  @Input() pageSizeForm!: FormControl;

  @Input() totalPages!: number;
  @Input() totalElements!: number;

  @Input() borderButtons: number = 1;

  @Input() reset!: Subject<any>;

  @Output() pageChange = new EventEmitter<number>();

  pages: number[] = [];
  page = 1;

  pageSizes: SelectInputOption[] = [
    {value: 10, name: '10'},
    {value: 15, name: '15'},
    {value: 25, name: '25'},
  ];

  ngOnInit(): void {
    setTimeout(() => this.countPages(false), 32);

    this.reset.pipe(
      tap(() => this.page = 1)
    ).subscribe();
  }

  /* Counter */

  countPages(emitChange: boolean): void {
    this.pages = [];

    let iterator = 0;
    for (let i = -(this.borderButtons); i < (this.borderButtons * 2) - (this.borderButtons - 1); i++) {
      this.pages[iterator++] = this.page + i;
    }

    if (emitChange) {
      this.pageChange.emit(this.page - 1);
    }
  }

  /* Page changes */

  prevPage(): void {
    if (this.page - 1 > 0) {
      this.page--;
      this.countPages(true);
    }
  }

  nextPage(): void {
    if (this.page + 1 <= this.totalPages!!) {
      this.page++;
      this.countPages(true);
    }
  }

  changePage(page: number): void {
    if (this.page != page) {
      this.page = page;
      this.countPages(true);
    }
  }
}
