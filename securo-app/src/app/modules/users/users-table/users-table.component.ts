import { Component, DestroyRef, inject, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { tap } from 'rxjs/operators';
import { TableComponent } from '../../../shared/components/table/table.component';
import { TableAction } from '../../../shared/components/table/table.interface';
import { Pageable } from '../../../shared/model/pageable.interface';
import { RequestParam } from '../../../shared/model/request.interface';
import { User } from '../users.interface';
import { UsersService } from '../users.service';
import { userActions, userColumns } from './users-table.const';

@Component({
  selector: 'sec-users-table',
  standalone: true,
  imports: [TableComponent],
  templateUrl: './users-table.component.html',
  styleUrl: './users-table.component.scss'
})
export class UsersTableComponent implements OnInit {

  @Input() filters!: FormGroup;

  data: Pageable<User> = {};
  requestParam: RequestParam = {page: 0, size: 10};

  columns = userColumns;
  actions = userActions;

  private destroyRef = inject(DestroyRef);

  constructor(private router: Router, private usersService: UsersService) {
    this.getUsers();
  }

  ngOnInit(): void {
    const destroyed = new Subject<void>();

    this.destroyRef.onDestroy(() => {
      destroyed.next();
      destroyed.complete();
    });

    this.filters.valueChanges.pipe(
      takeUntil(destroyed),
      tap((filters: any) => {
        this.requestParam.page = 0;
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

  onActionActive(action: TableAction): void {
    if (action.name == 'VIEW') {
      this.router.navigate(['/users/' + action.data]).then();
    }
  }
}
