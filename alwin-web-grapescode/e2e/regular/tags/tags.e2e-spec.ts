import {LoginUtilPage} from '../../util/login-util.po';
import {TagsPage} from '../../util/po/tags.po';
import {TagsAssert} from './tags.assert';

describe('Tags page', () => {

  let assert: TagsAssert;
  let util: LoginUtilPage;
  let tagsPage: TagsPage;

  beforeEach(() => {
    tagsPage = new TagsPage();
    assert = new TagsAssert(tagsPage);
    util = new LoginUtilPage();
  });

  it('should be accessible for phone debt collector manager', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToTags();

    // then
    util.expectUrlInQaModeToBe(util.navigation.TAGS, 'not redirected to tags');
  });

  it('should not be accessible for phone debt collector', () => {
    // given
    util.loggedInPhoneDebtCollector();

    // when
    util.navigation.navigateToHolidays();

    // then
    util.expectUrlToBe(util.navigation.SEGMENTS, 'not redirected to segments');
  });

  it('should display tags table', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToTags();

    // then
    assert.expectTags();
  });

  it('should display create tag dialog with selected first tag icon', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToTags();

    // and
    tagsPage.clickAddTagButton();

    // then
    assert.expectToSeeCreateTagDialog();

    // and
    assert.dialogHeader('Nowa etykieta');

    // and
    assert.tagIconsInTagIconPicker();

    // and
    assert.expectSelectedIcon(0);
  });

  it('should display update tag dialog with selected tag icon', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToTags();

    // and
    tagsPage.clickEditTagButton();

    // then
    assert.expectToSeeUpdateTagDialog();

    // and
    assert.dialogHeader('Aktualizacja etykiety');

    // and
    assert.tagIconsInTagIconPicker();

    // and
    assert.expectSelectedIcon(1);
  });

});
