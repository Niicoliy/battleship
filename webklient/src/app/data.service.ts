import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
// Imports related to REST
import { HttpClient } from '@angular/common/http';
import {Router} from "@angular/router";

@Injectable()
export class DataService {
  apiRoot: string = 'http://ubuntu4.saluton.dk:47714/rest/resources/game';
  public returnData = '';

  private goals = new BehaviorSubject<any>(['Get this game finished', 'Make the highscore', 'Beat Mikkel!']);
  goal = this.goals.asObservable();

  private highscores = new BehaviorSubject<any>(['s164911 4 wins / 0 looses', 's164914 3 wins / 1 looses', 's153795 1 win / 4 looses']);
  highscore = this.highscores.asObservable();

  private games = new BehaviorSubject<any>(['s164911', 's164914']);
  game = this.games.asObservable();

  constructor(private http:HttpClient, private router:Router) { }

  changeGoal(goal) {
    this.goals.next(goal);
  }

  login(username, password) {
    console.log("POST login initiated");
    let url = `${this.apiRoot}/lobby?Username=${username}&Password=${password}`;
    this.http.post(url).subscribe(
      data => { this.returnData = data; },
      err => console.error(err),
      () => {
        console.log('Logging in succeeded? = ' + this.returnData);
        this.success(this.returnData);
      });
    return this.returnData;
  }

  success(response){
    if (response == true) {
      this.router.navigate(['start'])
    }};
}
