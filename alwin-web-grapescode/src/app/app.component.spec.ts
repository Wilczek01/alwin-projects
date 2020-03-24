import {async, inject, TestBed} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {Router} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {NotFoundComponent} from './not-found/not-found.component';
import {UserAccessService} from './common/user-access.service';
import {VersionService} from './version/version.service';
import {HttpClientModule} from '@angular/common/http';
import {EnvironmentSpecificService} from './shared/environment-specific.service';
import {MessageService} from './shared/message.service';
import {AlwinMatModule} from './alwin-mat.module';
import {MenuService} from './shared/authentication/menu.service';
import {ChangeDetectorRef} from '@angular/core';

describe('AppComponent', () => {

  const currentUserKey = 'currentUser';
  const storedUser = {username: 'Adam Mickiewicz', role: 'ADMIN', token: 'Barer asdasewqesdfvfwerew'};
  let subject;
  let router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([
        {path: 'test', component: NotFoundComponent}
      ]), HttpClientModule, AlwinMatModule],
      providers: [AppComponent, UserAccessService, VersionService, EnvironmentSpecificService, MessageService, MenuService, ChangeDetectorRef],
      declarations: [NotFoundComponent]
    });
  });

  beforeEach(inject([AppComponent, Router], (appComponent: AppComponent, _router: Router) => {
    router = _router;
    subject = appComponent;
    localStorage.removeItem(currentUserKey);
  }));

  it('should return that user is not logged in absent in local storage', async(() => {
    // when
    const loggedIn = subject.isLoggedIn();

    // then
    expect(loggedIn).toBeFalsy();
  }));

  it('should return that user is logged in if present in local storage', async(() => {
    // given
    localStorage.setItem(currentUserKey, JSON.stringify(storedUser));

    // when
    const loggedIn = subject.isLoggedIn();

    // then
    expect(loggedIn).toBeTruthy();
  }));
});
