import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import {HttpClient} from "@angular/common/http";
import { User} from '../model/user.model';


 const API_URL = environment.ROOT_URL + '/api/v1/authentication';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  public currentUser!: Observable<User>;
  private currentUserSubject: BehaviorSubject<User>;

  constructor(private http: HttpClient) {
    let storageUser;
    const strStorageUser = localStorage.getItem('currentUser');
    if (strStorageUser) {
      storageUser = JSON.parse(strStorageUser);
    }
    this.currentUserSubject = new BehaviorSubject<User>(storageUser);
    this.currentUser = this.currentUserSubject.asObservable();

   }

   public get currentUserValue(): User {
     return this.currentUserSubject.value;
   }

   login(username: string, password: string): Observable<User> {
     return this.http.post<User>(API_URL + '/login', {username, password}, { withCredentials: true }).pipe(
       map((response: User) => {
         if(response) {
            this.setCurrentUser(response);
         }
         return response;
       })
     );
   }

   setCurrentUser(user: User) {
      localStorage.setItem('currentUser', JSON.stringify(user));
      this.currentUserSubject.next(user);
   }

   refreshToken(): Observable<any> {
     return this.http.post(API_URL + '/refresh-token?userId='+ this.currentUserValue.id, {}, {withCredentials: true} );
   }

   logout(user: User) {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(new User);

    return this.http.post(API_URL + '/logOut', user, {withCredentials: true} );

   }
}
