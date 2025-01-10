import { Component, DestroyRef, inject, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ListComponent } from '../../../shared/components/list/list.component';
import { Pageable } from '../../../shared/model/pageable.interface';
import { RequestParam } from '../../../shared/model/request.interface';
import { User } from '../users.interface';
import { UsersService } from '../users.service';
import { userListEntry } from './users-list.const';

@Component({
  selector: 'sec-users-list',
  standalone: true,
  imports: [ListComponent],
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.scss'
})
export class UsersListComponent implements OnInit {

  @Input() filters!: FormGroup;

  data: Pageable<User> = {};
  requestParam: RequestParam = {page: 0, size: 10};

  userListEntry = userListEntry;

  private destroyRef = inject(DestroyRef);

  constructor(private router: Router, private usersService: UsersService) {
    this.getUsers(true);
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
      tap(() => this.getUsers(true))
    ).subscribe();
  }

  getUsers(refresh: boolean): void {
    this.usersService.getUsersPage(this.requestParam).pipe(
      tap((response: Pageable<User>) => {
        if (refresh || this.data.content == null) {
          this.data = response;
        } else if (response.content != undefined) {
          this.data.content = this.data.content.concat(response.content);
        }
      }),
    ).subscribe();
  }

  onRequestParamChange(requestParam: RequestParam): void {
    this.requestParam = requestParam;
    this.getUsers(false);
  }

  onActionActive(id: string): void {
    this.router.navigate(['/users/' + id]).then();
  }
}
