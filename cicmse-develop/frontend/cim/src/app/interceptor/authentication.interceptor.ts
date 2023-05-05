import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {JwtPayload} from "jwt-decode";
import { catchError, map, Observable, switchMap, throwError } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';
import { Router } from '@angular/router';
import { User } from '../model/user.model';


@Injectable()
export class AuthenInterceptor implements HttpInterceptor {
  private user: User = new User();
  constructor(private authenticationService: AuthenticationService, private router: Router) {
    this.authenticationService.currentUser.subscribe((user: User) => {
      this.user = user;
    })
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (request.url.indexOf('refresh-token') > -1 || request.url.indexOf('logOut') > -1) {
      return next.handle(request)
    }else {
      return this.handleRequest(request, next)
    }
  }

  private handleRequest(request: HttpRequest<unknown>, next: HttpHandler) {
    return next.handle(request).pipe(
      catchError((error: Response) => {
        if (error.status == 403) {
          return this.authenticationService.refreshToken().pipe(
            switchMap(() => {
              return next.handle(request);
            }),
            catchError( err => {
              this.authenticationService.logout(this.user).subscribe(msg => {
                //console.log(msg);
              });
              this.router.navigate(['/login']);

              return throwError(() => new Error(err));
            })
          );
        } else {
          this.authenticationService.logout(this.user).subscribe(msg => {
            //console.log(msg);
          });
          this.router.navigate(['/login']);

          return throwError(() => error);
        }
      })
    );

  }
}
