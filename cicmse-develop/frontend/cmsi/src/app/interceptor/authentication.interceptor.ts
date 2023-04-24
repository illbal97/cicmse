import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import jwtDecode, {JwtPayload} from "jwt-decode";
import { catchError, Observable, switchMap, throwError } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';
import { Router } from '@angular/router';
import { User } from '../model/user.model';
import { RefreshTokenInvalidComponent } from '../components/refresh-token-invalid/refresh-token-invalid.component';

const HEADER_AUTHORIZATION = "authorization";

@Injectable()
export class AuthenInterceptor implements HttpInterceptor {

  private jwt: JwtPayload = {};
  constructor(private authenticationService: AuthenticationService, private router: Router) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (request.headers.has(HEADER_AUTHORIZATION)) {
      return this.handleToken(request, next);
    }else {
      return next.handle(request)
    }
  }

  private handleToken(request: HttpRequest<unknown>, next: HttpHandler) {
    //this.jwt = jwtDecode(this.authenticationService.currentUserValue.accessToken);

    const nowSecs = Date.now() / 1000;
    //const exp = this.jwt.exp || 0;

    if (true) {

      return next.handle(request);
    }else {
      return this.authenticationService.refreshToken().pipe(
        switchMap((response: User) => {
          this.authenticationService.setCurrentUser(response);

          return next.handle(request);
        }),
        catchError( err => {
          this.authenticationService.logOut();

          this.router.navigate(['/login']);


          return throwError(() => new Error(err));
        })
      );
    }
  }
}
