import {async, TestBed} from '@angular/core/testing';
import {AuthenticationService} from './authentication.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {HttpHeaders} from '@angular/common/http';
import {EnvironmentSpecificService} from '../environment-specific.service';
import {MenuService} from './menu.service';

describe('Authentication Service', () => {

  const currentUserKey = 'currentUser';
  const login = 'amickiewicz';
  const password = 'test';
  const userName = 'Adam Mickiewicz';
  const role = 'ADMIN';
  const token = 'Barer asdasewqesdfvfwerew';
  const id = '1';
  const typeName = 'ADMIN';
  const typeLabel = 'Administrator systemu';
  const loginResponse = {username: userName, role: {id, typeName, typeLabel}};
  const storedUser = {username: userName, role, roleLabel: typeLabel, token};
  const headers = new HttpHeaders({'Content-Type': 'application/json', 'Authorization': token});

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        AuthenticationService,
        EnvironmentSpecificService,
        MenuService
      ]
    });
    localStorage.clear();
  });

  it('should clear storage and token when logged out', async(() => {
    // given
    const authenticationService = TestBed.get(AuthenticationService);
    localStorage.setItem(currentUserKey, JSON.stringify(storedUser));
    authenticationService.token = 'Barer asdasewqesdfvfwerew';

    // when
    authenticationService.logout();

    // then
    expect(localStorage.getItem(currentUserKey)).toBe(null);
    expect(authenticationService.token).toBe(null);
  }));

  it('should log in and set user in storage', () => {
    // given
    const authenticationService = TestBed.get(AuthenticationService);
    const http = TestBed.get(HttpTestingController);

    // when
    let result = null;
    authenticationService.login(login, password).subscribe((response) => {
      result = response;
    });

    // then
    http.expectOne(authenticationService.alwinBaseApiUrl + 'users/login').flush(loginResponse, {headers});
    expect(localStorage.getItem(currentUserKey)).toBe(JSON.stringify(storedUser));
    expect(authenticationService.token).toBe(token);
    expect(result).toEqual(true);
  });

  it('should not log in if Authorization header is not present', () => {
    // given
    const authenticationService = TestBed.get(AuthenticationService);
    const http = TestBed.get(HttpTestingController);

    // when
    let result = null;
    authenticationService.login(login, password).subscribe((response) => {
      result = response;
    });

    // then
    http.expectOne(authenticationService.alwinBaseApiUrl + 'users/login').flush(loginResponse, {headers: new HttpHeaders()});
    expect(localStorage.getItem(currentUserKey)).toBe(null);
    expect(authenticationService.token).toBe(null);
    expect(result).toEqual(false);
  });

});
