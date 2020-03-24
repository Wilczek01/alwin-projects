import {Component, OnInit} from '@angular/core';
import {MessageService} from '../shared/message.service';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';
import { MatDialog } from '@angular/material/dialog';
import {NewTermination} from './model/new-termination';
import {TerminationService} from './termination.service';
import {NewTerminationContract} from './model/new-termination-contract';
import {ProcessTerminations} from './model/process-terminations';
import {TerminationContractTypeConst} from './model/termination-contract-type-const';
import {DateUtils} from '../issues/shared/utils/date-utils';

/**
 * Komponent odpowiedzialny za widok wyświetlający formularz do wysłania nowych wypowiedzień umów
 */
@Component({
  selector: 'alwin-new-terminations',
  styleUrls: ['./new-terminations.component.css'],
  templateUrl: './new-terminations.component.html'
})
export class NewTerminationsComponent implements OnInit {

  terminations: NewTermination[] = [];
  loading: boolean;
  processing: boolean;

  constructor(private dialog: MatDialog, private terminationService: TerminationService, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.reloadTerminations();
    this.trackTerminationContractTypeChange();
  }

  private trackTerminationContractTypeChange() {
    this.terminationService.terminationContract$.subscribe(contract => {
      let terminationContract;
      let contractIndex;
      let terminationIndex;
      let found = false;
      for (let terminationNo = 0; terminationNo < this.terminations.length; terminationNo++) {
        for (let contractNo = 0; contractNo < this.terminations[terminationNo].contracts.length; contractNo++) {
          if (this.terminations[terminationNo].contracts[contractNo].terminationContract.contractTerminationId === contract.terminationContract.contractTerminationId) {
            terminationContract = contract.terminationContract;
            contractIndex = contractNo;
            terminationIndex = terminationNo;
            found = true;
            break;
          }
        }
        if (found) {
          break;
        }
      }

      if (found) {
        this.moveContractTerminationToAnotherGroup(terminationIndex, contract, contractIndex);
      }
      this.terminationService.refreshTerminationsSelection();
    });
  }

  private moveContractTerminationToAnotherGroup(terminationIndex, contract, contractIndex) {
    let alternativeGroupExists = false;
    for (let terminationNo = 0; terminationNo < this.terminations.length; terminationNo++) {
      const termination = this.terminations[terminationNo].termination;
      if (termination.extCompanyId === this.terminations[terminationIndex].termination.extCompanyId
        && termination.terminationDate === this.terminations[terminationIndex].termination.terminationDate
        && termination.type === contract.terminationContract.type) {
        alternativeGroupExists = true;
        this.terminations[terminationNo].contracts.push(this.terminations[terminationIndex].contracts[contractIndex]);
        break;
      }
    }

    if (!alternativeGroupExists) {
      this.moveContractTerminationToNewGroup(terminationIndex, contract);
    }

    this.terminations[terminationIndex].contracts.splice(contractIndex, 1);
    if (this.terminations[terminationIndex].contracts.length === 0) {
      this.terminations.splice(terminationIndex, 1);
    }
  }

  private moveContractTerminationToNewGroup(terminationIndex, contract) {
    const terminationCopy = Object.assign({}, this.terminations[terminationIndex]);
    terminationCopy.termination = Object.assign({}, this.terminations[terminationIndex].termination);
    terminationCopy.termination.type = contract.terminationContract.type;
    terminationCopy.contracts = [contract];
    terminationCopy.sendAll = contract.send;
    terminationCopy.postponeAll = contract.postpone;
    terminationCopy.rejectAll = contract.reject;
    this.terminations.push(terminationCopy);
  }

  /**
   * Odświeża tabelę sugestii wypowiedzeń
   */
  reloadTerminations() {
    this.loading = true;
    this.terminations = [];
    this.terminationService.findContractTerminationsToOperate().subscribe(
      terminations => {
        if (terminations == null || terminations.length === 0) {
          this.loading = false;
          return;
        }
        this.terminations = terminations.map(termination => {
          const newTermination = new NewTermination();
          newTermination.termination = termination;
          newTermination.contracts = termination.contracts.map(contract => {
            const newContract = new NewTerminationContract();
            newContract.terminationContract = contract;
            newContract.changeType = TerminationContractTypeConst.CONDITIONAL === contract.type;
            return newContract;
          });
          return newTermination;
        });
        this.loading = false;
      },
      err => {
        this.loading = false;
        HandlingErrorUtils.handleError(this.messageService, err);
      });
  }

  /**
   * Wysyła na serwer żądanie przeprocesowania wybranych wypowiedzeń do wysłania, odroczenia i do odrzucenia
   * Odświeża tabelę sugestii wypowiedzeń w przypadku powodzenia
   */
  processSelectedTerminations() {
    this.processing = true;
    const terminationsToProcess = this.buildTerminationsToProcess();
    if (this.terminationsAreInvalid(terminationsToProcess)) {
      this.processing = false;
      return;
    }
    this.terminationService.processTerminations(terminationsToProcess).subscribe(
      response => {
        if (response.status === 204) {
          this.messageService.showMessage('Wypowiedzenia są w trakcie procesowania');
        } else {
          this.messageService.showMessage('Nie udało się uruchomić procesowania wypowiedzeń');
        }
        this.reloadTerminations();
        this.processing = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.processing = false;
      });
  }

  /**
   * Metoda walidująca dane wypowiedzeń przed wysyłką na serwer
   * @param terminationsToProcess
   */
  private terminationsAreInvalid(terminationsToProcess: ProcessTerminations) {
    let isInvalid = false;
    terminationsToProcess.terminationsToPostpone.forEach(terminationToPostpone => {
      terminationToPostpone.contracts.forEach(contract => {
        if (!DateUtils.isValidFutureDate(contract.terminationDate)) {
          this.messageService.showMessage(`Niepoprawna data odroczenia dla umowy ${contract.contractNumber} klienta ${terminationToPostpone.companyName}`);
          isInvalid = true;
          return;
        }
      });
    });
    return isInvalid;
  }

  private buildTerminationsToProcess() {
    const terminationsToProcess = new ProcessTerminations();
    this.terminations.forEach(termination => {
      const terminationToSend = Object.assign({}, termination.termination);
      terminationToSend.contracts = [];
      const terminationToReject = Object.assign({}, termination.termination);
      terminationToReject.contracts = [];
      const terminationToPostpone = Object.assign({}, termination.termination);
      terminationToPostpone.contracts = [];
      termination.contracts.forEach(contract => {
        if (contract.send) {
          terminationToSend.contracts.push(contract.terminationContract);
        } else if (contract.postpone) {
          terminationToPostpone.contracts.push(contract.terminationContract);
        } else if (contract.reject) {
          terminationToReject.contracts.push(contract.terminationContract);
        }
      });
      if (terminationToSend.contracts.length > 0) {
        terminationsToProcess.terminationsToSend.push(terminationToSend);
      }
      if (terminationToReject.contracts.length > 0) {
        terminationsToProcess.terminationsToReject.push(terminationToReject);
      }
      if (terminationToPostpone.contracts.length > 0) {
        terminationsToProcess.terminationsToPostpone.push(terminationToPostpone);
      }
    });
    return terminationsToProcess;
  }
}
