import { Component, OnInit } from '@angular/core';
import { animate, keyframes, query, stagger, style, transition, trigger } from "@angular/animations";
import { DataService } from "../data.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss',],
  animations: [
    trigger('highscores', [
      transition('* => *', [
        query(':enter', style({ opacity: 0 }), {optional: true}),

        query(':enter', stagger('300ms', [
          animate('.6s ease-in', keyframes([
            style({opacity: 0, transform: 'translateY(-75%)', offset: 0}),
            style({opacity: .5, transform: 'translateY(35px)', offset: .3}),
            style({opacity: 1, transform: 'translateY(0)', offset: 1}),
          ]))]), {optional: true})
      ])
    ])
  ]
})

export class LoginComponent implements OnInit {
  btnText: string = 'Login';
  highscores = [];
  loginText: string = 'Waiting for login to be pressed...';
  loginUsername: string  = '';
  loginPassword: string = '';
  public loginSuccess = '';

  constructor(private _data: DataService) { }

  ngOnInit() {
    this._data.highscore.subscribe(res => this.highscores = res);
  }

  login() {
    this.loginText = 'Connecting to login server...';
    this.loginSuccess = this._data.login(this.loginUsername, this.loginPassword);
    console.log("Login success? " + this.loginSuccess);
// .subscribe(
    //   data => { this.loginSuccess = data},
    //   err => console.error(err),
    //   () => console.log('Done logging in')
    // );
//    this.loginText = ('Did it work?' + this.loginSuccess);
  }

}
