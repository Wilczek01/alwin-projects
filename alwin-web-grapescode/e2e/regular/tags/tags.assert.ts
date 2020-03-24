import {TagsPage} from '../../util/po/tags.po';

export class TagsAssert {

  constructor(private page: TagsPage) {
  }

  expectTags() {
    this.expectTag(1, 'Koszyk 1', 'Zlecenie koszyka 1', 'rgba(51, 51, 51, 1)', null, null);
    this.expectTag(2, 'Koszyk 2', 'Zlecenie koszyka 2', 'rgba(153, 153, 153, 1)', null, null);
    this.expectTag(3, 'Zaległe', 'Zaległe zlecenie', 'rgba(229, 57, 53, 1)', null, 'true');
  }

  expectTag(number: number, name: string, description: string, color: string, editDisabled: string, deleteDisabled: string) {
    const row = this.page.getTagsTableRow(number);
    expect(row.get(0).getText()).toBe(name);
    expect(row.get(1).getText()).toBe(description);
    expect(this.page.getBookmarkColor(row.get(2))).toBe(color);
    expect(this.page.getEditDisabled(row.get(3), number)).toBe(editDisabled);
    expect(this.page.getDeleteDisabled(row.get(3), number)).toBe(deleteDisabled);
  }

  expectToSeeCreateTagDialog() {
    expect(this.page.createTagDialog().isPresent()).toBe(true);
  }

  expectToSeeUpdateTagDialog() {
    expect(this.page.updateTagDialog().isPresent()).toBe(true);
  }

  dialogHeader(header: string) {
    expect(this.page.getHeaderTitle()).toBe(header);
  }

  tagIconsInTagIconPicker() {
    const tagIcons = this.page.getTagIconsInTagIconPicker();
    expect(tagIcons.count()).toBe(3);
    expect(tagIcons.get(0).getText()).toBe('bookmark');
    expect(tagIcons.get(1).getText()).toBe('contact_support');
    expect(tagIcons.get(2).getText()).toBe('date_range');
  }

  expectSelectedIcon(iconIndex: number) {
    const tagIcons = this.page.getTagIconsInTagIconPicker();
    const selectedIcons = this.page.getSelectedTagIconsInTagIconPicker();
    expect(selectedIcons.count()).toBe(1);
    expect(selectedIcons.get(0).getText()).toBe(tagIcons.get(iconIndex).getText());
  }

}
