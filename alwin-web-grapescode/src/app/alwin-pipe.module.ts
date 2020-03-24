import {NgModule} from '@angular/core';
import {TruncatePipe} from './shared/pipe/truncate.pipe';
import {ConcatenatePipe} from './shared/pipe/concatenate.pipe';
import {NullablePipe} from './shared/pipe/nullable.pipe';
import {ChoicePipe} from './shared/pipe/choice.pipe';
import {ActivityDetailPipe} from './shared/pipe/activity-detail.pipe';
import {PermissionPipe} from './user/pipe/permission.pipe';
import {AddressPipe} from './shared/pipe/address.pipe';
import {SafeUrlPipe} from './shared/pipe/safe-url.pipe';
import {BalanceInvoiceNoPipe} from './shared/pipe/balance-invoice-no.pipe';
import {BooleanToStringPipe} from './shared/pipe/boolean-to-string.pipe';
import {SettlementOperationTypePipe} from './shared/pipe/settlement-operation-type.pipe';
import {IssueTypePipe} from './shared/pipe/issue-type.pipe';
import {ChoiceWithIconsPipe} from './shared/pipe/choice-with-icons.pipe';
import {ActivitySummaryPipe} from './issues/issue/activity/pipe/activity-summary.pipe';
import {PhoneCallLengthPipe} from './shared/pipe/phone-call-length.pipe';
import {DurationPipe} from './shared/pipe/duration.pipe';
import {AbsPipe} from './shared/pipe/abs.pipe';
import {ActivityCommentPipe} from './issues/issue/activity/pipe/activity-comment.pipe';
import {FilterActivityTypeDetailPropertiesByStatePipe} from './shared/pipe/filter-activity-type-detail-properties-by-state.pipe';
import {ContractDpdPipe} from './shared/pipe/contract-dpd.pipe';


@NgModule({
  declarations: [
    TruncatePipe,
    ConcatenatePipe,
    NullablePipe,
    ChoicePipe,
    ChoiceWithIconsPipe,
    ActivityDetailPipe,
    PermissionPipe,
    AddressPipe,
    SafeUrlPipe,
    BalanceInvoiceNoPipe,
    BooleanToStringPipe,
    SettlementOperationTypePipe,
    IssueTypePipe,
    ActivitySummaryPipe,
    ActivityCommentPipe,
    PhoneCallLengthPipe,
    DurationPipe,
    AbsPipe,
    FilterActivityTypeDetailPropertiesByStatePipe,
    ContractDpdPipe
  ],
  exports: [
    TruncatePipe,
    ConcatenatePipe,
    NullablePipe,
    ChoicePipe,
    ChoiceWithIconsPipe,
    ActivityDetailPipe,
    PermissionPipe,
    AddressPipe,
    SafeUrlPipe,
    BalanceInvoiceNoPipe,
    BooleanToStringPipe,
    SettlementOperationTypePipe,
    IssueTypePipe,
    ActivitySummaryPipe,
    ActivityCommentPipe,
    PhoneCallLengthPipe,
    DurationPipe,
    AbsPipe,
    FilterActivityTypeDetailPropertiesByStatePipe,
    ContractDpdPipe
  ]
})
export class AlwinPipeModule {
}
