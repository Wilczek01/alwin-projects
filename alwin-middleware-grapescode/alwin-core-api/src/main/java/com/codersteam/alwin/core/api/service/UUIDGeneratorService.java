package com.codersteam.alwin.core.api.service;

import javax.ejb.Local;
import java.util.UUID;

/**
 * Generator UUID
 *
 * @author Tomasz Sliwinski
 */
@Local
public interface UUIDGeneratorService {

    /**
     * Generuje losowego UUIDa
     *
     * @return UUID
     */
    UUID generateRandomUUID();
}
