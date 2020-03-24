import {Component, OnInit, ViewChild} from '@angular/core';
import {ExtCompanyService} from '../ext-company.service';
import {MessageService} from '../../shared/message.service';
import {ExtCompaniesDatasource} from '../ext-companies-datasource';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import {PaginatorLabels} from '../../shared/pagination/paginator-labels';
import {CreateIssueDialogComponent} from 'app/issues/create/dialog/create-issue-dialog.component';
import {IssueService} from '../../issues/shared/issue.service';
import {IssueType} from '../../issues/shared/issue-type';
import {ExtCompany} from '../ext-company';
import {OperatorService} from '../../issues/shared/operator.service';
import {Operator} from '../../operator/operator';
import {HttpParams} from '@angular/common/http';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';

@Component({
  selector: 'alwin-manage-companies',
  styleUrls: ['./manage-companies.component.css'],
  templateUrl: './manage-companies.component.html'
})
export class ManageCompaniesComponent implements OnInit {

  companyDisplayedColumns = [
    'id',
    'companyName',
    'nip',
    'regon',
    'krs',
    'legalForm',
    'createIssue'
  ];

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  dataSource: ExtCompaniesDatasource | null;

  companyId: number;
  companyName: string;
  companyNip: string;
  companyRegon: string;

  issueTypes: IssueType[] = [];
  managedOperators: Operator[];
  loadingIssueTypes: boolean;
  loadingOperators: boolean;

  constructor(private extCompanyService: ExtCompanyService, private issueService: IssueService, private operatorService: OperatorService,
              private messageService: MessageService, private createIssueDialog: MatDialog) {
  }

  ngOnInit(): void {
    this.dataSource = new ExtCompaniesDatasource(this.extCompanyService, this.paginator, this.messageService, null);
    PaginatorLabels.addAlwinLabels(this.paginator);
    this.createIssueTypes();
    this.createManagedOperators();
  }

  openCreateIssueDialog(company: ExtCompany) {
    const dialogRef = this.createIssueDialog.open(CreateIssueDialogComponent);
    dialogRef.componentInstance.extCompany = company;
    dialogRef.componentInstance.issueTypes = this.issueTypes;
    dialogRef.componentInstance.managedOperators = this.managedOperators;
  }

  findCompanies() {
    let params = new HttpParams();
    if (this.companyId) {
      params = params.append('extCompanyId', String(this.companyId));
    }
    if (this.companyName) {
      params = params.append('companyName', this.companyName);
    }
    if (this.companyNip) {
      params = params.append('nip', this.companyNip);
    }
    if (this.companyRegon) {
      params = params.append('regon', this.companyRegon);
    }

    this.dataSource = new ExtCompaniesDatasource(this.extCompanyService, this.paginator, this.messageService, params);
    this.paginator.pageIndex = 0;
    PaginatorLabels.addAlwinLabels(this.paginator);
  }

  createIssueTypes() {
    this.loadingIssueTypes = true;
    this.issueService.getAllIssueTypes().subscribe(
      issueTypes => {
        if (issueTypes == null) {
          this.loadingIssueTypes = false;
          return;
        }
        this.issueTypes = issueTypes;
        this.loadingIssueTypes = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingIssueTypes = false;
      });
  }

  private createManagedOperators() {
    this.loadingOperators = true;
    this.operatorService.getManagedOperators().subscribe(
      managedOperators => {
        if (managedOperators == null) {
          this.loadingOperators = false;
          return;
        }
        this.managedOperators = managedOperators;
        this.loadingOperators = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingOperators = false;
      });
  }
}
