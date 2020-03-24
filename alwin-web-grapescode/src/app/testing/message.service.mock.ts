import {MessageService} from '../shared/message.service';
import {ErrorResponse} from '../error/error-response';

export class MessageServiceMock extends MessageService {

  constructor() {
    super(null, null);
  }

  showMessage(message: string) {
  }

  showMessageErrorResponse(errorResponse: ErrorResponse) {
  }

  showMessageErrorResponseCustomMessage(message: string, errorResponse: ErrorResponse) {
  }
}
