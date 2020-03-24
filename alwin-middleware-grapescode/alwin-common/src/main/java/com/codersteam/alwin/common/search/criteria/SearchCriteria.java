package com.codersteam.alwin.common.search.criteria;

/**
 * @author Adam Stepnowski
 */
public abstract class SearchCriteria {

    private int firstResult;
    private int maxResults;

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(final Integer firstResult) {
        if (firstResult != null) {
            this.firstResult = firstResult;
        }
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(final Integer maxResults) {
        if (maxResults != null) {
            this.maxResults = maxResults;
        }
    }
}
