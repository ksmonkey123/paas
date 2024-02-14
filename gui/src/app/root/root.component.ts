import {Component, OnInit} from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {MatToolbar} from "@angular/material/toolbar";
import {Router, RouterLink, RouterOutlet} from "@angular/router";
import {AuthService, User} from "../common/auth.service";
import {Observable} from "rxjs";
import {AsyncPipe, NgIf} from "@angular/common";
import {MatCard, MatCardContent} from "@angular/material/card";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    MatIcon,
    MatIconButton,
    MatMenu,
    MatMenuItem,
    MatToolbar,
    RouterOutlet,
    MatMenuTrigger,
    AsyncPipe,
    NgIf,
    RouterLink,
    MatCard,
    MatCardContent
  ],
  templateUrl: './root.component.html',
  styleUrl: './root.component.scss'
})
export class RootComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) {
  }

  public userInfo$ ?: Observable<User>

  ngOnInit() {
    this.userInfo$ = this.authService.getUserInfo()
  }

  onLogout() {
    this.authService.fullLogout(() => this.router.navigate(['/login']))
  }
}
