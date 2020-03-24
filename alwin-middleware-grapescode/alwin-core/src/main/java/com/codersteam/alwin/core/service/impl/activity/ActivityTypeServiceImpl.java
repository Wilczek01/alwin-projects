package com.codersteam.alwin.core.service.impl.activity;

import com.codersteam.alwin.common.activity.ActivityTypeDetailPropertyType;
import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.core.api.model.activity.ActivityTypeByStateDto;
import com.codersteam.alwin.core.api.model.activity.ActivityTypeDetailPropertyDto;
import com.codersteam.alwin.core.api.model.activity.ActivityTypeDto;
import com.codersteam.alwin.core.api.service.activity.ActivityTypeService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.ActivityTypeDao;
import com.codersteam.alwin.jpa.activity.ActivityType;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.codersteam.alwin.common.activity.ActivityTypeDetailPropertyType.EXECUTED;
import static com.codersteam.alwin.common.activity.ActivityTypeDetailPropertyType.PLANNED;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

/**
 * @author Michal Horowic
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class ActivityTypeServiceImpl implements ActivityTypeService {

    private final ActivityTypeDao activityTypeDao;
    private final AlwinMapper alwinMapper;

    @Inject
    public ActivityTypeServiceImpl(final ActivityTypeDao activityTypeDao, final AlwinMapper alwinMapper) {
        this.activityTypeDao = activityTypeDao;
        this.alwinMapper = alwinMapper;
    }

    @Override
    public ActivityTypeByStateDto findAllActivityTypesGroupedByState(final Boolean mayHaveDeclarations) {
        return groupByState(findActivityTypes(null, mayHaveDeclarations));
    }


    @Override
    public List<ActivityTypeDto> findActivityTypes(final IssueTypeName issueTypeName, final Boolean mayHaveDeclarations) {
        final List<ActivityType> types = activityTypeDao.findActivityTypes(issueTypeName, mayHaveDeclarations);
        return alwinMapper.mapAsList(types, ActivityTypeDto.class);
    }

    @Override
    public ActivityTypeByStateDto findActivityTypeByIssueTypeGroupedByState(final IssueTypeName issueTypeName, final Boolean mayHaveDeclarations) {
        return groupByState(findActivityTypes(issueTypeName, mayHaveDeclarations));
    }

    private ActivityTypeByStateDto groupByState(final List<ActivityTypeDto> executedTypes) {
        final List<ActivityTypeDto> plannedTypes = new ArrayList<>();
        executedTypes.forEach(type -> {
            final Map<ActivityTypeDetailPropertyType, Set<ActivityTypeDetailPropertyDto>> detailProperties = groupByState(type);
            if (isTrue(type.getCanBePlanned())) {
                plannedTypes.add(preparePlannedType(type, detailProperties.get(PLANNED)));
            }
            type.setActivityTypeDetailProperties(detailProperties.get(EXECUTED));
        });
        return new ActivityTypeByStateDto(plannedTypes, executedTypes);
    }

    private ActivityTypeDto preparePlannedType(final ActivityTypeDto type, final Set<ActivityTypeDetailPropertyDto> detailProperties) {
        final ActivityTypeDto plannedType = alwinMapper.map(type, ActivityTypeDto.class);
        plannedType.setActivityTypeDetailProperties(detailProperties);
        return plannedType;
    }

    private Map<ActivityTypeDetailPropertyType, Set<ActivityTypeDetailPropertyDto>> groupByState(final ActivityTypeDto type) {
        return type.getActivityTypeDetailProperties().stream().collect(groupingBy(ActivityTypeDetailPropertyDto::getState, toSet()));
    }


    @Override
    public boolean checkIfActivityTypeIsAllowedForIssueType(final Long issueTypeId, final Long activityTypeId) {
        return activityTypeDao.checkIfActivityTypeIsAllowedForIssueType(issueTypeId, activityTypeId);
    }
}
