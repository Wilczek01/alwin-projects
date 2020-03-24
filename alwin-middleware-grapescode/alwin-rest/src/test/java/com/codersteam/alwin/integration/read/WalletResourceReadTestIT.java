package com.codersteam.alwin.integration.read;

import org.junit.Test;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;

/**
 * @author Piotr Naroznik
 */
public class WalletResourceReadTestIT extends ReadTestBase {

    @Test
    public void shouldByUnauthorizedToFindAllWallets() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("wallets/all");

        // then
        assertUnauthorized(responseCode);
    }
}