package com.codersteam.alwin.validator;

import com.codersteam.alwin.core.api.model.tag.TagInputDto;
import com.codersteam.alwin.core.api.service.issue.TagService;
import com.codersteam.alwin.exception.AlwinValidationException;
import com.codersteam.alwin.util.IdsDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Logika walidująca poprawność danych dla etykiet
 *
 * @author Michal Horowic
 */
@Stateless
public class TagValidator {

    private static final String MISSING_NAME_MESSAGE = "Brakująca nazwa dla etykiety";
    private static final String MISSING_COLOR_MESSAGE = "Brakujący kolor dla etykiety";
    private static final String MISSING_TAG_MESSAGE = "Etykieta o podanym identyfikatorze [%s] nie istnieje";
    private static final String TAG_NOT_DELETABLE_MESSAGE = "Etykieta o podanym identyfikatorze [%s] nie może zostać usnięta, ponieważ jest zdefiniowana przez system";
    private static final String INVALID_TAG_COLOR_MESSAGE = "Podany kolor etykiety [%s] jest niepoprawny. Wymagany format: #000000";
    private static final String HEX_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

    private TagService tagService;
    private Pattern pattern;

    public TagValidator() {
    }

    @Inject
    public TagValidator(final TagService tagService) {
        this.tagService = tagService;
        this.pattern = Pattern.compile(HEX_PATTERN);
    }

    public void validate(final long tagId, final TagInputDto tag) throws AlwinValidationException {
        checkIfTagExists(tagId);
        validate(tag);
    }

    /**
     * Sprawdza poprawność danych etykiety
     *
     * @param tag - dane etykiety
     * @throws AlwinValidationException jeżeli nie została podana nazwa lub kolor etykiety lub nie podano kolory w odpowiednim formacie
     */
    public void validate(final TagInputDto tag) throws AlwinValidationException {
        if (isEmpty(tag.getName())) {
            throw new AlwinValidationException(MISSING_NAME_MESSAGE);
        }

        if (isEmpty(tag.getColor())) {
            throw new AlwinValidationException(MISSING_COLOR_MESSAGE);
        }

        if (isInvalidColor(tag.getColor())) {
            throw new AlwinValidationException(String.format(INVALID_TAG_COLOR_MESSAGE, tag.getColor()));
        }
    }

    /**
     * Sprawdza czy istnieje etykieta o podanym identyfikatorze
     *
     * @param tagId - identyfikator etykiety
     * @throws AlwinValidationException jeżeli etykieta nie istnieje
     */
    public void checkIfTagExists(final long tagId) throws AlwinValidationException {
        if (tagService.checkIfTagExists(tagId)) {
            return;
        }

        throw new AlwinValidationException(String.format(MISSING_TAG_MESSAGE, tagId));
    }

    /**
     * Sprawdza czy etykieta o podanym identyfikatorze może zostać usunięta
     *
     * @param tagId - identyfikator etykiety
     * @throws AlwinValidationException jeżeli etykieta nie istnieje
     */
    public void checkIfTagIsDeletable(final long tagId) throws AlwinValidationException {
        if (tagService.isDeletable(tagId)) {
            return;
        }

        throw new AlwinValidationException(String.format(TAG_NOT_DELETABLE_MESSAGE, tagId));
    }

    /**
     * Sprawdza poprawność koloru etykiety
     *
     * @param hexColor - kolor etykiety (np. #000000)
     * @return true jeżeli kolor jest poprawny, false w przeciwnym wypadku
     */
    protected boolean isInvalidColor(final String hexColor) {
        return !pattern.matcher(hexColor).matches();
    }

    /**
     * Sprawdza czy istnieją podane etykiety
     *
     * @param tagIds - identyfikatory etykiet
     */
    public void checkIfTagsExists(final IdsDto tagIds) {
        final Set<Long> tagsIds = new HashSet<>(tagIds.getIds());
        checkIfTagsExists(tagsIds);
    }

    /**
     * Sprawdza czy istnieją podane etykiety
     *
     * @param tagsIds - identyfikatory etykiet
     */
    public void checkIfTagsExists(final Set<Long> tagsIds) {
        final Set<Long> existingTagsIds = tagService.findExistingTagsIds(tagsIds);
        if (existingTagsIds.containsAll(tagsIds)) {
            return;
        }
        tagsIds.removeAll(existingTagsIds);
        throw new AlwinValidationException(String.format(MISSING_TAG_MESSAGE, tagsIds));
    }
}
