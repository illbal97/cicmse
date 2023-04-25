import { Component, OnInit,  } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user.model';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent implements OnInit {
  private user: User = new User();
  constructor(private authenticationService: AuthenticationService, private router: Router) {
    this.authenticationService.currentUser.subscribe((user: User) => {
      this.user = user;
    })
  }
  ngOnInit() {
  }

  signOut() {
    this.authenticationService.logOut(this.user).subscribe(msg => {
      console.log(msg);
    });
    this.router.navigate(['/login']);
  }
}
