import { Component, OnInit } from '@angular/core';
import { trigger,style,transition,animate,keyframes,query,stagger } from '@angular/animations';
import { DataService } from '../data.service';
import {Router} from "@angular/router";


@Component({
  selector: 'app-start',
  templateUrl: './start.component.html',
  styleUrls: ['./start.component.scss'],
  animations: [
    trigger('games', [
      transition('* => *', [
        query(':enter', style({ opacity: 0 }), {optional: true}),

        query(':enter', stagger('300ms', [
          animate('.6s ease-in', keyframes([
            style({opacity: 0, transform: 'translateY(-75%)', offset: 0}),
            style({opacity: .5, transform: 'translateY(35px)', offset: .3}),
            style({opacity: 1, transform: 'translateY(0)', offset: 1}),
          ]))]), {optional: true}),

        query(':leave', stagger('300ms', [
          animate('.6s ease-in', keyframes([
            style({opacity: 1, transform: 'translateY(0)', offset: 0}),
            style({opacity: .5, transform: 'translateY(35px)', offset: .3}),
            style({opacity: 0, transform: 'translateY(-75%)', offset: 1}),
          ]))]), {optional: true})
      ])
    ])

  ]
})

export class StartComponent implements OnInit {

  itemCount: number;
  btnStartTxt: string = 'Start new game';
  games = [];

  constructor(private _data: DataService, private router:Router) { }

  ngOnInit() {
    this._data.game.subscribe(res => this.games = res);
    this.itemCount = this.games.length;
    this._data.changeGoal(this.games);
  }

  startGame(i) {
    this.router.navigate(['game']);
    // this.goals.splice(i, 1);
    // this._data.changeGoal(this.goals);
  }
}
