import {LoginPage} from '../../util/po/login.po';
import {Navigation} from '../../util/navigation.po';

describe('welcome page', () => {
  let page: LoginPage;
  let navigation: Navigation;

  beforeEach(() => {
    page = new LoginPage();
    navigation = new Navigation();
    navigation.navigateToLogin();
  });

  it('should display login page if user is not logged in', () => {
    // when
    navigation.navigateToRoot();

    // then
    page.expectToSeeLoginBox();
  });

  it('should display login page if user is not logged in and wants to open dashboard', () => {
    // when
    navigation.navigateToDashboard();

    // then
    page.expectToSeeLoginBox();
  });

  it('should log in and display my issues if correct user and password were provided', () => {
    // given
    navigation.navigateToLogin();

    // when
    page.loginUser('amickiewicz', 'test');

    // then
    page.expectUserToBeLoggedIn();
  });

  it('should not log in if incorrect user or password was provided', () => {
    // given
    navigation.navigateToLogin();

    // when
    page.loginUser('amickiewicz', 'wrong_password');

    // then
    page.expectToSeeLoginBox();
  });

  it('should log out and display login page if user is logged in and wants to open login page', () => {
    // given
    page.loggedInUser('amickiewicz', 'test');

    // when
    navigation.navigateToLogin();

    // then
    page.expectToSeeLoginBox();
  });

  it('should not display login page if user is logged in', () => {
    // given
    page.loggedInUser('amickiewicz', 'test');

    // when
    navigation.navigateToDashboard();

    // then
    page.expectUserToBeLoggedIn();
  });

});
