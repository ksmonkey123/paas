import {Routes} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {ShortenerComponent} from "./shortener/shortener.component";
import {ShortenerService} from "./shortener/shortener.service";

export const page_routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'shortener', component: ShortenerComponent, providers: [ShortenerService]},
]

export const primary_pages: PageOverview[] = [
  {path: '/shortener', title: 'URL-Shortener', auth: 'shortener'},
]

export interface PageOverview {
  path: string
  title: string
  auth: string
}
