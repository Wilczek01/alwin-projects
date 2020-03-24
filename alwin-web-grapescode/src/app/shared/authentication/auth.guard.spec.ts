import {async, inject, TestBed} from '@angular/core/testing';
import {AuthGuard} from './auth.guard';
import {ActivatedRouteSnapshot, Router} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {NotFoundComponent} from '../../not-found/not-found.component';
import {RoleType} from '../../issues/shared/role-type';

describe('AuthGuard', () => {

  const currentUserKey = 'currentUser';
  const storedUserAdmin = {username: 'Adam Mickiewicz', role: RoleType.ADMIN, token: 'Barer asdasewqesdfvfwerew'};
  const storedNotSupportedRole = {username: 'Jan Kowalski', role: 'not_supported', token: 'Barer asdasewqesdfvfwereg'};
  let subject;
  let router;
  let snapshot;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([
        {path: 'login', component: NotFoundComponent}
      ])],
      providers: [AuthGuard],
      declarations: [NotFoundComponent]
    });
  });

  beforeEach(inject([AuthGuard, Router], (authGuard: AuthGuard, _router: Router) => {
    router = _router;
    subject = authGuard;
    localStorage.removeItem(currentUserKey);
    snapshot = new ActivatedRouteSnapshot();
  }));

  it('should not activate if user is not in the storage and redirect to login', async(() => {
    // given
    const spyNavigation = spyOn(router, 'navigate');

    // when
    const canActivate = subject.canActivate(null, null);

    // then
    expect(spyNavigation).toHaveBeenCalled();
    expect(spyNavigation).toHaveBeenCalledWith(['/login']);
    expect(canActivate).toBeFalsy();
  }));

  it('should activate if user is in the storage', async(() => {
    // given
    localStorage.setItem(currentUserKey, JSON.stringify(storedUserAdmin));
    snapshot = {data: {}};

    // when
    const canActivate = subject.canActivate(snapshot, localStorage);

    // then
    expect(canActivate).toBeTruthy();
  }));

  it('should activate if user role match', async(() => {
    // given
    localStorage.setItem(currentUserKey, JSON.stringify(storedUserAdmin));
    snapshot = {data: {roles: ['ADMIN', 'Test1']}};

    // when
    const canActivate = subject.canActivate(snapshot, localStorage);

    // then
    expect(canActivate).toBeTruthy();
  }));

  it('should not activate if user role not match', async(() => {
    // given
    localStorage.setItem(currentUserKey, JSON.stringify(storedNotSupportedRole));
    snapshot = {data: {roles: ['Test1']}};

    // when
    const canActivate = subject.canActivate(snapshot, localStorage);

    // then
    expect(canActivate).toBeFalsy();
  }));

});
