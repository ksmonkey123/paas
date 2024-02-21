import {Injectable, OnDestroy} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, Observable, Subject, take, takeUntil} from "rxjs";
import {ToastrService} from "ngx-toastr";

@Injectable()
export class UserManagementService implements OnDestroy {

  URLS = {
    list: 'rest/auth/accounts',
    create: 'rest/auth/accounts/',
    edit: 'rest/auth/accounts/',
  }

  constructor(private http: HttpClient, private toastr: ToastrService) {
  }

  public accountList$ = new BehaviorSubject<Account[]>([])
  public userDetails$ = new BehaviorSubject<AccountDetails | undefined>(undefined)


  private closer$ = new Subject<void>()

  ngOnDestroy() {
    this.closer$.next()
    this.closer$.complete()
    this.userDetails$.complete()
    this.accountList$.complete()
  }

  loadList() {
    this.http.get<Account[]>(this.URLS.list)
      .pipe(takeUntil(this.closer$))
      .subscribe((x) => this.accountList$.next(x))
  }

  createUser(username: string, password: string) {
    return this.http.put(this.URLS.create + username, {
      username: username,
      password: password
    })
  }

  loadUser(username: string) {
    this.http.get<AccountDetails>('/rest/auth/accounts/' + username)
      .pipe(takeUntil(this.closer$))
      .subscribe((account) => this.userDetails$.next(account))
  }

  setUserEnabled(username: string, enabled: boolean) {
    return this.http.patch(this.URLS.edit + username, {
      enabled: enabled
    })
  }

  setBasicsForUser(username: string, enabled?: boolean, admin?: boolean) {
    this.http.patch<Account>('/rest/auth/accounts/' + username, {
      enabled: enabled,
      admin: admin
    })
      .pipe(takeUntil(this.closer$))
      .subscribe({
          next: _ => this.loadUser(username),
          error: error => {
            this.toastr.error(error?.error?.message, "could not update user")
            this.loadUser(username)
          }
        }
      )
  }

  setRoleStateForUser(username: string, role: string, newState: boolean) {
    this.http.patch<AccountDetails>('/rest/auth/accounts/' + username + '/roles', {
      add: newState ? [role] : null,
      remove: newState ? null : [role]
    })
      .pipe(takeUntil(this.closer$))
      .subscribe({
        next: (acc) => {
          this.userDetails$.next(acc)
        },
        error: (error) => {
          this.toastr.error(error?.error?.message, "could not create user")
          this.loadUser(username)
        }
      })
  }

  resetPassword(username: string, password: string) {
    this.http.patch<Account>('/rest/auth/accounts/' + username, {password: password})
      .pipe(takeUntil(this.closer$))
      .subscribe({
          next: _ => {
            this.toastr.success("password updated")
          },
          error: error => {
            this.toastr.error(error?.error?.message, "could not update password")
          }
        }
      )
  }

}

export interface Account {
  username: string
  admin: boolean
  enabled: boolean
}

export interface AccountDetails {
  username: string
  admin: boolean
  enabled: boolean
  roles: string[]
}
