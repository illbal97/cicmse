import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../model/user.model';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class HeaderService {
  protected user: User = new User();

constructor(protected http: HttpClient, protected authenticationService: AuthenticationService) {
  this.authenticationService.currentUser.subscribe((data:User) => {
    this.user = data;
  })
 }

 protected getHeader(): HttpHeaders{
  return new HttpHeaders(
    {
      authorization: 'Bearer ' + this.user?.accessToken,
      "Content-Type": "application/json; charset=UTF-8"
    }
  );
 }

}
