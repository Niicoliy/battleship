import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { DataService } from '../data.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.scss']
})
export class AboutComponent implements OnInit {

  goals: any;

  constructor(private route: ActivatedRoute, private router: Router, private _data: DataService, private http: HttpClient) {
    this.route.params.subscribe(res => console.log(res.id));
  }

  ngOnInit(): void   {
    this._data.goal.subscribe(res => this.goals = res);

    this.http.get('http://ubuntu4.saluton.dk:47714/rest/resources/game/game/games').subscribe(data => {
      console.log("Available games= " + data);
    });
    // this.http.post('http://ubuntu4.saluton.dk:47714/rest/resources/game/lobby?Username=s164911&Password=takRanders').subscribe(data => {
    //   console.log("Login success? " + data);
    //});
  }

  sendMeHome() {
    this.router.navigate(['']);
  }



}
