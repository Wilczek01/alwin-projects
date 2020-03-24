import {Page} from '../../shared/pagination/page';
import {Issue} from '../../issues/shared/issue';

export const FIRST_SET_OF_ISSUES = [{id: 1}, {id: 2}];
export const SECOND_SET_OF_ISSUES = [{id: 3}, {id: 4}];
export const TOTAL_VALUES = 2;

export const EXPECTED_FIRST_PAGE = new Page(FIRST_SET_OF_ISSUES as Issue[], TOTAL_VALUES);
export const EXPECTED_SECOND_PAGE = new Page(SECOND_SET_OF_ISSUES as Issue[], TOTAL_VALUES);

export const EXPECTED_FIRST_PAGE_RESPONSE = new Page<Issue>(FIRST_SET_OF_ISSUES as Issue[], TOTAL_VALUES);
export const EXPECTED_EMPTY_RESPONSE = null;
