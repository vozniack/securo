import {
  CdkDrag,
  CdkDragDrop,
  CdkDragPlaceholder,
  CdkDropList,
  DragDropModule,
  moveItemInArray
} from '@angular/cdk/drag-drop';
import { NgForOf, NgIf } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Store } from '@ngrx/store';
import { TranslatePipe } from '@ngx-translate/core';
import { catchError, tap } from 'rxjs/operators';
import { Environment, EnvironmentType } from '../../../../domain/entity/environment.interface';
import { EnvironmentService } from '../../../../domain/services/environment.service';
import { ButtonComponent } from '../../../../shared/components/buttons/button/button.component';
import { IconButtonComponent } from '../../../../shared/components/buttons/icon-button/icon-button.component';
import { handleError } from '../../../../shared/utils/request.utils';
import { ACTION_PROGRESS } from '../../../../store/app/app.actions';

@Component({
  selector: 'sec-settings-environments',
  standalone: true,
  imports: [TranslatePipe, NgForOf, NgIf, IconButtonComponent, ButtonComponent, DragDropModule, CdkDropList, CdkDrag, CdkDragPlaceholder],
  templateUrl: './settings-environments.component.html',
  styleUrl: './settings-environments.component.scss'
})
export class SettingsEnvironmentsComponent {

  types: string[] = Object.keys(EnvironmentType);
  environments!: Map<EnvironmentType, Environment[]>;

  constructor(private store: Store, private environmentService: EnvironmentService) {
    this.environmentService.getEnvironments().pipe(
      takeUntilDestroyed(),
      tap((response: Map<EnvironmentType, Environment[]>) => {
        this.environments = new Map<EnvironmentType, Environment[]>(
          Object.entries(response) as [EnvironmentType, Environment[]][]
        );
      })
    ).subscribe();
  }

  getEnvironments(type: string): Environment[] {
    return this.environments.get(type as EnvironmentType) ?? [];
  }

  updateEnvironmentsOrder(event: CdkDragDrop<Environment[]>, type: string): void {
    const tempEnvironments: Environment[] = this.environments.get(type as EnvironmentType) ?? [];

    if (tempEnvironments && event.previousIndex != event.currentIndex) {
      moveItemInArray(tempEnvironments, event.previousIndex, event.currentIndex);
      tempEnvironments.forEach((env, index) => env.index = index);

      this.updateEnvironments(type, tempEnvironments);
    }
  }

  updateEnvironment(environment: Environment): void {
  }

  removeEnvironment(environment: Environment): void {
  }

  private updateEnvironments(type: string, environments: Environment[]): void {
    this.store.dispatch(ACTION_PROGRESS({progress: 'loading'}));

    this.environmentService.updateOrder({type: type as EnvironmentType, environments: environments}).pipe(
      tap(() => {
        this.environments.set(type as EnvironmentType, environments);
        this.store.dispatch(ACTION_PROGRESS({progress: 'success'}));
      }),
      catchError((response: HttpErrorResponse) => handleError(this.store, response))
    ).subscribe();
  }
}
