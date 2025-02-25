import { HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Store } from '@ngrx/store';
import { throwError } from 'rxjs';
import { ACTION_PROGRESS } from '../../store/app/app.actions';
import { RequestParam, SortRequest } from '../model/request.interface';

export function buildHttpParams(requestParam?: RequestParam): HttpParams {
  let httpParams: HttpParams = new HttpParams();

  if (requestParam != null) {
    httpParams = appendIfNotNull(httpParams, 'page', requestParam.page);
    httpParams = appendIfNotNull(httpParams, 'size', requestParam.size);
    httpParams = appendSortIfNotNull(httpParams, 'sort', requestParam.sort);
    httpParams = appendIfNotNull(httpParams, 'search', requestParam.search);
  }

  return httpParams;
}

function appendSortIfNotNull(httpParams: HttpParams, param: string, data?: SortRequest): HttpParams {
  return data != null ? httpParams.append(param, `${data.field},${data.direction}`) : httpParams;
}

function appendIfNotNull(httpParams: HttpParams, param: string, data: any): HttpParams {
  return data != null ? httpParams.append(param, data) : httpParams;
}

export function handleError(store: Store, error: HttpErrorResponse) {
  store.dispatch(ACTION_PROGRESS({progress: 'failure'}));
  return throwError(() => new Error(error.message));
}
