import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {IDataRow} from "app/shared/model/data-row.model";
import {map} from 'rxjs/operators';

import {SERVER_API_URL} from "app/app.constants";

type EntityResponseType = HttpResponse<IDataRow>
type EntityArrayResponseType = HttpResponse<IDataRow[]>

@Injectable({providedIn: 'root'})
export class DataRowService {
  public resourceUrl = SERVER_API_URL + 'api/data';

  constructor(protected http: HttpClient) {
  }

  query(): any {
    return this.http.get<IDataRow[]>(this.resourceUrl, { observe: 'response'})
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dataRow: any) => {
        dataRow.deliveryDate = dataRow.deliveryDate != null ? dataRow.deliveryDate.split("T")[0] : null;
      });
    }
    return res;
  }
}
