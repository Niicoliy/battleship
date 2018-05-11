import { Component, OnInit } from '@angular/core';
import { animate, keyframes, query, stagger, style, transition, trigger } from "@angular/animations";
import { DataService } from "../data.service";
import { Observable } from 'rxjs/Observable';



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
  public loginSuccess;

  constructor(private _data: DataService) { }
  //constructor(private http: HttpClient) { }

  ngOnInit() {
    this._data.highscore.subscribe(res => this.highscores = res);
  }

  login() {
    this._data.login(this.loginUsername, this.loginPassword).subscribe(
      data => { this.loginSuccess = data},
      err => console.error(err),
      () => console.log('Done logging in')
    );
    this.loginText = 'Did it work?' + this.loginSuccess;
  }

  // loginClick() {
  //   this.btnText = 'Wait...';
  //   this.loginText = 'Contacting authentification server...';
  //   const body = new HttpParams()
  //     .set('Username', this.loginUsername)
  //     .set('Password', this.loginPassword);
  //
  //    return this.http.post('http://ubuntu4.saluton.dk:47714/rest/resources/game/lobby', this.loginUsername, this.loginPassword);
  //      //? this.loginText = 'Login success!' : this.loginText = 'Error!';
  //     setTimeout(()=>{ this.loginText = 'YAY! you logged in!'; }, 500);
  //
  //   this.btnText = 'Login';
  // }
    //   body.toString(),
    //   {
    //     headers: new HttpHeaders()
    //       .set('Content-Type', 'application/x-www-form-urlencoded')
    //   }
    // ).map((promise: Promise<JSON>) => {
    //     const token = promise;
    //     // console.log(token);
    //     localStorage.setItem('token', JSON.stringify(token));
    // });
  //}

}
