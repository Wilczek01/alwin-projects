package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.issue.TagIcon;

import javax.ejb.Stateless;

/**
 * Klasa dostępu do symboli etykiet
 *
 * @author Dariusz Rackowski
 */
@Stateless
public class TagIconDao extends AbstractDao<TagIcon> {

    @Override
    public Class<TagIcon> getType() {
        return TagIcon.class;
    }

}
