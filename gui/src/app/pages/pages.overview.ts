import {Routes} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {ShortenerComponent} from "./shortener/shortener.component";
import {ShortenerPopupComponent} from "./shortener/shortener-popup/shortener-popup.component";

export const page_routes: Routes = [
  {path: '', component: HomeComponent},
  {
    path: 'shortener', children: [
      {path: '', component: ShortenerComponent},
      {path: 'sub', component: ShortenerPopupComponent},
    ]
  }
]

export const primary_pages: PageOverview[] = [
  {path: 'shortener', title: 'URL-Shortener', auth: 'shortener'}
]

export interface PageOverview {
  path: string
  title: string
  auth: string
}
