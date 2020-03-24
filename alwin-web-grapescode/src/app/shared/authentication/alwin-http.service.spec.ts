import {async, inject, TestBed} from '@angular/core/testing';
import {AlwinHttpService} from './alwin-http.service';
import {HttpHeaders, HttpRequest, HttpResponse} from '@angular/common/http';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {EnvironmentSpecificService} from '../environment-specific.service';

describe('Alwin HTTP Service', () => {

  const currentUserKey = 'currentUser';
  const token = 'Barer asdasewqesdfvfwerew';
  const storedUser = {username: 'Adam Mickiewicz', role: 'ADMIN', token};
  const testResponse = {firstField: 'test', secondField: 2};
  const testPath = 'test/api/path';
  const expectedHeaders = new HttpHeaders().append('Authorization', token);
  const expectedResponse = new HttpResponse({body: testResponse, headers: expectedHeaders});

  let subject: AlwinHttpService;

  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [AlwinHttpService, EnvironmentSpecificService]
  }));

  beforeEach(inject([AlwinHttpService], (alwinHttpService) => {
    subject = alwinHttpService;
    localStorage.clear();
  }));

  it('should create authorization header', async(() => {
    // given
    localStorage.setItem(currentUserKey, JSON.stringify(storedUser));

    // when
    const headers = subject.createAuthorizationHeader();

    // then
    expect(headers.get('Authorization')).toBe(token);
  }));

  it('should call HTTP get with token from local storage', (done) => {
    // given
    localStorage.setItem(currentUserKey, JSON.stringify(storedUser));
    const http = TestBed.get(HttpTestingController);

    // when
    subject.get(testPath).subscribe((response) => {

      // then
      expect(response).toEqual(expectedResponse);
      done();
    });

    http.expectOne((req: HttpRequest<any>) => {
      expect(req.url).toEqual(subject.alwinBaseApiUrl + testPath);
      expect(req.method).toEqual('GET');
      expect(req.headers.get('Authorization')).toEqual(token);
      return true;
    }, null).flush(expectedResponse);
  });

  it('should send HTTP post with token from local storage', (done) => {
    // given
    localStorage.setItem(currentUserKey, JSON.stringify(storedUser));
    const http = TestBed.get(HttpTestingController);

    // when
    subject.post(testPath, storedUser).subscribe((response) => {

      // then
      expect(response).toEqual(expectedResponse);
      done();
    });

    http.expectOne((req: HttpRequest<any>) => {
      expect(req.url).toEqual(subject.alwinBaseApiUrl + testPath);
      expect(req.method).toEqual('POST');
      expect(req.headers.get('Authorization')).toEqual(token);
      expect(JSON.stringify(req.body, null, 2)).toEqual(JSON.stringify(storedUser, null, 2));
      return true;
    }, null).flush(expectedResponse);
  });

});
