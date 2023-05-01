import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  subscriptionUser: any;
  user: User = new User();


  constructor(private router: Router, private authenticationService: AuthenticationService) {

  }

  ngOnInit() {
    this.subscriptionUser = this.authenticationService.currentUser.subscribe((data: User) => {
      this.user = data;
    });
  }

  signOut() {
    this.authenticationService.logOut(this.user).subscribe(msg => {
      // console.log(msg);
    })
    this.router.navigate(['/login']);
  }

}
