import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {AuthGuard} from './shared/authentication/auth.guard';
import {MyIssuesComponent} from './issues/my/my-issues.component';
import {IssueComponent} from './issues/issue/issue.component';
import {RoleType} from './issues/shared/role-type';
import {ManageIssuesComponent} from './issues/manage/manage-issues.component';
import {UsersComponent} from './user/users.component';
import {UserDetailsComponent} from './user/user-details.component';
import {SegmentsComponent} from './segment/segments.component';
import {ManageCompaniesComponent} from './company/manage/manage-companies.component';
import {HolidaysComponent} from './holiday/holidays.component';
import {DashboardPlaceholderComponent} from './dashboard-placeholder/dashboard.placeholder.component';
import {ChangePasswordComponent} from './operator/password/change-password.component';
import {ChangePasswordGuard} from './shared/authentication/changePassword.guard';
import {TagComponent} from './tag/tag.component';
import {ConfigurationComponent} from './configuration/configuration.component';
import {FieldDebtIssuesComponent} from './issues/field-debt-issues/field-debt-issues.component';
import {DemandsComponent} from './demand/demands.component';
import {TerminationsComponent} from './termination/terminations.component';
import {LocationComponent} from './location/location.component';
import {CompanyOutOfServiceComponent} from './company/manage/company-out-of-service.component';

export const routes: Routes = [
  {path: '', redirectTo: '/segments', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'changePassword', component: ChangePasswordComponent, canActivate: [ChangePasswordGuard]},
  {
    path: 'dashboard',
    component: ManageIssuesComponent,
    canActivate: [AuthGuard],
    data: {roles: [RoleType.ADMIN, RoleType.PHONE_DEBT_COLLECTOR, RoleType.PHONE_DEBT_COLLECTOR_1, RoleType.PHONE_DEBT_COLLECTOR_2], readonly: true}
  },
  {
    path: 'segments', component: SegmentsComponent, canActivate: [AuthGuard], data: {
      roles: [RoleType.PHONE_DEBT_COLLECTOR, RoleType.PHONE_DEBT_COLLECTOR_1, RoleType.PHONE_DEBT_COLLECTOR_2, RoleType.PHONE_DEBT_COLLECTOR_MANAGER]
    }
  },
  {
    path: 'issues/my',
    component: MyIssuesComponent,
    canActivate: [AuthGuard],
    data: {roles: [RoleType.PHONE_DEBT_COLLECTOR, RoleType.PHONE_DEBT_COLLECTOR_1, RoleType.PHONE_DEBT_COLLECTOR_2, RoleType.FIELD_DEBT_COLLECTOR]}
  },
  {path: 'issue/:issueId', component: IssueComponent, canActivate: [AuthGuard], data: {readonly: true}},
  {
    path: 'my-work',
    component: IssueComponent,
    canActivate: [AuthGuard],
    data: {roles: [RoleType.PHONE_DEBT_COLLECTOR, RoleType.PHONE_DEBT_COLLECTOR_1, RoleType.PHONE_DEBT_COLLECTOR_2], readonly: false}
  },
  {
    path: 'issues/manage',
    component: ManageIssuesComponent,
    canActivate: [AuthGuard],
    data: {roles: [RoleType.PHONE_DEBT_COLLECTOR_MANAGER, RoleType.DIRECT_DEBT_COLLECTION_MANAGER]}
  },
  {
    path: 'issues/field-debt',
    component: FieldDebtIssuesComponent,
    canActivate: [AuthGuard],
    data: {roles: [RoleType.FIELD_DEBT_COLLECTOR]}
  },
  {
    path: 'companies/manage',
    component: ManageCompaniesComponent,
    canActivate: [AuthGuard],
    data: {roles: [RoleType.PHONE_DEBT_COLLECTOR_MANAGER, RoleType.DIRECT_DEBT_COLLECTION_MANAGER]}
  },
  {
    path: 'demands',
    component: DemandsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'terminations',
    component: TerminationsComponent,
    canActivate: [AuthGuard],
    data: {roles: [RoleType.PHONE_DEBT_COLLECTOR_MANAGER]}
  },
  {
    path: 'holidays',
    component: HolidaysComponent,
    canActivate: [AuthGuard],
    data: {roles: [RoleType.PHONE_DEBT_COLLECTOR_MANAGER, RoleType.ADMIN, RoleType.DIRECT_DEBT_COLLECTION_MANAGER]}
  },
  {
    path: 'tags',
    component: TagComponent,
    canActivate: [AuthGuard],
    data: {roles: [RoleType.PHONE_DEBT_COLLECTOR_MANAGER, RoleType.DIRECT_DEBT_COLLECTION_MANAGER]}
  },
  {
    path: 'locations',
    component: LocationComponent,
    canActivate: [AuthGuard],
    data: {roles: [RoleType.DIRECT_DEBT_COLLECTION_MANAGER, RoleType.ADMIN]}
  },
  {
    path: 'companies/:extCompanyId/manage',
    component: CompanyOutOfServiceComponent,
    canActivate: [AuthGuard],
    data: {roles: [RoleType.PHONE_DEBT_COLLECTOR_MANAGER, RoleType.DIRECT_DEBT_COLLECTION_MANAGER]}
  },
  {path: 'users/:userId', component: UserDetailsComponent, canActivate: [AuthGuard], data: {roles: [RoleType.ADMIN]}},
  {path: 'users', component: UsersComponent, canActivate: [AuthGuard], data: {roles: [RoleType.ADMIN]}},
  {path: 'configuration', component: ConfigurationComponent, canActivate: [AuthGuard], data: {roles: [RoleType.ADMIN]}},
  {
    path: 'dashboard-placeholder', component: DashboardPlaceholderComponent, canActivate: [AuthGuard], data: {
      roles: [
        RoleType.RESTRUCTURING_SPECIALIST,
        RoleType.RENUNCIATION_COORDINATOR,
        RoleType.SECURITY_SPECIALIST,
        RoleType.ANALYST,
        RoleType.DEPARTMENT_MANAGER
      ]
    }
  },
  {path: '**', component: NotFoundComponent}
];

/**
 * Moduł odpowiedzialny za nawigację po aplikacji
 */
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AlwinRoutingModule {
}
