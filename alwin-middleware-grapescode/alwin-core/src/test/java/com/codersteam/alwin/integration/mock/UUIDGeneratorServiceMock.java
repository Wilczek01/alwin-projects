package com.codersteam.alwin.integration.mock;

import com.codersteam.alwin.core.api.service.UUIDGeneratorService;

import javax.ejb.Singleton;
import java.util.UUID;

/**
 * @author Tomasz Sliwinski
 */
@Singleton
public class UUIDGeneratorServiceMock implements UUIDGeneratorService {

    public static final String TEST_UUID_STRING = "acbcb5e4-89ee-4971-a29f-d95d9a9d2662";

    @Override
    public UUID generateRandomUUID() {
        return UUID.fromString(TEST_UUID_STRING);
    }
}
