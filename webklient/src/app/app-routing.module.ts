import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { StartComponent} from "./start/start.component";
import { GameComponent } from './game/game.component';
import { AboutComponent } from './about/about.component';



const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'start',
    component: StartComponent
  },
  {
    path: 'game',
    component: GameComponent
  },
  {
    path: 'about',
    component: AboutComponent
  },
  {
    path: '**',
    component: LoginComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}
