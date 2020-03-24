package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.model.issue.IssueTypeConfigurationDto;
import com.codersteam.alwin.core.api.model.operator.OperatorTypeDto;
import com.codersteam.alwin.core.api.service.activity.DefaultIssueActivityService;
import com.codersteam.alwin.core.api.service.issue.IssueConfigurationService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.IssueTypeConfigurationDao;
import com.codersteam.alwin.jpa.issue.IssueTypeConfiguration;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;

/**
 * @author Adam Stepnowski
 */
@Stateless
public class IssueConfigurationServiceImpl implements IssueConfigurationService {

    private IssueTypeConfigurationDao issueTypeConfigurationDao;
    private DefaultIssueActivityService defaultIssueActivityService;
    private AlwinMapper mapper;

    public IssueConfigurationServiceImpl() {
        // For deployment
    }

    @Inject
    public IssueConfigurationServiceImpl(final IssueTypeConfigurationDao issueTypeConfigurationDao, final DefaultIssueActivityService defaultIssueActivityService,
                                         final AlwinMapper mapper) {
        this.issueTypeConfigurationDao = issueTypeConfigurationDao;
        this.defaultIssueActivityService = defaultIssueActivityService;
        this.mapper = mapper;
    }

    @Override
    public List<IssueTypeConfigurationDto> findAllIssueTypeConfigurations() {
        final List<IssueTypeConfiguration> issueTypeConfiguration = issueTypeConfigurationDao.findAllIssueTypeConfiguration();
        final List<IssueTypeConfigurationDto> issueTypeConfigurationDtos = mapper.mapAsList(issueTypeConfiguration, IssueTypeConfigurationDto.class);
        issueTypeConfigurationDtos.forEach(issueTypeConfigurationDto -> issueTypeConfigurationDto.setDefaultIssueActivity(defaultIssueActivityService
                .findDefaultIssueActivities(issueTypeConfigurationDto.getIssueType().getId())));
        return issueTypeConfigurationDtos;
    }

    @Override
    public IssueTypeConfigurationDto findIssueTypeConfiguration(final IssueTypeName issueTypeName, final Segment segment) {
        final IssueTypeConfiguration issueTypeConfiguration = issueTypeConfigurationDao
                .findIssueTypeConfigurationByTypeAndSegment(issueTypeName, segment)
                .orElseThrow(() -> new EntityNotFoundException(format("type=%s, segment=%s", issueTypeName, segment)));
        return mapper.map(issueTypeConfiguration, IssueTypeConfigurationDto.class);
    }

    @Override
    public void updateIssueTypeConfiguration(final long configurationId, final IssueTypeConfigurationDto issueTypeConfigurationDto) {
        final Long issueTypeId = issueTypeConfigurationDto.getIssueType().getId();
        final Set<Long> operatorTypesIds = issueTypeConfigurationDto.getOperatorTypes().stream().map(OperatorTypeDto::getId).collect(toSet());
        final IssueTypeConfiguration configuration = mapper.map(issueTypeConfigurationDto, IssueTypeConfiguration.class);
        configuration.setId(configurationId);
        issueTypeConfigurationDao.update(configuration, issueTypeId, operatorTypesIds);
    }

    @Override
    public List<IssueTypeConfigurationDto> findAllCreateAutomaticallyIssueTypeConfigurations() {
        final List<IssueTypeConfiguration> allCreateAutomaticallyIssueTypeConfigurations =
                issueTypeConfigurationDao.findAllCreateAutomaticallyIssueTypeConfigurations();
        return mapper.mapAsList(allCreateAutomaticallyIssueTypeConfigurations, IssueTypeConfigurationDto.class);
    }
}
