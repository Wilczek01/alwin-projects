import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Page} from '../../shared/pagination/page';
import {AlwinHttpService} from '../../shared/authentication/alwin-http.service';
import {FullUser} from '../../user/full-user';
import {ParentOperator} from '../../operator/parent-operator';
import {HttpParams} from '@angular/common/http';

@Injectable()
export class UserService {

  constructor(private http: AlwinHttpService) {
  }

  getUsers(firstResult: string, maxResult: string): Observable<Page<FullUser>> {
    return this.getSelectedUsers('users', firstResult, maxResult, null);
  }

  private getSelectedUsers(path: string, firstResult: string, maxResult: string, searchParams: HttpParams) {
    let params: HttpParams = new HttpParams();
    params = params.append('firstResult', firstResult);
    params = params.append('maxResults', maxResult);
    if (searchParams != null) {
      searchParams.keys().forEach(key => {
        searchParams.getAll(key).forEach(param => {
          params = params.append(key, param);
        });
      });
    }
    return this.http.getWithQuery<Page<FullUser>>(path, params);
  }

  createUser(user: FullUser): any {
    return this.http.postObserve('users', user);
  }

  updateUser(user: FullUser): any {
    return this.http.patch(`users/${user.id}`, user);
  }

  getUser(userId: string): Observable<FullUser> {
    return this.http.get<FullUser>(`users/${userId}`);
  }

  getOperatorsFilteredByLoginOrName(firstResult: string, maxResult: string, searchText: string): Observable<Page<ParentOperator>> {
    let params = new HttpParams;
    if (searchText != null) {
      params = params.append('searchText', searchText);
    }
    params = params.append('firstResult', firstResult);
    params = params.append('maxResults', maxResult);
    return this.http.getWithQuery <Page<ParentOperator>>('operators', params);
  }
}
