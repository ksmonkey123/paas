import {Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {settings_routes} from "./settings/settings.routes";
import {page_routes} from "./pages/pages.overview";

export const routes: Routes = [
  ...page_routes,
  ...settings_routes,
  {path: 'login', component: LoginComponent},
  {path: '**', redirectTo: ''},
];
