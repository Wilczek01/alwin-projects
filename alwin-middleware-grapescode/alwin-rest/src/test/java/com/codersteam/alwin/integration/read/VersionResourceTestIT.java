package com.codersteam.alwin.integration.read;

import com.codersteam.alwin.core.api.model.version.AppVersionDto;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Michal Horowic
 */
public class VersionResourceTestIT extends ReadTestBase {

    @Test
    public void shouldGetVersion() throws Exception {
        // when
        final AppVersionDto appVersionDto = sendHttpGet("healthCheck", AppVersionDto.class);

        // then
        assertNotNull(appVersionDto.getVersion());
    }
}
