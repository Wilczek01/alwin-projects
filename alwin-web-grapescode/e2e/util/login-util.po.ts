import {LoginPage} from './po/login.po';
import {Navigation} from './navigation.po';
import {AbstractPage} from './abstract.po';
import {by, element} from 'protractor';

export class LoginUtilPage extends AbstractPage {
  loginPage = new LoginPage();
  navigation = new Navigation();

  loggedInUser(login: string, password: string) {
    this.loginPage.loggedInUser(login, password);
  }

  logout() {
    this.navigation.navigateToLogin();
  }

  loggedInAdmin() {
    this.loginPage.loggedInUser('amickiewicz', 'test');
  }

  loggedInAdminForcedToChangePassword() {
    this.loginPage.loggedInUser('amickiewicz2', 'test');
  }

  loggedInPhoneDebtCollector() {
    this.loginPage.loggedInUser('afredro', 'test');
  }

  loggedInPhoneDebtCollectorWithoutWork() {
    this.loginPage.loggedInUser('jslowacki', 'test');
  }

  loggedInPhoneDebtCollectorWithoutPermissionToManageIssueTermination() {
    this.loginPage.loggedInUser('wszymborska', 'test');
  }

  loggedInPhoneDebtCollectorManager() {
    this.loginPage.loggedInUser('cmilosz', 'test');
  }

  loggedInDirectDebtCollectionManager() {
    this.loginPage.loggedInUser('slem', 'test');
  }

  loggedInFieldDebtCollector() {
    this.loginPage.loggedInUser('jbrzechwa', 'test');
  }

  loggedInRestructuringSpecialist() {
    this.loginPage.loggedInUser('rkapuscinski', 'test');
  }

  loggedInRenunciationCoordinator() {
    this.loginPage.loggedInUser('jkochanowski', 'test');
  }

  loggedInSecuritySpecialist() {
    this.loginPage.loggedInUser('bprus', 'test');
  }

  loggedInAnalyst() {
    this.loginPage.loggedInUser('wreymont', 'test');
  }

  loggedInDepartmentManager() {
    this.loginPage.loggedInUser('jtuwim', 'test');
  }

  getLoggedInUserAndRoleLabel() {
    return element(by.id('logged-in-user-and-role')).getText();
  }
}
