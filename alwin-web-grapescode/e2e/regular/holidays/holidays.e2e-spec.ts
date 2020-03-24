import {LoginUtilPage} from '../../util/login-util.po';
import {HolidaysPage} from '../../util/po/holidays.po';

describe('Holidays page', () => {

  let page: HolidaysPage;
  let util: LoginUtilPage;

  beforeEach(() => {
    page = new HolidaysPage();
    util = new LoginUtilPage();
  });

  it('should be accessible for phone debt collector manager', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToHolidays();

    // then
    util.expectUrlInQaModeToBe(util.navigation.HOLIDAYS, 'not redirected to holidays');
  });

  it('should be accessible for admin', () => {
    // given
    util.loggedInAdmin();

    // when
    util.navigation.navigateToHolidays();

    // then
    util.expectUrlInQaModeToBe(util.navigation.HOLIDAYS, 'not redirected to holidays');
  });

  it('should not be accessible for phone debt collector', () => {
    // given
    util.loggedInPhoneDebtCollector();

    // when
    util.navigation.navigateToHolidays();

    // then
    util.expectUrlToBe(util.navigation.SEGMENTS, 'not redirected to segments');
  });

});
