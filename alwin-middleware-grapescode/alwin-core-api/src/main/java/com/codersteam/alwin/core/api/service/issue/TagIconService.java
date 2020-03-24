package com.codersteam.alwin.core.api.service.issue;

import com.codersteam.alwin.core.api.model.tag.TagIconDto;

import javax.ejb.Local;
import java.util.List;

/**
 * Serwis do obsługi symboli etykiet
 *
 * @author Dariusz Rackowski
 */
@Local
public interface TagIconService {

    /**
     * Zwraca listę wszystkich symboli etykiet
     *
     * @return lista symboli etykiet
     */
    List<TagIconDto> findAllTags();

}
