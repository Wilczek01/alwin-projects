package com.codersteam.alwin.core.service.impl;

import com.codersteam.alwin.core.api.service.UUIDGeneratorService;

import javax.ejb.Singleton;
import java.util.UUID;

/**
 * @author Tomasz Sliwinski
 */
@Singleton
public class UUIDGeneratorServiceImpl implements UUIDGeneratorService {

    @Override
    public UUID generateRandomUUID() {
        return UUID.randomUUID();
    }
}
