import {ElementRef, ViewChild} from '@angular/core';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import {TagService} from '../../../tag/tag.service';
import {MessageService} from '../../../shared/message.service';
import {HandlingErrorUtils} from '../../shared/utils/handling-error-utils';
import {Tag} from '../../../tag/tag';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';

/**
 * Klasa abstrakcyjna komponentu dla widoku dodawania i usuwania etykiet
 */
export abstract class TagIssueDialog {

  loading: boolean;
  issueTags: Tag[] = [];
  issueId: number;
  tags: Tag[] = [];

  tagCtrl = new FormControl();

  filteredTags: Observable<Tag[]>;

  @ViewChild('tagInput', {static: true}) tagInput: ElementRef;

  constructor(protected tagService: TagService, protected messageService: MessageService) {

  }

  /**
   * Dodaje pierwszą znalezioną etykietę (jeżeli nie została ona jeszcze dodana), której nzwa pasuje do podanej - akcja uruchamiana po wpisaniu nazwy etykiety i
   * przycisięciu enter, bez wybierania z listy dostępnych. Jeżeli nie istnieje taka etykieta wartość zostaje zignorowana. Niezależnie od powyżej logiki
   * na koniec wartść pola zostaje wyczyszczona.
   *
   * @param {MatChipInputEvent} event - zdarzenie wcisnięcia przycisku enter
   */
  add(event: MatChipInputEvent): void {
    const value = event.value;
    if (value === '') {
      return;
    }

    const filtered = this.filter(value);
    if (filtered.length > 0) {
      this.pushIssueTag(filtered[0]);
    }

    this.tagInput.nativeElement.value = '';
  }

  /**
   * Usuwa etykietę z listy etykiet zlecenia
   * @param tag - etykieta do usunięcia
   */
  remove(tag: any): void {
    const index = this.issueTags.indexOf(tag);

    if (index >= 0) {
      this.issueTags.splice(index, 1);
    }
  }

  /**
   * Znajduje na liście dostępnych etykiet tylko te, których nazwa zaczyna się tak samo jak podana przez użytkownika. Ignoruje wielkość liter.
   * @param {string | Tag} typedTag - podana etykieta przez użytkownika
   *
   * @returns {Tag[]} lista znalezionych etykiet
   */
  filter(typedTag: string | Tag) {
    return this.tags.filter(tag => {
      if (typeof typedTag === 'string') {
        return tag.name.toLowerCase().indexOf(typedTag.toLowerCase()) === 0;
      } else {
        return tag.name.toLowerCase().indexOf(typedTag.name.toLowerCase()) === 0;
      }
    });
  }

  /**
   * Dodaje wybraną etykietę jeżeli nie została ona jeszcze dodana do zlecenia. Niezależnie od powyżej logiki na koniec wartść pola zostaje wyczyszczona.
   * @param {MatAutocompleteSelectedEvent} event - zdarzenie wybrania etykiety z listy podpowiedzi
   */
  selected(event: MatAutocompleteSelectedEvent): void {
    this.pushIssueTag(event.option.value);
    this.tagInput.nativeElement.value = '';
  }

  /**
   * Dodaje wybraną etykietę jeżeli nie została ona jeszcze dodana do zlecenia. Niezależnie od powyżej logiki na koniec wartść pola zostaje wyczyszczona.
   * @param {Tag} newTag
   */
  protected pushIssueTag(newTag: Tag) {
    if (!this.issueTags.some(tag => tag.id === newTag.id)) {
      this.issueTags.push(newTag);
    }
  }

  /**
   * Pobiera listę etykiet
   */
  protected loadTags() {
    this.loading = true;
    this.tagService.getTags().subscribe(
      tags => {
        if (tags == null) {
          this.messageService.showMessage('Nie znaleziono etykiet');
          this.loading = false;
          return;
        }
        this.tags = tags;
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

  /**
   * Wyświetla nazwę etykiety
   * @param {Tag} tag - etykieta
   * @returns {string} nazwa etykiety
   */
  displayTag(tag: Tag): string {
    return tag ? tag.name : '';
  }

}
