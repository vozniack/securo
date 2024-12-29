import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Subject, takeUntil } from 'rxjs';
import { tap } from 'rxjs/operators';
import { TableComponent } from '../../../shared/components/table/table.component';
import { Pageable } from '../../../shared/model/pageable.interface';
import { RequestParam } from '../../../shared/model/request.interface';
import { User } from '../users.interface';
import { UsersService } from '../users.service';
import { userActions, userColumns } from './users-table.const';

@Component({
  selector: 'sec-users-table',
  standalone: true,
  imports: [
    TableComponent
  ],
  templateUrl: './users-table.component.html',
  styleUrl: './users-table.component.scss'
})
export class UsersTableComponent implements OnInit {

  @Input() filters!: FormGroup;

  data: Pageable<User> = {};
  requestParam: RequestParam = {page: 0, size: 25};

  columns = userColumns;
  actions = userActions;

  ngDestroyed$ = new Subject<boolean>();

  constructor(private usersService: UsersService) {
    this.getUsers();
  }

  ngOnInit(): void {
    this.filters.valueChanges.pipe(
      takeUntil(this.ngDestroyed$),
      tap((filters: any) => {
        this.requestParam.page = 0;
        this.requestParam.size = filters.size;
        this.requestParam.search = filters.search;
      }),
      tap(() => this.getUsers())
    ).subscribe();
  }

  getUsers(): void {
    this.usersService.getUsersPage(this.requestParam).pipe(
      tap((response: Pageable<User>) => this.data = response),
    ).subscribe();
  }

  onRequestParamChange(requestParam: RequestParam): void {
    this.requestParam = requestParam;
    this.getUsers();
  }
}
