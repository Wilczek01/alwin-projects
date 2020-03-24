package com.codersteam.alwin.core.service.impl.activity;

import com.codersteam.alwin.core.api.model.activity.ActivityDetailPropertyDto;
import com.codersteam.alwin.core.api.service.activity.ActivityDetailPropertyService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.ActivityDetailPropertyDao;
import com.codersteam.alwin.jpa.activity.ActivityDetailProperty;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author Adam Stepnowski
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class ActivityDetailPropertyServiceImpl implements ActivityDetailPropertyService {

    private final AlwinMapper alwinMapper;
    private final ActivityDetailPropertyDao activityDetailPropertyDao;

    @Inject
    public ActivityDetailPropertyServiceImpl(final ActivityDetailPropertyDao activityDetailPropertyDao, final AlwinMapper alwinMapper) {
        this.activityDetailPropertyDao = activityDetailPropertyDao;
        this.alwinMapper = alwinMapper;
    }

    @Override
    public List<ActivityDetailPropertyDto> findAllActivityDetailProperties() {
        final List<ActivityDetailProperty> properties = activityDetailPropertyDao.findAllActivityDetailProperties();
        return alwinMapper.mapAsList(properties, ActivityDetailPropertyDto.class);
    }

    @Override
    public boolean checkIfDetailPropertyExists(final Long id){
        return id != null && activityDetailPropertyDao.exists(id);
    }
}
