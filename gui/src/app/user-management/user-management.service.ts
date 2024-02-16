import {Injectable, OnDestroy} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, Observable, Subject, takeUntil} from "rxjs";

@Injectable()
export class UserManagementService implements OnDestroy {

  URLS = {
    list: 'rest/auth/accounts',
    create: 'rest/auth/accounts/',
    edit: 'rest/auth/accounts/',
  }

  constructor(private http: HttpClient) {
    console.log("userManagementService hi")
  }

  public accountList$ = new BehaviorSubject<Account[]>([])
  private closer$ = new Subject<void>()

  ngOnDestroy() {
    this.closer$.next()
    this.closer$.complete()
    this.accountList$.complete()
    console.log("userManagementService bye")
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

  setUserEnabled(username: string, enabled: boolean) {
    return this.http.patch(this.URLS.edit + username, {
      enabled: enabled
    })
  }
}

export interface Account {
  username: string
  admin: boolean
  enabled: boolean
}
