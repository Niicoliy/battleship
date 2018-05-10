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

  yourBoard: number[] = [];
  yourRows =     [[0,0,0,0,0,0,0,0,0,0],
                  [0,1,0,0,0,0,0,0,0,0],
                  [0,0,0,0,0,0,0,0,0,0],
                  [0,0,2,0,0,0,0,0,0,0],
                  [0,0,0,0,0,0,0,1,0,0],
                  [0,0,3,0,4,0,0,1,0,0],
                  [0,0,3,0,4,0,0,1,0,0],
                  [0,0,3,0,0,0,0,0,0,0],
                  [0,0,0,0,0,0,0,0,0,0],
                  [0,0,0,0,0,0,0,0,0,0]];
  opponentBoard: number[] = [];
  opponentRows = [[0,0,0,0,0,0,0,0,0,0],
                  [0,0,0,0,0,0,0,0,0,0],
                  [0,0,0,0,0,0,0,0,0,0],
                  [0,0,0,0,0,0,0,0,0,0],
                  [0,0,0,1,1,1,1,0,0,0],
                  [0,0,0,0,0,0,0,0,0,0],
                  [0,0,0,0,0,0,0,0,0,0],
                  [0,0,0,0,0,0,0,0,0,0],
                  [0,0,0,0,0,0,0,0,0,0],
                  [0,0,0,0,0,0,0,0,0,0]];

  constructor() {
    this.yourBoard = this.yourRows;
    this.opponentBoard = this.opponentRows;
  }

  ngOnInit() {

  }

  placeShip(i,j) {
    this.placeText = 'You placed a ship! ' + i + ',' + j;
    setTimeout(()=>{ this.placeText = ''; }, 1000)

  }

  shoot(i,j) {
    this.shootText = 'You fired a shot! ' + i + ',' + j;
    setTimeout(()=>{ this.shootText = ''; }, 1000)
  }

  decorateCell(cell) {
    switch (cell) {
      case 0:
        return '#57B3F1';
      case 1:
        return '#2885C4';
      case 2:
        return '#2E9CE6';
      case 3:
        return '#ff0000';
      case 4:
        return '#00ff00';
    }
  }

}
