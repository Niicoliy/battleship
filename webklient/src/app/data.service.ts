import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

// Imports related to REST
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

// const related to REST
const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
  //headers: new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'})

};

@Injectable()
export class DataService {
  apiRoot: string = 'http://ubuntu4.saluton.dk:47714/rest/resources/game';


  private goals = new BehaviorSubject<any>(['Get this game finished', 'Make the highscore', 'Beat Mikkel!']);
  goal = this.goals.asObservable();

  private highscores = new BehaviorSubject<any>(['s164911 4 wins / 0 looses', 's164914 3 wins / 1 looses', 's153795 1 win / 4 looses']);
  highscore = this.highscores.asObservable();

  constructor(private http:HttpClient) { }

  changeGoal(goal) {
    this.goals.next(goal);
  }

  login(username, password) {
    console.log("POST login");
    let url = `${this.apiRoot}/lobby?Username=${username}&Password=${password}`;
    // const body = new HttpParams()
    //   .set('Username', username)
    //   .set('Password', password);

    //return this.http.post(url, body.toString(), httpOptions);
    return this.http.post(url, httpOptions);
    //return this.http.post(url, {Username:"s164914", Password:"robertsand"}, httpOptions);
  }

}
