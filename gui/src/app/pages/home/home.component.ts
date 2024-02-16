import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../common/auth.service";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {

  constructor(public auth: AuthService) {
  }

  ngOnInit() {
    console.log("hi")
  }


}
