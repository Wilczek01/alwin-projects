import {Component, OnInit} from '@angular/core';
import {TerminationContractSubject} from '../model/termination-contract-subject';
import {ContractService} from '../../contract/contract.service';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {MessageService} from '../../shared/message.service';
import {TerminationContractFullSubject} from '../model/termination-contract-full-subject';
import {GpsInstallationChargeUtils} from './gps-installation-charge.utils';

/**
 * Komponent z widokiem listy przedmiotów na umowie do wypowiedzenia
 */
@Component({
  selector: 'alwin-new-contract-subjects-dialog',
  styleUrls: ['./new-contract-subjects-dialog.component.css'],
  templateUrl: './new-contract-subjects-dialog.component.html'
})
export class NewContractSubjectsDialogComponent implements OnInit {

  terminationSubjects: TerminationContractSubject[] = [];
  subjects: TerminationContractFullSubject[] = [];
  contractNo: string;
  loading = false;

  constructor(private contractService: ContractService, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.loading = true;
    this.contractService.getContractSubjects(this.contractNo).subscribe(
      subjects => {
        if (subjects == null) {
          this.loading = false;
          return;
        }
        this.terminationSubjects.forEach(terminationSubject => {
          const fullSubject = new TerminationContractFullSubject();
          fullSubject.terminationData = terminationSubject;
          const subjectData = subjects.filter(s => s.subjectId === terminationSubject.subjectId);
          if (subjectData.length !== 0) {
            fullSubject.subjectData = subjectData[0];
            this.subjects.push(fullSubject);
          }
        });
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      });
  }

  getGpsInstallationCharge(subject: TerminationContractSubject) {
    return GpsInstallationChargeUtils.getGpsInstallationCharge(subject);
  }

}
