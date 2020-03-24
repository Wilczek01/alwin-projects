import {HttpParams} from '@angular/common/http';

export const FIRST_RESULT = '0';
export const MAX_RESULTS = '5';
export const FIRST_RESULT_KEY = 'firstResult';
export const MAX_RESULTS_KEY = 'maxResults';
export let SEARCH_PARAMS = new HttpParams();
SEARCH_PARAMS = SEARCH_PARAMS.append(FIRST_RESULT_KEY, FIRST_RESULT);
SEARCH_PARAMS = SEARCH_PARAMS.append(MAX_RESULTS_KEY, MAX_RESULTS);
