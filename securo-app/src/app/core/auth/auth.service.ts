import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../../environments/environment';
import { SELECT_APP_STATE } from '../../store/app/app.selectors';
import { AppState } from '../../store/app/app.state';
import { LoginRequest, LoginResponse } from './auth.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  baseUrl = `${environment.backendUrl}/v1/auth`;
  token: string | undefined = '';

  constructor(private store: Store, private httpClient: HttpClient) {
    this.store.pipe(
      select(SELECT_APP_STATE),
      tap((state: AppState) => this.token = state?.user?.token),
    ).subscribe();
  }

  login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.httpClient.post<LoginResponse>(this.baseUrl, loginRequest);
  }
}
