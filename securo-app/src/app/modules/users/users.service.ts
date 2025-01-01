import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Pageable } from '../../shared/model/pageable.interface';
import { RequestParam } from '../../shared/model/request.interface';
import { buildHttpParams } from '../../shared/utils/request.utils';
import { User } from './users.interface';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  baseUrl = `${environment.backendUrl}/v1/users`;

  constructor(private httpClient: HttpClient) {

  }

  getUser(id: string): Observable<User> {
    return this.httpClient.get<User>(`${this.baseUrl}/${id}`);
  }

  getUsersPage(requestParam: RequestParam): Observable<Pageable<User>> {
    return this.httpClient.get<Pageable<User>>(`${this.baseUrl}/page`, {
      params: buildHttpParams(requestParam)
    });
  }
}
