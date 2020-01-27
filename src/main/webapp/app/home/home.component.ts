import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';

import {LoginModalService} from 'app/core/login/login-modal.service';
import {AccountService} from 'app/core/auth/account.service';
import {Account} from 'app/core/user/account.model';
import {IDataRow} from "app/shared/model/data-row.model";
import {DataRowService} from "app/entities/DataRowService";
import {HttpClient, HttpResponse} from '@angular/common/http';

import {filter, map} from 'rxjs/operators';
import {CSV_SERVER_API_URL} from "app/app.constants";
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;
  dataRows: IDataRow[];
  resourceUrl = CSV_SERVER_API_URL + "api/csv";
  // @ts-ignore
  uploadForm: FormGroup;

  constructor(private accountService: AccountService, private loginModalService: LoginModalService,
              private dataRowService: DataRowService, private formBuilder: FormBuilder,  private httpClient: HttpClient) {
    this.dataRows = [];
  }

  ngOnInit(): void {
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.dataRowService.query().pipe(
      filter((mayBeOk: HttpResponse<IDataRow[]>) => mayBeOk.ok),
      map((response: HttpResponse<IDataRow[]>) => response.body)
    ).subscribe((result: IDataRow[]) => {
      this.dataRows = result;
      // eslint-disable-next-line no-console
      console.log(result);
    });

  }
  onFileSelect(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      // @ts-ignore
      this.uploadForm.get('profile').setValue(file);
    }
  }
  onSubmit() {
    const formData = new FormData();
    // @ts-ignore
    formData.append('file', this.uploadForm.get('profile').value);

    this.httpClient.post<any>(this.resourceUrl, formData).subscribe(
      // eslint-disable-next-line no-console
      (res) => location.reload(),
      // eslint-disable-next-line no-console
      (err) => location.reload()
  );
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
