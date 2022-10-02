import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent implements OnInit {
  constructor(private authenticationService: AuthenticationService, private router: Router) {

  }
  ngOnInit() {
  }

  signOut() {
    this.authenticationService.logOut()
    this.router.navigate(['/login']);
  }
}
