import {ActivatedRoute} from '@angular/router';
import {Observable, of} from 'rxjs';

const currentUserKey = 'currentUser';
const storedUser = {username: 'Adam Mickiewicz', role: 'ADMIN', token: 'Barer asdasewqesdfvfwerew'};
localStorage.setItem(currentUserKey, JSON.stringify(storedUser));

export const ActivatedRouteMock = {
  snapshot: {data: {}},
  queryParams: of({})
} as ActivatedRoute;
