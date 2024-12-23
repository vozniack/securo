import { HTTP_INTERCEPTORS, HttpClient, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideTranslateService, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MetaReducer, provideState, provideStore } from '@ngrx/store';
import { AuthInterceptor } from './core/auth/interceptor/auth.interceptor';
import { appReducer } from './store/app/app.reducers';
import { persistState } from './store/meta/persist.metareducer';

const httpLoaderFactory: (http: HttpClient) => TranslateHttpLoader =
  (http: HttpClient) => new TranslateHttpLoader(http, './i18n/', '.json');

export const appConfig: ApplicationConfig = {
  providers: [provideZoneChangeDetection({eventCoalescing: true}),
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    provideHttpClient(withInterceptorsFromDi()),
    provideRouter(routes),
    provideAnimationsAsync(),
    provideState('securo-storage', appReducer),
    provideStore(appReducer, {metaReducers: [persistState as MetaReducer]}),
    provideTranslateService({
      defaultLanguage: 'pl',
      loader: {provide: TranslateLoader, useFactory: httpLoaderFactory, deps: [HttpClient]}
    })
  ]
};
