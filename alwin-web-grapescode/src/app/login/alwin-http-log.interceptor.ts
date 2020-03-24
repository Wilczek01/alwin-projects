
import {throwError as observableThrowError, Observable} from 'rxjs';
import {Injectable, Injector} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import { catchError } from 'rxjs/operators';

import { MatDialog } from '@angular/material/dialog';
import {RefreshTokenDialogComponent} from './refresh/refresh-token-dialog.component';
import {Router} from '@angular/router';

@Injectable()
export class AlwinHttpLogInterceptor implements HttpInterceptor {

  constructor(private injector: Injector) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next
      .handle(request).pipe(
        catchError(response => {
          if (response instanceof HttpErrorResponse && response.status === 401) {
            const router = this.injector.get(Router);
            if (router.url !== '/login') {
              const dialog = this.injector.get(MatDialog);
              dialog.openDialogs.forEach(d => {
                  if (d.componentInstance.refreshSessionToken) {
                    d.close(false);
                  }
                }
              );
              dialog.open(RefreshTokenDialogComponent);
            }
          }
          return observableThrowError(response);
        })
      );
  }
}
