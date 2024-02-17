import {Routes} from "@angular/router";
import {AccountSettingsComponent} from "./account-settings/account-settings.component";
import {UserManagementService} from "./user-management/user-management.service";
import {RoleManagementService} from "./role-management/role-management.service";
import {UserListComponent} from "./user-management/user-list/user-list.component";
import {UserDetailsComponent} from "./user-management/user-details/user-details.component";
import {RoleManagementComponent} from "./role-management/role-management.component";

export const settings_routes: Routes = [
  {path: 'account', component: AccountSettingsComponent},
  {
    path: 'users', providers: [UserManagementService, RoleManagementService], children: [
      {path: '', component: UserListComponent},
      {path: ':username', component: UserDetailsComponent},
    ]
  },
  {path: 'roles', component: RoleManagementComponent},
]
