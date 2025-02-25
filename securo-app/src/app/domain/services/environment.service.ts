import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Environment, EnvironmentType, UpdateEnvironmentsOrderDto } from '../entity/environment.interface';

@Injectable({
  providedIn: 'root'
})
export class EnvironmentService {

  baseUrl = `${environment.backendUrl}/v1/environments`;

  constructor(private httpClient: HttpClient) {
  }

  getEnvironments(): Observable<Map<EnvironmentType, Environment[]>> {
    return this.httpClient.get<Map<EnvironmentType, Environment[]>>(`${this.baseUrl}/list`);
  }

  updateOrder(request: UpdateEnvironmentsOrderDto): Observable<Environment[]> {
    return this.httpClient.put<Environment[]>(`${this.baseUrl}/order`, request);
  }
}
