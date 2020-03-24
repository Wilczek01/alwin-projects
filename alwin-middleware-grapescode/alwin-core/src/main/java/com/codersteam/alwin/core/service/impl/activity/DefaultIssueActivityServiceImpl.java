package com.codersteam.alwin.core.service.impl.activity;

import com.codersteam.alwin.core.api.model.activity.DefaultIssueActivityDto;
import com.codersteam.alwin.core.api.model.activity.IssueTypeWithDefaultActivities;
import com.codersteam.alwin.core.api.model.issue.IssueTypeDto;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.activity.DefaultIssueActivityService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.DefaultIssueActivityDao;
import com.codersteam.alwin.jpa.activity.DefaultIssueActivity;
import com.codersteam.alwin.jpa.issue.IssueType;
import org.apache.commons.lang3.time.DateUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Tomasz Sliwinski
 */
@Stateless
@SuppressWarnings({"EjbClassBasicInspection"})
public class DefaultIssueActivityServiceImpl implements DefaultIssueActivityService {

    private final DefaultIssueActivityDao defaultIssueActivityDao;
    private final AlwinMapper mapper;
    private final DateProvider dateProvider;

    @Inject
    public DefaultIssueActivityServiceImpl(final DefaultIssueActivityDao defaultIssueActivityDao, final AlwinMapper mapper,
                                           final DateProvider dateProvider) {
        this.defaultIssueActivityDao = defaultIssueActivityDao;
        this.mapper = mapper;
        this.dateProvider = dateProvider;
    }

    @Override
    public List<DefaultIssueActivityDto> findDefaultIssueActivities(final Long issueTypeid) {
        return defaultIssueActivityDao.findDefaultActivities(issueTypeid).stream()
                .map(this::toDefaultIssueActivityDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<IssueTypeWithDefaultActivities> findAllDefaultIssueActivities() {
        final List<DefaultIssueActivity> allDefaultActivities = defaultIssueActivityDao.findAllDefaultActivities();
        return issueTypeWithActivitiesList(allDefaultActivities);
    }

    private List<IssueTypeWithDefaultActivities> issueTypeWithActivitiesList(final List<DefaultIssueActivity> defaultActivities) {
        return defaultActivities.stream()
                .collect(Collectors.groupingBy(DefaultIssueActivity::getIssueType))
                .entrySet().stream()
                .map(this::toIssueTypeWithDefaultActivities)
                .sorted(Comparator.comparing(issueTypeWithActivities -> issueTypeWithActivities.getIssueType().getId()))
                .collect(Collectors.toList());
    }

    private IssueTypeWithDefaultActivities toIssueTypeWithDefaultActivities(final Map.Entry<IssueType, List<DefaultIssueActivity>> issueTypeWithDefaultActivities) {
        final IssueTypeWithDefaultActivities result = new IssueTypeWithDefaultActivities();
        result.setIssueType(mapper.map(issueTypeWithDefaultActivities.getKey(), IssueTypeDto.class));
        result.setDefaultActivities(issueTypeWithDefaultActivities.getValue().stream().map(this::toDefaultIssueActivityDto).collect(Collectors.toList()));
        return result;
    }

    private DefaultIssueActivityDto toDefaultIssueActivityDto(final DefaultIssueActivity activity) {
        DefaultIssueActivityDto mappedAction = mapper.map(activity, DefaultIssueActivityDto.class);
        mappedAction.setDefaultExecutionDate(DateUtils.addDays(dateProvider.getCurrentDateStartOfDay(), activity.getDefaultDay()));
        return mappedAction;
    }
}
