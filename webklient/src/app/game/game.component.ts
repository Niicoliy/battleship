import { Component, OnInit } from '@angular/core';
import { animate, keyframes, query, stagger, style, transition, trigger } from "@angular/animations";
//import { DataService } from "../data.service";
//import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss'],
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

export class GameComponent implements OnInit {

  placeText: string = "";
  shootText: string = "";

  // yourBoard: [][];
  // opponentBoard: [][];

  yourBoard: number[10][10] =    [[0,0,0,0,0,0,0,0,0,0],
                                  [0,1,0,0,0,0,0,0,0,0],
                                  [0,0,0,0,0,0,0,0,0,0],
                                  [0,0,0,0,0,0,0,0,0,0],
                                  [0,0,0,0,0,0,0,1,0,0],
                                  [0,0,0,0,0,0,0,1,0,0],
                                  [0,0,0,0,0,0,0,1,0,0],
                                  [0,0,0,0,0,0,0,0,0,0],
                                  [0,0,0,0,0,0,0,0,0,0],
                                  [0,0,0,0,0,0,0,0,0,0]];
  opponentBoard: number[10][10]= [[0,0,0,0,0,0,0,0,0,0],
                                  [0,0,0,0,0,0,0,0,0,0],
                                  [0,0,0,0,0,0,0,0,0,0],
                                  [0,0,0,0,0,0,0,0,0,0],
                                  [0,0,0,1,1,1,1,0,0,0],
                                  [0,0,0,0,0,0,0,0,0,0],
                                  [0,0,0,0,0,0,0,0,0,0],
                                  [0,0,0,0,0,0,0,0,0,0],
                                  [0,0,0,0,0,0,0,0,0,0],
                                  [0,0,0,0,0,0,0,0,0,0]];

  constructor() { }

  ngOnInit() {

  }

  placeShip(i) {
    this.placeText = 'You placed a ship! ' + i;
    setTimeout(()=>{ this.placeText = ''; }, 1000)

  }

  shoot(i) {
    this.shootText = 'You fired a shot! ' + i;
    setTimeout(()=>{ this.shootText = ''; }, 1000)
  }

}
