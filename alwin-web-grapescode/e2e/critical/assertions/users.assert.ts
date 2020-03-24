import {UsersPage} from '../../util/po/users.po';
import {by, element} from 'protractor';
import {UsersTestData} from '../data/users.test-data';

export class UsersAssert {

  constructor(private page: UsersPage) {
  }

  expectCreateUserDialog() {
    expect(element(by.tagName('alwin-create-user-dialog')).isPresent()).toBe(true);
  }

  expectCreatedUserOperator() {
    expect(element(by.id(`${UsersTestData.CREATED_MANAGER_ID}-operator-0-login`)).getText()).toBe(UsersTestData.CREATED_MANAGER_LOGIN);
    expect(element(by.id(`${UsersTestData.CREATED_MANAGER_ID}-operator-0-type`)).getText()).toBe('Menedżer windykacji telefonicznej');
    expect(element(by.id(`${UsersTestData.CREATED_MANAGER_ID}-operator-0-active`)).getAttribute('checked')).toBe('true');
  }

}
