package com.codersteam.alwin.core.api.service.audit;

import com.codersteam.alwin.core.api.model.audit.AuditLogEntryDto;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Adam Stepnowski
 */
@Local
public interface AuditService {

    List<AuditLogEntryDto> findIssueChanges(Long personId);

}
