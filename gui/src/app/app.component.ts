import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, EventType, NavigationEnd, Router, RouterLink, RouterOutlet} from '@angular/router';
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {MatToolbar} from "@angular/material/toolbar";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatCard, MatCardContent} from "@angular/material/card";
import {AsyncPipe, NgIf} from "@angular/common";
import {AuthService} from "./common/auth.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, AsyncPipe, MatButton, MatIcon, MatIconButton, MatMenu, MatMenuItem, MatToolbar, NgIf, RouterLink, MatMenuTrigger],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {

  constructor(public auth: AuthService, private router: Router, private toastr: ToastrService) {
  }

  ngOnInit() {
    this.auth.fetch()
    // refresh auth info on every navigation event (and implicitly redirect to login page on bad auth)
    this.router.events.subscribe((e) => {
      if (e.type == EventType.NavigationEnd && (e as NavigationEnd).url !== '/login')
        this.auth.fetch()
    })
  }

  onLogout() {
    this.auth.fullLogout(() => {
      this.toastr.success("Logout Successful")
      this.router.navigate(['/login'])
    })
  }

}
