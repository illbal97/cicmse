import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user.model';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  Login: FormGroup;
  user: User = new User();

  constructor(private formBuilder: FormBuilder, private router: Router,
     private authenticationService: AuthenticationService) {
    this.Login = formBuilder.group({
      usernameCtrl: ['', Validators.maxLength(50)],
      passwordCtrl: ['', Validators.maxLength(200)]
    });
   }

  ngOnInit(): void {
  }


  login() {
    this.authenticationService.login(this.Login.value.usernameCtrl, this.Login.value.passwordCtrl).subscribe((data:User) => {
      this.user = data;
      this.router.navigate(["/welcome"]);
    });
  }
}
