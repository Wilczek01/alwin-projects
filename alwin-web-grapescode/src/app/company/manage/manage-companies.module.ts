import {NgModule} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
import {AlwinPipeModule} from '../../alwin-pipe.module';
import {AlwinMatModule} from '../../alwin-mat.module';
import {AlwinSharedModule} from '../../alwin-shared.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ManageCompaniesComponent} from './manage-companies.component';
import {ManageCompanyComponent} from './manage-company.component';
import {RouterModule} from '@angular/router';
import {ContractExclusionsDialogComponent} from './dialog/contract-exclusions-dialog.component';
import {CreateCustomerExclusionDialogComponent} from './dialog/create-customer-exclusion-dialog.component';
import {CreateContractExclusionDialogComponent} from './dialog/create-contract-exclusion-dialog.component';
import {UpdateContractExclusionDialogComponent} from './dialog/update-contract-exclusion-dialog.component';
import {UpdateCustomerExclusionDialogComponent} from './dialog/update-customer-exclusion-dialog.component';
import {CompanyOutOfServiceComponent} from './company-out-of-service.component';
import {ManageFormalDebtCollectionCompanyComponent} from './manage-formal-debt-collection-company.component';
import {DemandTypeKeyStringPipe} from '../../demand/pipe/demand-type-key-string.pipe';
import {ContractFormalDebtCollectionExclusionsDialogComponent} from './dialog/contract-formal-debt-collection-exclusions-dialog.component';
import {CreateCustomerFormalDebtCollectionExclusionDialogComponent} from './dialog/create-customer-formal-debt-collection-exclusion-dialog.component';
import {UpdateCustomerFormalDebtCollectionExclusionDialogComponent} from './dialog/update-customer-formal-debt-collection-exclusion-dialog.component';
import {CreateContractFormalDebtCollectionExclusionDialogComponent} from './dialog/create-contract-formal-debt-collection-exclusion-dialog.component';
import {UpdateContractFormalDebtCollectionExclusionDialogComponent} from './dialog/update-contract-formal-debt-collection-exclusion-dialog.component';

/**
 * Moduł deklarujący komponenty używane w widoku zarządzania klientami
 */
@NgModule({
  providers: [
    DatePipe
  ],
  imports: [
    CommonModule,
    AlwinMatModule,
    AlwinPipeModule,
    AlwinSharedModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  declarations: [
    ManageCompaniesComponent,
    ManageCompanyComponent,
    ContractExclusionsDialogComponent,
    CreateCustomerExclusionDialogComponent,
    CreateContractExclusionDialogComponent,
    UpdateCustomerExclusionDialogComponent,
    UpdateContractExclusionDialogComponent,
    CompanyOutOfServiceComponent,
    ManageFormalDebtCollectionCompanyComponent,
    DemandTypeKeyStringPipe,
    ContractFormalDebtCollectionExclusionsDialogComponent,
    CreateCustomerFormalDebtCollectionExclusionDialogComponent,
    UpdateCustomerFormalDebtCollectionExclusionDialogComponent,
    CreateContractFormalDebtCollectionExclusionDialogComponent,
    UpdateContractFormalDebtCollectionExclusionDialogComponent
  ],
  entryComponents: [
    ContractExclusionsDialogComponent,
    CreateCustomerExclusionDialogComponent,
    CreateContractExclusionDialogComponent,
    UpdateCustomerExclusionDialogComponent,
    UpdateContractExclusionDialogComponent,
    ContractFormalDebtCollectionExclusionsDialogComponent,
    CreateCustomerFormalDebtCollectionExclusionDialogComponent,
    UpdateCustomerFormalDebtCollectionExclusionDialogComponent,
    CreateContractFormalDebtCollectionExclusionDialogComponent,
    UpdateContractFormalDebtCollectionExclusionDialogComponent
  ],
  exports: [
    ManageCompaniesComponent,
    ManageCompanyComponent,
    ContractExclusionsDialogComponent,
    CreateCustomerExclusionDialogComponent,
    CreateContractExclusionDialogComponent,
    UpdateCustomerExclusionDialogComponent,
    UpdateContractExclusionDialogComponent,
    CompanyOutOfServiceComponent,
    ManageFormalDebtCollectionCompanyComponent,
    DemandTypeKeyStringPipe,
    ContractFormalDebtCollectionExclusionsDialogComponent,
    CreateCustomerFormalDebtCollectionExclusionDialogComponent,
    UpdateCustomerFormalDebtCollectionExclusionDialogComponent,
    CreateContractFormalDebtCollectionExclusionDialogComponent,
    UpdateContractFormalDebtCollectionExclusionDialogComponent
  ]
})
export class ManageCompaniesModule {
}
