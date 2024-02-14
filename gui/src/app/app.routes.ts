import {Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {RootComponent} from "./root/root.component";
import {AccountSettingsComponent} from "./account-settings/account-settings.component";
import {UserManagementComponent} from "./user-management/user-management.component";
import {
  UserDetailsManagementComponent
} from "./user-management/user-details-management/user-details-management.component";

export const routes: Routes = [
  {
    path: '', component: RootComponent, children: [
      {path: '', component: HomeComponent},
      {path: 'account', component: AccountSettingsComponent},
      {path: 'users', component: UserManagementComponent},
      {path: 'users/:username', component: UserDetailsManagementComponent},
    ]
  },
  {path: 'login', component: LoginComponent},
  {path: '**', component: HomeComponent},
];
