import {Injectable} from '@angular/core';
import {map, Observable, of} from "rxjs";
import {HttpClient} from "@angular/common/http";

export const TOKEN_NAME = "auth_token"

export interface User {
  id: number
  username: string
  admin: boolean
  enabled: boolean
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  URLS = {
    login: 'rest/auth/login',
    logout: 'rest/auth/logout',
    userInfo: 'rest/auth/account',
    pwChange: 'rest/auth/account/password',
  }

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

  getUserInfo(): Observable<User> {
    return this.http.get<User>(this.URLS.userInfo)
  }

  login(username: string, password: string): Observable<boolean> {
    return this.http.post(this.URLS.login, {username: username, password: password})
      .pipe(
        map((response: any) => {
          this.setToken(response.token);
          return true;
        }));
  }

  changePassword(oldPassword: string, newPassword: string) {
    return this.http.patch<void>(this.URLS.pwChange, {oldPassword: oldPassword, newPassword: newPassword})
  }

  fullLogout(callback: () => void) {
    this.http.post(this.URLS.logout, null).subscribe(
      {
        next: () => {
          this.logout();
          callback();
        },
        error: () => {
          this.logout();
          callback();
        }
      });
  }

  logout(): void {
    this.setToken(null);
  }

}
