import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Injectable()
export class DataService {

  private goals = new BehaviorSubject<any>(['Get this game finished', 'Make the highscore', 'Beat Mikkel!']);
  goal = this.goals.asObservable();

  private highscores = new BehaviorSubject<any>(['s164911 4 wins / 0 looses', 's164914 3 wins / 1 looses', 's153795 1 win / 4 looses']);
  highscore = this.highscores.asObservable();

  constructor() { }

  changeGoal(goal) {
    this.goals.next(goal);
  }
}
