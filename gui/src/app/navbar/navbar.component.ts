import {Component} from '@angular/core';
import {AuthService} from "../common/auth.service";
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {AsyncPipe, NgIf} from "@angular/common";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {MatToolbar} from "@angular/material/toolbar";
import {primary_pages} from "../pages/pages.overview";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    AsyncPipe,
    MatButton,
    MatIcon,
    MatIconButton,
    MatMenu,
    MatMenuItem,
    MatToolbar,
    NgIf,
    RouterLink,
    RouterLinkActive,
    MatMenuTrigger
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {

  public navItems = primary_pages

  constructor(public auth: AuthService, private router: Router) {
  }

  onLogout() {
    this.auth.fullLogout(() => {
      this.router.navigate(['/login'])
    })
  }

}
