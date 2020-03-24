package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.model.issue.IssueTerminationRequestDto;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.issue.IssueTerminationService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.IssueInvoiceDao;
import com.codersteam.alwin.db.dao.IssueTerminationRequestDao;
import com.codersteam.alwin.db.dao.OperatorDao;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.issue.IssueTerminationRequest;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.type.IssueTerminationRequestState;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;

import static com.codersteam.alwin.jpa.type.IssueState.CANCELED;
import static com.codersteam.alwin.jpa.type.IssueState.IN_PROGRESS;
import static com.codersteam.alwin.jpa.type.IssueTerminationRequestState.ACCEPTED;
import static com.codersteam.alwin.jpa.type.IssueTerminationRequestState.REJECTED;

/**
 * @author Piotr Naroznik
 */
@SuppressWarnings({"EjbClassBasicInspection", "CdiInjectionPointsInspection"})
@Stateless
public class IssueTerminationServiceImpl implements IssueTerminationService {

    private final IssueTerminationRequestDao issueTerminationRequestDao;
    private final DateProvider dateProvider;
    private final OperatorDao operatorDao;
    private final AlwinMapper mapper;
    private final IssueInvoiceDao issueInvoiceDao;

    @Inject
    public IssueTerminationServiceImpl(final IssueTerminationRequestDao issueTerminationRequestDao, final OperatorDao operatorDao,
                                       final DateProvider dateProvider, final AlwinMapper mapper, final IssueInvoiceDao issueInvoiceDao) {
        this.issueTerminationRequestDao = issueTerminationRequestDao;
        this.operatorDao = operatorDao;
        this.dateProvider = dateProvider;
        this.mapper = mapper;
        this.issueInvoiceDao = issueInvoiceDao;
    }

    @Override
    public void accept(final Long issueTerminationRequestId, final Long operatorId, final IssueTerminationRequestDto terminationRequest) {
        final IssueTerminationRequest request = getIssueTerminationRequest(issueTerminationRequestId);
        final Operator operator = getOperator(operatorId);
        final Date currentDate = dateProvider.getCurrentDate();
        terminateIssue(request.getIssue(), operator, currentDate, terminationRequest);
        updateIssueTerminationRequest(terminationRequest.getComment(), request, operator, currentDate, ACCEPTED);
    }

    @Override
    public void reject(final Long issueTerminationRequestId, final Long operatorId, final IssueTerminationRequestDto terminationRequest) {
        final IssueTerminationRequest request = getIssueTerminationRequest(issueTerminationRequestId);
        final Operator operator = getOperator(operatorId);
        final Date currentDate = dateProvider.getCurrentDate();
        restoreIssue(request.getIssue());
        updateIssueTerminationRequest(terminationRequest.getComment(), request, operator, currentDate, REJECTED);
    }

    @Override
    public IssueTerminationRequestDto findTerminationRequestByIssueId(final Long issueId) {
        final IssueTerminationRequest request = issueTerminationRequestDao.findIssueTerminationRequestByIssueId(issueId)
                .orElseThrow(() -> new EntityNotFoundException(issueId));
        return mapper.map(request, IssueTerminationRequestDto.class);
    }

    @Override
    public IssueTerminationRequestDto findTerminationRequestById(final Long issueTerminationRequestId) {
        final IssueTerminationRequest request = issueTerminationRequestDao.get(issueTerminationRequestId).orElse(null);
        return mapper.map(request, IssueTerminationRequestDto.class);
    }

    private Operator getOperator(final Long operatorId) {
        return operatorDao.get(operatorId)
                .orElseThrow(() -> new EntityNotFoundException(operatorId));
    }

    private IssueTerminationRequest getIssueTerminationRequest(final Long issueTerminationRequestId) {
        return issueTerminationRequestDao.get(issueTerminationRequestId)
                .orElseThrow(() -> new EntityNotFoundException(issueTerminationRequestId));
    }

    private void terminateIssue(final Issue issue, final Operator operator, final Date currentDate, final IssueTerminationRequestDto request) {
        issue.setExcludedFromStats(request.getExcludedFromStats());
        issue.setExclusionCause(request.getExclusionFromStatsCause());
        issue.setTerminationCause(request.getRequestCause());
        issue.setTerminationOperator(operator);
        issue.setTerminationDate(currentDate);
        issue.setIssueState(CANCELED);

        issueInvoiceDao.updateIssueInvoicesFinalBalance(request.getIssueId());
    }

    private void restoreIssue(final Issue issue) {
        issue.setIssueState(IN_PROGRESS);
    }

    private void updateIssueTerminationRequest(final String comment, final IssueTerminationRequest request, final Operator operator, final Date currentDate,
                                               final IssueTerminationRequestState state) {
        request.setState(state);
        request.setComment(comment);
        request.setUpdated(currentDate);
        request.setManagerOperator(operator);
        issueTerminationRequestDao.update(request);
    }
}