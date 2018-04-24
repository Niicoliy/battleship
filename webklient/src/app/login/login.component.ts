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

  constructor(private _data: DataService) { }

  ngOnInit() {
    this._data.highscore.subscribe(res => this.highscores = res);
  }

  login() {
    this.goals.push(this.goalText);
    this.goalText = '';
    this.itemCount = this.goals.length;
    this._data.changeGoal(this.goals);
  }

}
