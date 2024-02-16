import {Component, OnInit} from '@angular/core';
import {Account, UserManagementService} from "../user-management.service";
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {MatCard, MatCardContent} from "@angular/material/card";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef, MatHeaderRow, MatHeaderRowDef,
  MatRow, MatRowDef,
  MatTable
} from "@angular/material/table";
import {MatSlideToggle} from "@angular/material/slide-toggle";
import {FormBuilder, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AuthService} from "../../../common/auth.service";
import {MatChip, MatChipOption, MatChipSet} from "@angular/material/chips";
import {ActivatedRoute, Router} from "@angular/router";
import {MatAnchor, MatButton, MatFabButton, MatIconButton, MatMiniFabButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatTooltip} from "@angular/material/tooltip";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-user-management',
  standalone: true,
  imports: [
    AsyncPipe,
    NgForOf,
    MatCard,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatCell,
    MatCellDef,
    MatRow,
    MatRowDef,
    MatHeaderRow,
    MatHeaderRowDef,
    MatCardContent,
    MatSlideToggle,
    FormsModule,
    MatChip,
    MatChipSet,
    MatChipOption,
    NgIf,
    MatIconButton,
    MatIcon,
    MatFabButton,
    MatMiniFabButton,
    MatTooltip,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    MatButton,
    MatAnchor
  ],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.scss'
})
export class UserListComponent implements OnInit {

  displayedColumns = ['username', 'enabled']
  list$

  showingForm = false

  constructor(public svc: UserManagementService,
              public auth: AuthService,
              public router: Router,
              public route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private toastr: ToastrService) {
    this.list$ = svc.accountList$
  }

  form = this.formBuilder.group({
    username: '',
    password: ''
  })

  ngOnInit() {
    this.svc.loadList()
  }


  enableUser(username: string, enabled: boolean) {
    console.log("setting " + username + " enabled " + enabled)
    this.svc.setUserEnabled(username, enabled).subscribe(
      {
        next: () => {
          this.svc.loadList()
          this.toastr.success("user " + username + " " + (enabled ? "enabled" : "disabled"))
        }, error: (error) => {
          this.toastr.error(error?.error?.message, "could not edit user")
        }
      }
    )
  }

  selectUser(user: Account) {
    console.log(user)
    this.router.navigate([user.username], {relativeTo: this.route})
  }

  showForm() {
    this.showingForm = true;
  }

  hideForm() {
    this.showingForm = false;
  }

  createUser() {
    this.svc.createUser(this.form.value.username!!, this.form.value.password!!).subscribe(
      {
        next: () => {
          this.form.reset()
          this.showingForm = false
          this.svc.loadList()
          this.toastr.success("user created")
        },
        error: (error) => {
          this.toastr.error(error?.error?.message, "could not create user")
        }
      }
    )
  }

}
