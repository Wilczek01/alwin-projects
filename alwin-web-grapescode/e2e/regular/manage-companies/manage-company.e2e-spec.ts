import {LoginUtilPage} from '../../util/login-util.po';
import {ManageCompanyPage} from '../../util/po/manage-company.po';

describe('manage company page', () => {

  let page: ManageCompanyPage;
  let util: LoginUtilPage;

  beforeEach(() => {
    page = new ManageCompanyPage();
    util = new LoginUtilPage();
  });

  it('should manage company', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // and
    util.navigation.navigateToManagingCompanies();

    // and
    page.findCompanyById(10);

    // and
    page.clickCompanyDetailsLinkOnCompanyList();

    // then
    util.expectNewTabWithUrl('/companies/10/manage', 'not redirected to managing company');
  });

  it('should display company exclusions and company contracts exclusions', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManageCompany(253);

    // then
    page.expectCompanyExclusions();
    page.expectActiveContract();
    page.expectInactiveContract();
  });

  it('should update company exclusion', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManageCompany(253);

    // then
    page.expectCompanyExclusionToBeUpdated();
  });

  it('should update company contract exclusion', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManageCompany(253);

    // then
    page.expectCompanyContractExclusionToBeUpdated();
  });

  it('should add new company exclusion', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManageCompany(253);

    // then
    page.expectCompanyExclusionToBeAdded();
  });

  it('should add new company contract exclusion', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManageCompany(253);

    // then
    page.expectCompanyContractExclusionToBeAdded();
  });

  it('should delete existing company exclusion', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManageCompany(253);

    // then
    page.expectCompanyExclusionToBeDeleted();
  });

  it('should delete existing company contract exclusion', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManageCompany(253);

    // then
    page.expectCompanyContractExclusionToBeDeleted();
  });

  it('should present company data', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManageCompany(10);

    // then
    page.expectCompanyDataToBePresented();
  });

});
