import {UsersPage} from '../../util/po/users.po';
import {LoginUtilPage} from '../../util/login-util.po';
import {UsersAssert} from '../assertions/users.assert';
import {UsersTestData} from '../data/users.test-data';
import {browser} from 'protractor';

describe(' ', () => {
  let userPage: UsersPage;
  let util: LoginUtilPage;
  let usersAssert;

  beforeAll(() => {
    userPage = new UsersPage();
    util = new LoginUtilPage();
    usersAssert = new UsersAssert(userPage);
  });

  it('admin should be able to log in, display table of users and add new user with active operator', () => {
    util.navigation.navigateToLogin();

    util.loginPage.loginUser(UsersTestData.META_OPERATOR_NAME, UsersTestData.META_OPERATOR_PASSWORD);

    util.loginPage.expectUserToBeLoggedIn();

    util.expectUrlToBe(util.navigation.DASHBOARD, 'not redirected to dashboard');

    util.navigation.navigateToUsers();

    util.expectUrlInQaModeToBe(util.navigation.USERS, 'not redirected to users');

    // TODO: dodać asercję na sprawdzenie zawartości tabeli użytkowników

    userPage.clickAddUserButton();

    usersAssert.expectCreateUserDialog();

    userPage.fillNewUserData();

    userPage.clickCreateUserButton();

    // TODO: dodać asercję na sprawdzenie zawartości tabeli użytkowników czy pojawił się nowy

    userPage.expandSecondUserOnTheList();

    userPage.clickEditExpandedUser();

    util.expectUrlToBe(`${util.navigation.USERS}/${UsersTestData.CREATED_MANAGER_ID}`, `not redirected to edit user ${UsersTestData.CREATED_MANAGER_ID}`);

    util.navigation.navigateToUser(UsersTestData.CREATED_MANAGER_ID);

    userPage.clickAddOperatorButton();

    userPage.fillNewOperatorData();

    userPage.clickUpdateUserDataButton();

    util.navigation.navigateToUsers();

    util.expectUrlInQaModeToBe(util.navigation.USERS, 'not redirected to users');

    userPage.expandSecondUserOnTheList();

    usersAssert.expectCreatedUserOperator();
  });

  it('phone debt collector should be able to log in right after admin created his account', () => {
    util.navigation.navigateToLogin();

    util.loginPage.loginUser(UsersTestData.CREATED_MANAGER_LOGIN, UsersTestData.CREATED_MANAGER_PASSWORD);

    util.loginPage.expectUserToBeLoggedIn();

    util.expectUrlToBe(util.navigation.SEGMENTS, 'not redirected to segments');
  });


});
