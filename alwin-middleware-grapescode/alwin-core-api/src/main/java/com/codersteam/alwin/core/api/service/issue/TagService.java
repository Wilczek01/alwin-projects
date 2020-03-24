package com.codersteam.alwin.core.api.service.issue;

import com.codersteam.alwin.core.api.model.tag.TagDto;
import com.codersteam.alwin.core.api.model.tag.TagInputDto;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

/**
 * Serwis do obsługi etykiet
 *
 * @author Michal Horowic
 */
@Local
public interface TagService {

    /**
     * Zwraca listę wszystkich etykiet
     *
     * @return lista etykiet
     */
    List<TagDto> findAllTags();

    /**
     * Aktualizuje etykietę o podanym identyfikatorze
     *
     * @param tagId - identyfikatr etykiety
     * @param tag   - dane etykiety
     */
    void updateTag(final long tagId, final TagInputDto tag);

    /**
     * Tworzy nową etykietę
     *
     * @param tag - dane etykiety
     */
    void createTag(final TagInputDto tag);

    /**
     * Usuwa etykietę o podanym identyfikatorze
     *
     * @param tagId - identyfikator etykiety
     */
    void deleteTag(final long tagId);

    /**
     * Sprawdza czy etykieta o podanym identyfikatorze istnieje
     *
     * @param tagId - identyfikator etykiety
     * @return true jeżeli etykieta istnieje, false w przeciwnym wypadku
     */
    boolean checkIfTagExists(final long tagId);

    /**
     * Zwraca listę istniejących identyfikatorów etykiet z podanych
     *
     * @param tagsIds - podane identyfikatory etykiet
     * @return lista istniejących identyfikatorów etykiet
     */
    Set<Long> findExistingTagsIds(Set<Long> tagsIds);

    /**
     * Sprawdza czy etykietę można usunąć
     *
     * @param id - identyfikator etykiety
     * @return true jeżeli etykieta nie jest predefiniowana, false w przeciwnym wypadku
     */
    boolean isDeletable(final long id);

    /**
     * Przypisanie etykiety "Zaległe" do zlecenia
     *
     * @param issueId - identyfikator
     */
    void tagOverdueIssue(Long issueId);
}
