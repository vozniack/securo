import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MetaReducer, provideState, provideStore } from '@ngrx/store';
import { AuthInterceptor } from './core/auth/interceptor/auth.interceptor';
import { appReducer } from './store/app/app.reducers';
import { persistState } from './store/meta/persist.metareducer';

export const appConfig: ApplicationConfig = {
  providers: [provideZoneChangeDetection({eventCoalescing: true}),
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    provideHttpClient(withInterceptorsFromDi()),
    provideRouter(routes),
    provideAnimationsAsync(),
    provideState('securo-storage', appReducer),
    provideStore(appReducer, {metaReducers: [persistState as MetaReducer]}),
  ]
};
