package com.codersteam.alwin.util;

import java.util.List;
import java.util.Set;

/**
 * Klasa umożliwiająca przesłanie identyfikatorów encji, do których chcemy przypisać inne encje o podanych identyfikatorach
 *
 * @author Michal Horowic
 */
public class ManyToManyIdsDto {

    private List<Long> ids;
    private Set<Long> idsToAssign;

    public ManyToManyIdsDto() {
    }

    public ManyToManyIdsDto(List<Long> ids, Set<Long> idsToAssign) {
        this.ids = ids;
        this.idsToAssign = idsToAssign;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Set<Long> getIdsToAssign() {
        return idsToAssign;
    }

    public void setIdsToAssign(final Set<Long> idsToAssign) {
        this.idsToAssign = idsToAssign;
    }
}
