import {Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./pages/home/home.component";
import {AccountSettingsComponent} from "./settings/account-settings/account-settings.component";
import {UserListComponent} from "./settings/user-management/user-list/user-list.component";
import {UserDetailsComponent} from "./settings/user-management/user-details/user-details.component";
import {UserManagementService} from "./settings/user-management/user-management.service";
import {RoleManagementService} from "./settings/role-management/role-management.service";
import {RoleManagementComponent} from "./settings/role-management/role-management.component";

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'account', component: AccountSettingsComponent},
  { // user management
    path: 'users', providers: [UserManagementService], children: [
      {path: '', component: UserListComponent},
      {path: ':username', component: UserDetailsComponent},
    ]
  },
  {path: 'roles', component: RoleManagementComponent},
  {path: 'login', component: LoginComponent},
  {path: '**', redirectTo: ''},
];
