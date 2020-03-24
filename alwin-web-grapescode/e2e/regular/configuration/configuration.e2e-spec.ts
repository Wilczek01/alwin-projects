import {LoginUtilPage} from '../../util/login-util.po';
import {SchedulersAssert} from './schedulers.assert';
import {ConfigurationPage} from '../../util/po/configuration.po';
import {ConfigurationAssert} from './configuration.assert';


describe('Configuration page', () => {

  let assert: SchedulersAssert;
  let configurationAssert: ConfigurationAssert;
  let util: LoginUtilPage;
  let page: ConfigurationPage;

  beforeEach(() => {
    assert = new SchedulersAssert(new ConfigurationPage());
    util = new LoginUtilPage();
    page = new ConfigurationPage();
    configurationAssert = new ConfigurationAssert(new ConfigurationPage());
  });

  it('should be accessible for admin', () => {
    // given
    util.loggedInAdmin();

    // when
    util.navigation.navigateToConfiguration();

    // then
    util.expectUrlInQaModeToBe(util.navigation.CONFIGURATION, 'not redirected to configuration');
  });

  it('should not be accessible for phone debt collector', () => {
    // given
    util.loggedInPhoneDebtCollector();

    // when
    util.navigation.navigateToConfiguration();

    // then
    util.expectUrlToBe(util.navigation.SEGMENTS, 'not redirected to segments');
  });

  it('should display configuration executions table', () => {
    // given
    util.loggedInAdmin();

    // when
    util.navigation.navigateToConfiguration();

    // then
    assert.expectSchedulersExecutionsInTable();
  });

  it('should issue type configuration be accessible for admin', () => {
    // given
    util.loggedInAdmin();

    // when
    util.navigation.navigateToConfiguration();

    // and
    page.clickIssueTypeConfigurationTab();

    // then
    configurationAssert.expectIssueTypeConfigurations();
  });

  it('should edit existing issue type configuration', () => {
    // given
    util.loggedInAdmin();

    // when
    util.navigation.navigateToConfiguration();

    // and
    page.clickIssueTypeConfigurationTab();

    // then
    page.editExistingIssueTypeConfiguration();
  });

  it('should issue activity type configuration be accessible for admin', () => {
    // given
    util.loggedInAdmin();

    // when
    util.navigation.navigateToConfiguration();

    // and
    page.clickActivityTypeConfigurationTab();

    // then
    configurationAssert.expectActivityTypeConfigurations();
  });

  // TODO: trzeba poprawić funkcjonalność po zmienie modelu, póki co jest disable na widoku, Task: #26024
  xit('should edit existing issue activity type configuration', () => {
    // given
    util.loggedInAdmin();

    // when
    util.navigation.navigateToConfiguration();

    // and
    page.clickActivityTypeConfigurationTab();

    // then
    page.editExistingIssueTypeActivityConfiguration();
  });


  it('should issue default activity configuration be accessible for admin', () => {
    // given
    util.loggedInAdmin();

    // when
    util.navigation.navigateToConfiguration();

    // and
    page.clickDefaultActivityConfigurationTab();

    // then
    configurationAssert.expectDefaultActivityConfigurationsForFirstTab();

    // and
    page.clickSecondIssueTypeTab();

    // and
    configurationAssert.expectDefaultActivityConfigurationsForSecondTab();
  });

  it('should edit existing issue default activity configuration', () => {
    // given
    util.loggedInAdmin();

    // when
    util.navigation.navigateToConfiguration();

    // and
    page.clickDefaultActivityConfigurationTab();

    // then
    page.editDefaultActivityConfiguration();
  });

});
