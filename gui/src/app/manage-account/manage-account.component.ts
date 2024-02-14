import {Component} from '@angular/core';
import {MatCard, MatCardContent, MatCardHeader} from "@angular/material/card";
import {
  AbstractControl,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  ValidationErrors,
  Validators
} from "@angular/forms";
import {AuthService} from "../common/auth.service";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatIcon} from "@angular/material/icon";
import {MatButton} from "@angular/material/button";
import {NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-password-change',
  standalone: true,
  imports: [
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatFormField,
    ReactiveFormsModule,
    MatInput,
    MatLabel,
    MatIcon,
    MatButton,
    NgIf,
    RouterLink,
  ],
  templateUrl: './manage-account.component.html',
  styleUrl: './manage-account.component.scss'
})
export class ManageAccountComponent {

  constructor(private authService: AuthService) {
  }

  pwForm = new FormGroup({
    'oldPassword': new FormControl(null, [Validators.required]),
    'newPassword': new FormControl(null, [Validators.required]),
    'confirmPassword': new FormControl(null, [Validators.required, (control: AbstractControl): ValidationErrors | null => {
      if (control.value !== this.pwForm?.value.newPassword) {
        return {badConfirm: true}
      } else {
        return null
      }
    }]),
  })

  pwError ?: string
  pwSuccess = false

  onChangePassword() {
    this.pwError = undefined
    this.pwSuccess = false

    this.authService.changePassword(this.pwForm.value.oldPassword!, this.pwForm.value.newPassword!).subscribe(
      {
        next: () => {
          this.pwSuccess = true
        },
        error: (error) => {
          this.pwError = error?.error?.message || 'request failed'
        }
      }
    )
  }

}
