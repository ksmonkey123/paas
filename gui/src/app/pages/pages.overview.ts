import {Routes} from "@angular/router";
import {HomeComponent} from "./home/home.component";

export const page_routes: Routes = [
  {path: '', component: HomeComponent},
]

export const primary_pages: PageOverview[] = [
]

export interface PageOverview {
  path: string
  title: string
  auth: string
}
