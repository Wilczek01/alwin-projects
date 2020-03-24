import {async, inject, TestBed} from '@angular/core/testing';
import {ExtCompaniesDatasource} from './ext-companies-datasource';
import { MatPaginator } from '@angular/material/paginator';
import {AlwinMatModule} from '../alwin-mat.module';
import {ChangeDetectorRef} from '@angular/core';
import {ExtCompanyService} from './ext-company.service';
import {MessageService} from '../shared/message.service';
import {MessageServiceMock} from '../testing/message.service.mock';
import {ExtCompanyServiceMock} from '../testing/ext-company.service.mock';
import {FIRST_SET_OF_EXT_COMPANIES, TOTAL_VALUES} from '../testing/data/ext-company.test.data';

describe('Ext companies datasource', () => {

  let subject: ExtCompaniesDatasource;
  let paginator: MatPaginator;
  let result;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        AlwinMatModule
      ],
      providers: [
        MatPaginator,
        ChangeDetectorRef,
        URLSearchParams,
        {
          provide: ExtCompanyService,
          useClass: ExtCompanyServiceMock
        },
        {
          provide: MessageService,
          useClass: MessageServiceMock
        }
      ]
    });
  });

  beforeEach(inject([ExtCompanyService, MatPaginator, MessageService, URLSearchParams], (extCompanyService, matPaginator, messageService, params) => {
    subject = new ExtCompaniesDatasource(extCompanyService, matPaginator, messageService, params);
    paginator = matPaginator;
    result = subject.connect();
  }));

  it('should return first page of ext companies', async(() => {
    // when
    paginator._pageIndex = 0;
    paginator.pageSize = 2;

    // then
    result.subscribe(data => {
      expect(data).toBe(FIRST_SET_OF_EXT_COMPANIES);
      subject.max = TOTAL_VALUES;
    });
  }));

  it('should return empty page of ext companies if no data was found', async(() => {
    // when
    paginator._pageIndex = 10;
    paginator.pageSize = 2;

    // then
    result.subscribe(data => {
      expect(data).toEqual([]);
      subject.max = 0;
    });
  }));
});
