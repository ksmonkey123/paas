import {Injectable, OnDestroy} from '@angular/core';
import {BehaviorSubject, map, Observable, Subject, takeUntil} from "rxjs";
import { HttpClient } from "@angular/common/http";

export const TOKEN_NAME = "auth_token"

export interface AuthInfo {
  id: number
  username: string
  admin: boolean
  roles: string[]
}

@Injectable({
  providedIn: 'root'
})
export class AuthService implements OnDestroy {

  public authInfo$ = new BehaviorSubject<AuthInfo | null>(null)
  private closer$ = new Subject<void>()

  constructor(private http: HttpClient) {
  }

  hasToken() {
    return sessionStorage.getItem(TOKEN_NAME) !== null
  }

  getToken() {
    return sessionStorage.getItem(TOKEN_NAME)
  }

  setToken(token: string | null) {
    if (token === null) {
      sessionStorage.removeItem(TOKEN_NAME)
    } else {
      sessionStorage.setItem(TOKEN_NAME, token)
    }
  }

  public fetch() {
    this.http.get<AuthInfo>('rest/auth/authenticate')
      .pipe(takeUntil(this.closer$))
      .subscribe({
        next: (user) => {
          this.authInfo$.next(user)
        }
      })
  }

  ngOnDestroy() {
    this.authInfo$.complete()
    this.closer$.next()
    this.closer$.complete()
  }

  login(username: string, password: string): Observable<boolean> {
    return this.http.post('rest/auth/login', {
      username: username,
      password: password
    })
      .pipe(
        map((response: any) => {
          this.setToken(response.token)
          this.fetch()
          return true
        }))
  }

  changePassword(oldPassword: string, newPassword: string) {
    return this.http.patch<void>('rest/auth/account/password', {
      oldPassword: oldPassword,
      newPassword: newPassword
    })
  }

  fullLogout(callback: () => void) {
    this.http.post('rest/auth/logout', null).subscribe(
      {
        next: () => {
          this.logout()
          callback()
        },
        error: () => {
          this.logout()
          callback()
        }
      })
  }

  logout(): void {
    this.setToken(null)
    this.authInfo$.next(null)
  }

}
