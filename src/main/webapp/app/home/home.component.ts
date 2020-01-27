import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';

import {LoginModalService} from 'app/core/login/login-modal.service';
import {AccountService} from 'app/core/auth/account.service';
import {Account} from 'app/core/user/account.model';
import {IDataRow} from "app/shared/model/data-row.model";
import {DataRowService} from "app/entities/DataRowService";
import {HttpResponse} from '@angular/common/http';

import {filter, map} from 'rxjs/operators';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;
  dataRows: IDataRow[];

  constructor(private accountService: AccountService, private loginModalService: LoginModalService, private dataRowService: DataRowService) {
    this.dataRows = [];
  }

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.dataRowService.query().pipe(
      filter((mayBeOk: HttpResponse<IDataRow[]>) => mayBeOk.ok),
      map((response: HttpResponse<IDataRow[]>) => response.body)
    ).subscribe((result: IDataRow[]) => {
      this.dataRows = result;
      // eslint-disable-next-line no-console
      console.log(result);
    })
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }
}
