import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {MessageService} from '../../shared/message.service';
import {ExtCompanyService} from '../ext-company.service';
import {ExtCompany} from 'app/company/ext-company';

/**
 * Komponent odpowiedzialny za zarządzanie blokadami klienta oraz umów klienta
 */
@Component({
  selector: 'alwin-company-out-of-service',
  templateUrl: './company-out-of-service.component.html'
})
export class CompanyOutOfServiceComponent implements OnInit {

  loadingCompany: boolean;
  company: ExtCompany;

  extCompanyId: string;

  constructor(private route: ActivatedRoute, private messageService: MessageService, private extCompanyService: ExtCompanyService) {
  }

  ngOnInit(): void {
    this.extCompanyId = this.route.snapshot.paramMap.get('extCompanyId');
    this.loadCompany();
  }

  /**
   * Pobranie danych firmy
   */
  private loadCompany() {
    this.loadingCompany = true;
    this.extCompanyService.findCompany(this.extCompanyId).subscribe(
      company => {
        if (company != null) {
          this.company = company;
        }
        this.loadingCompany = false;
      },
      err => {
        if (err.status === 404) {
          this.messageService.showMessage('Brak danych firmy');
        } else {
          HandlingErrorUtils.handleError(this.messageService, err);
        }
        this.loadingCompany = false;
      }
    );
  }
}
