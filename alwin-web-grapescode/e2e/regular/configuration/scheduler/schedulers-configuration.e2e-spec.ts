import {SchedulersConfigurationAssert} from './schedulers-configuration.assert';
import {LoginUtilPage} from '../../../util/login-util.po';
import {SchedulersConfiguration} from '../../../util/po/schedulers-configuration.po';


describe('Scheduler configuration page', () => {

  let assert: SchedulersConfigurationAssert;
  let util: LoginUtilPage;
  let page: SchedulersConfiguration;

  beforeEach(() => {
    assert = new SchedulersConfigurationAssert(new SchedulersConfiguration());
    util = new LoginUtilPage();
    page = new SchedulersConfiguration();
  });

  it('should contain expected informations in scheduler configuration table', () => {
    // given
    util.loggedInAdmin();

    // and
    util.navigation.navigateToConfiguration();

    // when
    page.openSchedulerConfigurationTab();

    // then
    assert.expectSchedulersConfigurationsInTable();
  });

  it('should show confirmation dialog on start process action', () => {
    // given
    util.loggedInAdmin();

    // and
    util.navigation.navigateToConfiguration();

    // and
    page.openSchedulerConfigurationTab();

    // when
    page.clickStartProcessButton('CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED');

    // then
    assert.expectConfirmationDialogVisible('Zamknięcie przeterminowanych zleceń i w razie potrzeby utworzenie nowych z odpowiednim typem\nCzy na pewno chcesz uruchomić?');
  });

  it('should open edit scheduler hour dialog on edit button pressed', () => {
    // given
    util.loggedInAdmin();

    // and
    util.navigation.navigateToConfiguration();

    // and
    page.openSchedulerConfigurationTab();

    // when
    page.clickEditSchedulerHourButton('BASE_SCHEDULE_TASKS');

    // then
    assert.expectSchedulerEditDialogVisible('Aktualizacja sald, otwarcie nowych oraz zamknięcie przeterminowanych zleceń. ' +
      'Przetwarzanie przeterminowanych dokumentów i wysłanie raportu email. Aktualizacja firm: dane adresowe i kontaktowe, zaangażowanie firm. ' +
      'Wysłanie raportu o nierozwiązanych zleceniach. Utworzenie czynności windykacyjnych typu \'telefon wychodzący\'');

    // and
    assert.expectExecutionStartHour(3);
  });

});
