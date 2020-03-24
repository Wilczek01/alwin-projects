import {Activity} from './activity';
import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ActivityService} from './activity.service';
import {MessageService} from '../shared/message.service';
import {Issue} from '../issues/shared/issue';
import {ActivityState} from './activity-state';
import {FormArray, FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {Operator} from '../operator/operator';
import {RoleType} from '../issues/shared/role-type';
import {UserAccessService} from '../common/user-access.service';
import {ActivityDetail} from './activity-detail';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';
import {PersonService} from '../person/person.service';
import {Person, UNKNOWN_PERSON} from '../customer/shared/person';
import {KeyLabel} from '../shared/key-label';
import {ActivityUtils} from './activity.utils';
import {ActivityTypeDetailProperty} from './activity-type-detail-property';
import {ActivityStateConst} from './activity-state-const';
import {ContactDetailService} from '../customer/contact-detail/contact-detail-service';
import { map, startWith } from 'rxjs/operators';
/**
 * Komponent odpowiedzialny za wyświetlanie formularza z danymi czynności windykacyjnej wraz z jej szczegółami oraz deklaracjami spłaty
 */
@Component({
  selector: 'alwin-activity-inputs',
  styleUrls: ['./activity-inputs.component.css'],
  templateUrl: './activity-inputs.component.html'
})
export class ActivityInputsComponent implements OnInit {

  @Input()
  activity: Activity;

  @Input()
  issue: Issue;

  @Input()
  loading: boolean;

  @Input()
  creation: boolean;

  @Input()
  activityPlanning: boolean;

  @Input()
  updateWithDeclarations: boolean;

  statuses: ActivityState[] = [];

  @Input()
  declarationsForm: FormArray;

  operatorControl: FormControl = new FormControl();
  filteredOptions: Observable<Operator[]>;

  @Input()
  operators: Operator[];

  @Input()
  activityExecuted: boolean;

  issues: number[];

  role = RoleType;

  persons: Person[];

  @Input()
  comment: ActivityDetail;

  @Input()
  readOnly: boolean;

  @Output()
  commentChange = new EventEmitter<ActivityDetail>();

  @Input()
  filesData: Map<string, string>;

  suggestedPhones: string[];

  constructor(private activityService: ActivityService, private messageService: MessageService, private userAccessService: UserAccessService,
              private personService: PersonService, protected contactDetailService: ContactDetailService) {
  }

  ngOnInit() {
    this.loading = true;
    this.activityService.getActivityStatuses().subscribe(
      statuses => {
        if (statuses == null) {
          this.loading = false;
          return;
        }
        this.statuses = statuses;
        this.loading = false;
        if (this.activity.state != null) {
          this.activity.state = this.statuses.find(obj => obj.key === this.activity.state.key);
        }
        if (this.creation) {
          this.activity.state = this.statuses.find(obj => obj.key === ActivityStateConst.EXECUTED);
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      });

    const companyId = this.issue.customer.company.id;
    this.loadPersons(companyId);
    this.loadContactDetailsStates(companyId);

    if (this.activity.operator) {
      if (this.activity.operator.id != null) {
        const matchingOperators = this.operators.filter(operator => operator.id === this.activity.operator.id);
        if (matchingOperators.length !== 0) {
          this.operatorControl.setValue(matchingOperators[0]);
        }
      }

      this.filteredOptions = this.operatorControl.valueChanges.pipe(
        startWith(null),
        map(operator => operator && typeof operator === 'object' ? operator.user.firstName : operator),
        map(name => name ? this.filterOperators(name) : this.operators.slice())
      );
    } else {
      this.filteredOptions = this.operatorControl.valueChanges.pipe(
        startWith(null),
        map(operator => operator && typeof operator === 'object' ? operator.user.firstName : operator),
        map(name => name ? this.filterOperators(name) : this.operators.slice())
      );
    }
    this.reloadDetails();
  }

  /**
   * Pobiera osoby uprawnione dla firmy
   */
  loadPersons(companyId: number) {
    this.loading = true;
    this.personService.getCompanyPersons(companyId).subscribe(
      persons => {
        if (persons != null) {
          this.persons = persons;
          this.persons.unshift(UNKNOWN_PERSON);
        }
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      });
  }

  /**
   * Filtruje listę załadowanych operatorów po przekazanym ciągu znaków. Rozważana jest sekwencja Imię, Nazwisko oraz Imię i nazwisko oddzielone spacją
   * @param {string} name - ciąg znaków po którym nastąpi filtrowanie
   * @returns {Operator[]} - lista operatorów po przefiltrowaniu
   */
  filterOperators(name: string): Operator[] {
    return this.operators.filter(operator =>
      operator.user.firstName.toLowerCase().indexOf(name.toLowerCase()) === 0 ||
      operator.user.lastName.toLowerCase().indexOf(name.toLowerCase()) === 0 ||
      `${operator.user.firstName} ${operator.user.lastName}`.toLowerCase().indexOf(name.toLowerCase()) === 0);
  }

  /**
   * Zwraca ciąg znaków w formacie 'Imię Nazwisko' reprezentujący podanego operatora
   * @param {Operator} operator - operator do wyświetlenia
   * @returns {string} - 'Imię Nazwisko' lub pusta wartość, jeżeli przekazany użytkownik jest nullem
   */
  displayOperator(operator: Operator): string {
    return operator ? `${operator.user.firstName} ${operator.user.lastName}` : '';
  }

  /**
   * Zwraca ciąg znaków reprezentujący priorytet
   * @param {KeyLabel} priority - operator do wyświetlenia
   * @returns {string} - 'Label' lub pusta wartość, jeżeli przekazany priorytet jest nullem
   */
  displayPriority(priority: KeyLabel): string {
    return priority ? `${priority.label}` : '';
  }

  /**
   * Sprawdza czy podana rola użytkownika jest taka sama jak rola zalogowanego użytkownika
   * @param {string} role - sprawdzana rola
   * @returns {boolean} - true jeżeli podana rola jest taka sama jak rola zalogowanego użytkownika, false w przeciwnym wypadku
   */
  hasRole(role: string) {
    return this.userAccessService.hasRole(role);
  }

  /**
   * Wczytanie danych szczegółowych czynności windykacyjnej
   */
  reloadDetails(): void {
    if (!this.creation && this.activity != null && this.activity.activityDetails.length === 0) {
      for (const property of this.activity.activityType.activityTypeDetailProperties) {
        if (ActivityUtils.isComment(property.activityDetailProperty)) {
          this.comment = this.comment != null ? this.comment : ActivityInputsComponent.prepareActivityDetail(property);
          continue;
        }
        const activityDetail = ActivityInputsComponent.prepareActivityDetail(property);
        this.activity.activityDetails.push(activityDetail);
      }
      return;
    }
  }

  /**
   * Utworzenie nowego ActivityDetail na podstawie ActivityTypeDetailProperty
   * @param {ActivityTypeDetailProperty} property - cecha
   * @returns {ActivityDetail}
   */
  private static prepareActivityDetail(property: ActivityTypeDetailProperty): ActivityDetail {
    const activityDetail = new ActivityDetail();
    activityDetail.detailProperty = property.activityDetailProperty;
    activityDetail.required = property.required;
    return activityDetail;
  }

  /**
   * Wysłanie eventu w przypadku edycji komentarza
   */
  commentFieldChanged() {
    this.commentChange.emit(this.comment);
  }

  /**
   * Ładuje sugerowane numery telefonów firmy i jej pracowników - preferowany, aktywny, nieaktwyny
   */
  loadContactDetailsStates(companyId: number) {
    this.loading = true;
    this.contactDetailService.getSuggestedPhoneNumbers(companyId).subscribe(
      suggestedPhones => {
        if (suggestedPhones == null) {
          this.loading = false;
          return;
        }
        this.suggestedPhones = suggestedPhones;
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      });
  }
}
