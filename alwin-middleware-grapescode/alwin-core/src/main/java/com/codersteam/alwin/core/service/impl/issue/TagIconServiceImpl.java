package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.alwin.core.api.model.tag.TagIconDto;
import com.codersteam.alwin.core.api.service.issue.TagIconService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.TagIconDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author Dariusz Rackowski
 */
@Stateless
@SuppressWarnings({"EjbClassBasicInspection"})
public class TagIconServiceImpl implements TagIconService {

  private final AlwinMapper mapper;
  private final TagIconDao tagIconDao;

  @Inject
  public TagIconServiceImpl(final TagIconDao tagIconDao, final AlwinMapper mapper) {
    this.mapper = mapper;
    this.tagIconDao = tagIconDao;
  }

  @Override
  public List<TagIconDto> findAllTags() {
    return mapper.mapAsList(tagIconDao.allOrderedById(), TagIconDto.class);
  }

}
