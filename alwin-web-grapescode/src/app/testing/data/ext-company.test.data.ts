import {Page} from '../../shared/pagination/page';
import {ExtCompany} from '../../company/ext-company';

export const COMPANY_NAME = 'adh';
export const COMPANY_ID = 1;
export const COMPANY_NIP = '323423423';
export const COMPANY_REGON = '32423';


export const FIRST_SET_OF_EXT_COMPANIES = [{id: 1}, {id: 2}];
export const SECOND_SET_OF_EXT_COMPANIES = [{id: 3}, {id: 4}];

export const TOTAL_VALUES = 4;

export const EXPECTED_FIRST_PAGE = new Page(FIRST_SET_OF_EXT_COMPANIES as ExtCompany[], TOTAL_VALUES);
export const EXPECTED_SECOND_PAGE = new Page(SECOND_SET_OF_EXT_COMPANIES as ExtCompany[], TOTAL_VALUES);
