import {ManageCompaniesComponent} from './manage-companies.component';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AlwinMatModule} from '../../alwin-mat.module';
import {TruncatePipe} from 'app/shared/pipe/truncate.pipe';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import {AlwinHttpService} from '../../shared/authentication/alwin-http.service';
import {MessageService} from '../../shared/message.service';
import {COMPANY_ID, COMPANY_NAME, COMPANY_NIP, COMPANY_REGON} from '../../testing/data/ext-company.test.data';
import {ExtCompanyService} from '../ext-company.service';
import {IssueService} from '../../issues/shared/issue.service';
import {HttpClientModule} from '@angular/common/http';
import {OperatorService} from '../../issues/shared/operator.service';
import {RouterModule} from '@angular/router';
import {EnvironmentSpecificService} from '../../shared/environment-specific.service';

describe('Manage issue component', () => {

  let component: ManageCompaniesComponent;
  let fixture: ComponentFixture<ManageCompaniesComponent>;

  beforeEach(() => {
    // refine the test module by declaring the test component
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, FormsModule, AlwinMatModule, HttpClientModule, RouterModule],
      declarations: [ManageCompaniesComponent, TruncatePipe],
      providers: [MatProgressBarModule, AlwinMatModule, ExtCompanyService, AlwinHttpService, MessageService, IssueService, OperatorService, EnvironmentSpecificService]
    });

    // create component and test fixture
    fixture = TestBed.createComponent(ManageCompaniesComponent);

    // get test component from the fixture
    component = fixture.componentInstance;
  });

  it('should pass search parameters to datasource', () => {
    // given
    component.companyName = COMPANY_NAME;
    component.companyId = COMPANY_ID;
    component.companyNip = COMPANY_NIP;
    component.companyRegon = COMPANY_REGON;

    // when
    component.findCompanies();

    // then
    expect(component.dataSource.params.get('companyName')).toBe(COMPANY_NAME);
    expect(component.dataSource.params.get('extCompanyId')).toBe(String(COMPANY_ID));
    expect(component.dataSource.params.get('nip')).toBe(COMPANY_NIP);
    expect(component.dataSource.params.get('regon')).toBe(COMPANY_REGON);
  });

  it('should not pass empty search parameters to datasource', () => {
    // given
    component.companyName = null;
    component.companyId = null;
    component.companyNip = null;
    component.companyRegon = null;

    // when
    component.findCompanies();

    // then
    expect(component.dataSource.params.has('companyName')).toBeFalsy();
    expect(component.dataSource.params.has('extCompanyId')).toBeFalsy();
    expect(component.dataSource.params.has('nip')).toBeFalsy();
    expect(component.dataSource.params.has('regon')).toBeFalsy();
  });
});

