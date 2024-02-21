import {Component, signal} from '@angular/core';
import {UserManagementService} from "../user-management.service";
import {MatCard, MatCardContent} from "@angular/material/card";
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {RoleManagementService} from "../../role-management/role-management.service";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormBuilder, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatButton} from "@angular/material/button";
import {MatSlideToggle} from "@angular/material/slide-toggle";
import {AuthService} from "../../../common/auth.service";
import {MatIcon} from "@angular/material/icon";
import {
  MatAccordion,
  MatExpansionPanel,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from "@angular/material/expansion";

@Component({
  selector: 'app-user-details-management',
  standalone: true,
  imports: [
    MatCard,
    MatCardContent,
    AsyncPipe,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    MatButton,
    MatSlideToggle,
    NgForOf,
    MatIcon,
    FormsModule,
    RouterLink,
    MatAccordion,
    MatExpansionPanel,
    MatExpansionPanelHeader,
    MatExpansionPanelTitle,
    NgIf
  ],
  providers: [RoleManagementService],
  templateUrl: './user-details.component.html',
  styleUrl: './user-details.component.scss'
})
export class UserDetailsComponent {

  name ?: string
  userDetails$
  roleList$
  authInfo$

  pwForm = this.formBuilder.group({
    password: '',
  })

  constructor(
    private svc: UserManagementService,
    private formBuilder: FormBuilder,
    private roleService: RoleManagementService,
    route: ActivatedRoute,
    auth: AuthService,
  ) {
    this.roleService.loadList()
    this.userDetails$ = svc.userDetails$
    this.roleList$ = roleService.roleList$
    this.authInfo$ = auth.authInfo$
    route.paramMap.subscribe(params => {
      this.name = params.get('username')!
      this.svc.loadUser(this.name)
    })
  }

  toggleRole(role: string) {
    const roleToToggle = this.roleList$.getValue().find(r => r.name === role)!
    this.svc.setRoleStateForUser(this.name!, role, !this.userDetails$.getValue()?.roles?.includes(role))
  }

  toggleAdmin() {
    const user = this.userDetails$.value!
    this.svc.setBasicsForUser(user.username, undefined, !user.admin)
  }

  toggleEnabled() {
    const user = this.userDetails$.value!
    this.svc.setBasicsForUser(user.username, !user.enabled, undefined)
  }

  resetPassword() {
    const user = this.userDetails$.value!
    this.svc.resetPassword(user.username, this.pwForm.value.password!)
  }

}
