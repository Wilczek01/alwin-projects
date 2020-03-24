package com.codersteam.alwin.core.api.service.activity;

import javax.ejb.Local;

@Local
public interface CreateActivityService {

    void createNewPhoneDebtCollectionActivity(Long issueId);

    void createNewDirectDebtCollectionActivity(Long issueId);
}
