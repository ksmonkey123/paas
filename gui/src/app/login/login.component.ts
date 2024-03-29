import {Component, OnInit} from '@angular/core';
import {MatCard, MatCardContent} from "@angular/material/card";
import {FormBuilder, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {AuthService} from "../common/auth.service";
import {Router} from "@angular/router";
import {MatToolbar} from "@angular/material/toolbar";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatCard,
    MatCardContent,
    FormsModule,
    MatFormField,
    MatInput,
    MatLabel,
    MatButton,
    MatIcon,
    ReactiveFormsModule,
    MatToolbar,
    NgIf
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) {
  }

  problem = false

  form = this.formBuilder.group({
    username: '',
    password: '',
  })

  ngOnInit() {
    if (this.authService.hasToken()) {
      console.log('already logged in, redirecting to main page')
      this.router.navigateByUrl('/')
    }
  }

  onSubmit() {
    this.authService.login(this.form.value.username || '', this.form.value.password || '')
      .subscribe({
          next: (x) => {
            console.log("login done")
            this.router.navigateByUrl('/')
          },
          error: () => {
            this.problem = true
          }
        }
      )
  }


}
