import {Component, OnInit} from '@angular/core';
import {MessageService} from '../shared/message.service';
import {WalletService} from './wallet.service';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';
import {UserAccessService} from 'app/common/user-access.service';
import {IssueType} from '../issues/shared/issue-type';
import {NavigationExtras, Router} from '@angular/router';
import {KeyLabel} from '../shared/key-label';
import {WalletsByIssueStates} from './wallets-by-issue-states';
import {TagWallet} from './tag-wallet';
import {Tag} from '../tag/tag';
import {IssueStateWallet} from './issue-state-wallet';
import {AllWallets} from './all-wallets';

@Component({
  selector: 'alwin-segments',
  styleUrls: ['./segments.component.css'],
  templateUrl: './segments.component.html'
})
export class SegmentsComponent implements OnInit {
  allWallets: AllWallets;
  tagWallets: TagWallet[];
  walletsByIssueStates: WalletsByIssueStates;
  waitingForTerminationIssuesWallet: IssueStateWallet;

  loadingAllWallets: boolean;

  constructor(private walletService: WalletService,
              private messageService: MessageService,
              private userAccessService: UserAccessService,
              private router: Router) {
  }

  /**
   * Inicjalizacja komponentu przy załadowaniu strony
   */
  ngOnInit(): void {
    this.loadAllWallets();
  }

  /**
   *  Pobieranie wszystkich portfeli prezentowanych na stronie segmentów
   */
  private loadAllWallets() {
    this.loadingAllWallets = true;
    this.walletService.getAllWallets().subscribe(
      allWallets => {
        if (allWallets) {
          this.allWallets = allWallets;
          this.tagWallets = allWallets.tagWallets;
          this.walletsByIssueStates = allWallets.walletsByIssueStates;
          this.waitingForTerminationIssuesWallet = allWallets.waitingForTerminationIssuesWallet;
        }
        this.loadingAllWallets = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingAllWallets = false;
      }
    );
  }

  /**
   * Sprawdza czy użytkownik jest managerem windykacji telefonicznej
   * @returns {boolean} - true jeżeli użytkownik jest managerem windykacji telefonicznej, false w przeciwnym wypadku
   */
  isPhoneDebtCollectorManager() {
    return this.userAccessService.isPhoneDebtCollectorManager();
  }

  /**
   * Przekierowuje na stronę wyszukiwania zleceń z parametrami typ zlecenia, segment klienta oraz status zlecenia
   * @param {IssueType} issueType - typ zlecenia
   * @param {KeyLabel} segment - segment klienta
   * @param {KeyLabel} issueStates - statusy zleceń
   */
  private navigateToIssuesPage(issueType: IssueType, segment: KeyLabel, issueStates: KeyLabel[]) {
    const issueStatesKeys = issueStates.map(function (state: KeyLabel) {
      return state.key;
    });
    const navigationExtras: NavigationExtras = {
      queryParams: {
        issueTypeId: issueType.id,
        segment: segment.key,
        issueStates: issueStatesKeys
      }
    };
    this.router.navigate(['/issues/manage'], navigationExtras);
  }

  /**
   * Przekierowuje na stronę wyszukiwania zleceń z parametrem etykieta
   * @param {Tag} tag - etykieta
   */
  private navigateToTagIssuesPage(tag: Tag) {
    const navigationExtras: NavigationExtras = {
      queryParams: {
        tagId: tag.id,
      }
    };
    this.router.navigate(['/issues/manage'], navigationExtras);
  }

  /**
   * Przekierowuje na stronę wyszukiwania zleceń z parametrem status zlecenia
   * @param {KeyLabel} issueState - etykieta
   */
  private navigateToWaitingForTerminationIssuesPage(issueState: KeyLabel) {
    const navigationExtras: NavigationExtras = {
      queryParams: {
        issueState: issueState.key
      }
    };
    this.router.navigate(['/issues/manage'], navigationExtras);
  }

  /**
   * Sprawdza czy użytkownik jest managerem windykacji telefonicznej
   * @returns {boolean} - true jeżeli użytkownik jest managerem windykacji telefonicznej, false w przeciwnym wypadku
   */
  joinLabels(issueStates: KeyLabel[]) {
    const issueStatesLabels = issueStates.map(function (state: KeyLabel) {
      return state.label;
    });
    return issueStatesLabels.join(', ');
  }
}
