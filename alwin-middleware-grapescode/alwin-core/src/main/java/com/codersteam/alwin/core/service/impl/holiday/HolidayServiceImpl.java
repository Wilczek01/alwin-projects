package com.codersteam.alwin.core.service.impl.holiday;

import com.codersteam.alwin.core.api.model.holiday.HolidayDto;
import com.codersteam.alwin.core.api.model.holiday.HolidayInputDto;
import com.codersteam.alwin.core.api.service.holiday.HolidayService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.HolidayDao;
import com.codersteam.alwin.jpa.holiday.Holiday;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * @author Michal Horowic
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class HolidayServiceImpl implements HolidayService {

    private final HolidayDao holidayDao;
    private final AlwinMapper mapper;

    @Inject
    public HolidayServiceImpl(final HolidayDao holidayDao, final AlwinMapper mapper) {
        this.holidayDao = holidayDao;
        this.mapper = mapper;
    }

    @Override
    public List<HolidayDto> findAllHolidaysPerDay(final int day, final int month, final int year, final Long userId) {
        final List<Holiday> operators = holidayDao.findAllHolidaysPerDay(day, month, year, userId);
        if (isEmpty(operators)) {
            return emptyList();
        }
        return mapper.mapAsList(operators, HolidayDto.class);
    }

    @Override
    public List<HolidayDto> findAllHolidaysPerMonth(final int month, final int year, final Long userId) {
        final List<Holiday> operators = holidayDao.findAllHolidaysPerMonth(month, year, userId);
        if (isEmpty(operators)) {
            return emptyList();
        }
        return mapper.mapAsList(operators, HolidayDto.class);
    }

    @Override
    public List<HolidayDto> findAllHolidaysPerYear(final int year, final Long userId) {
        final List<Holiday> operators = holidayDao.findAllHolidaysPerYear(year, userId);
        if (isEmpty(operators)) {
            return emptyList();
        }
        return mapper.mapAsList(operators, HolidayDto.class);
    }

    @Override
    public void createNewHoliday(final HolidayInputDto holiday) {
        final Holiday newHoliday = mapper.map(holiday, Holiday.class);
        holidayDao.create(newHoliday);
    }

    @Override
    public void updateHoliday(final long holidayId, final HolidayInputDto holiday) {
        final Holiday existingHoliday = mapper.map(holiday, Holiday.class);
        existingHoliday.setId(holidayId);
        holidayDao.update(existingHoliday);
    }

    @Override
    public void deleteHoliday(final long holidayId) {
        holidayDao.delete(holidayId);
    }

    @Override
    public boolean checkIfHolidayExists(final long holidayId) {
        return holidayDao.exists(holidayId);
    }
}
