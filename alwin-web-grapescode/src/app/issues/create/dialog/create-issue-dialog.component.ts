import {Component, OnInit} from '@angular/core';
import {ExtCompanyService} from '../../../company/ext-company.service';
import {MessageService} from '../../../shared/message.service';
import {IssueType} from '../../shared/issue-type';
import { MatDialogRef } from '@angular/material/dialog';
import {ExtCompany} from '../../../company/ext-company';
import {Operator} from '../../../operator/operator';
import {DateUtils} from '../../shared/utils/date-utils';
import {OperatorType} from '../../../operator/operator-type';
import {IssueService} from '../../shared/issue.service';
import {Router} from '@angular/router';
import {HandlingErrorUtils} from '../../shared/utils/handling-error-utils';

@Component({
  selector: 'alwin-create-issue-dialog',
  styleUrls: ['./create-issue-dialog.component.css'],
  templateUrl: './create-issue-dialog.component.html'
})
export class CreateIssueDialogComponent implements OnInit {

  extCompany: ExtCompany;
  companyBalancePLN: number;
  companyBalanceEUR: number;
  issueTypes: IssueType[] = [];
  managedOperators: Operator[] = [];
  issueTypeToOperators: Map<number, Operator[]> = new Map();
  minDate: Date;

  issueTypeOperators: Operator[] = [];

  balanceLoading = false;
  activeIssueIdLoading = false;
  issueCreating: boolean;

  issueTypeId: number;
  operatorId: number;
  expirationDate: Date;

  activeIssueId: number;

  constructor(public dialogRef: MatDialogRef<CreateIssueDialogComponent>, private extCompanyService: ExtCompanyService, private messageService: MessageService,
              private issueService: IssueService, private router: Router) {
    this.minDate = DateUtils.addDays(new Date, 1);
  }

  ngOnInit(): void {
    this.prepareIssueTypeToOperatorsMap();
    this.calculateCurrentCompanyBalance();
    this.findCompanyActiveIssue();
  }

  createIssue() {
    this.issueCreating = true;
    this.issueService.createIssue(this.extCompany.id, this.issueTypeId, this.expirationDate, this.operatorId).subscribe(
      createIssueResponse => {
        this.issueCreating = false;
        this.dialogRef.close();
        this.router.navigate([`issue/${createIssueResponse.issueId}`]);
      },
      err => {
        HandlingErrorUtils.handleErrorWithValidationMessage(this.messageService, err);
        this.issueCreating = false;
      }
    );
  }

  filterOperators() {
    this.operatorId = null;
    this.issueTypeOperators = this.issueTypeToOperators.get(this.issueTypeId);
  }

  private prepareIssueTypeToOperatorsMap() {
    this.issueTypes.forEach(issueType => {
      this.issueTypeToOperators.set(issueType.id, this.filterOperatorsByIssueOperatorTypes(issueType.operatorTypes));
    });
  }

  private filterOperatorsByIssueOperatorTypes(operatorTypes: OperatorType[]) {
    return this.managedOperators.filter(operator => operatorTypes.find(operatorType => operator.type.id === operatorType.id) != null);
  }

  private calculateCurrentCompanyBalance() {
    this.balanceLoading = true;
    this.extCompanyService.getCurrentCompanyBalance(this.extCompany.id).subscribe(
      balance => {
        this.companyBalancePLN = balance.balancePLN;
        this.companyBalanceEUR = balance.balanceEUR;
        this.balanceLoading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.balanceLoading = false;
      });
  }

  private findCompanyActiveIssue() {
    this.activeIssueIdLoading = true;
    this.issueService.getCompanyActiveIssueId(this.extCompany.id).subscribe(
      activeIssue => {
        this.activeIssueId = activeIssue.issueId;
        this.activeIssueIdLoading = false;
      },
      err => {
        if (err.status === 404) {
          this.activeIssueId = null;
        } else {
          HandlingErrorUtils.handleError(this.messageService, err);
        }
        this.activeIssueIdLoading = false;
      });
  }
}
