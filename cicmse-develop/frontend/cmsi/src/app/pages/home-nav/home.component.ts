import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {


  constructor(private router: Router, private authenticationService: AuthenticationService) {

  }

  ngOnInit() {
  }

  signOut() {
    this.authenticationService.logOut()
    this.router.navigate(['/login']);
  }

}
