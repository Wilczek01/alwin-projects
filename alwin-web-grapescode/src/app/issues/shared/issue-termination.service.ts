import {AlwinHttpService} from '../../shared/authentication/alwin-http.service';
import {Injectable} from '@angular/core';
import {IssueTerminationRequest} from './terminate-issue-request';
import {Observable} from 'rxjs';
import {IssueTermination} from './issue-termination';

@Injectable()
export class IssueTerminationService {

  constructor(private http: AlwinHttpService) {
  }

  /**
   * Przedterminowe zakończenie zlecenia windykacyjnego
   *
   * @param issueId - identyfikator zlecenia
   * @param issueTerminationRequest - dane do zamknięcia zlecenia
   * @return komunikat: zlecenie zakończone lub żądanie o przedterminowe zakończenie zlecenia zostało utowrzone
   */
  terminateIssue(issueId: number, issueTerminationRequest: IssueTerminationRequest): Observable<string> {
    return this.http.post(`issues/terminate/${issueId}`, issueTerminationRequest);
  }

  /**
   * Pobieranie otwartego żądania o przedterminowe zakończenie zlecenia
   *
   * @param issueId - identyfikator zlecenia
   * @return żądanie o przedterminowe zakończenie zlecenia
   */
  findTerminationRequest(issueId: number): Observable<IssueTermination> {
    return this.http.get(`issues/termination/${issueId}`);
  }

  /**
   * Zaakceptowanie żądania o przedterminowe zakończenie zlecenia oraz zakończenie zlecenia
   *
   * @param terminationRequestId - identyfikator żądania o przedterminowe zakończenie zlecenia
   * @param terminationRequest   - dane do zaakcpetowania żądania
   * @return pusta odpowiedź ze statusem 200
   */
  acceptIssueTermination(terminationRequestId: number, terminationRequest: IssueTermination): any {
    return this.http.postObserve(`issues/termination/accept/${terminationRequestId}`, terminationRequest);
  }

  /**
   * Odrzucenie żądania o przedterminowe zakończenie zlecenia
   *
   * @param terminationRequestId - identyfikator żądania o przedterminowe zakończenie zlecenia
   * @param terminationRequest   - dane do odrzucenia żądania
   * @return pusta odpowiedź ze statusem 200
   */
  rejectIssueTermination(terminationRequestId: number, terminationRequest: IssueTermination): any {
    return this.http.postObserve(`issues/termination/reject/${terminationRequestId}`, terminationRequest);
  }
}
