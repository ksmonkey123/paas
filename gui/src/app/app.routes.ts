import {Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {RootComponent} from "./root/root.component";
import {ManageAccountComponent} from "./manage-account/manage-account.component";

export const routes: Routes = [
  {
    path: '', component: RootComponent, children: [
      {path: '', component: HomeComponent},
      {path: 'account', component: ManageAccountComponent}
    ]
  },
  {path: 'login', component: LoginComponent},
  {path: '**', redirectTo: ''}
];
