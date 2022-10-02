import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from '../model/user.model';
import { AuthenticationService } from '../services/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  private currentUser: User = new User;

constructor(private authenticationService: AuthenticationService, private router: Router) {
  this.authenticationService.currentUser.subscribe((user: User) => {
    this.currentUser = user;
  });
}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if (this.currentUser) {
        return true;
      }
      this.router.navigate(['/login']);
      return false;
  }

}
