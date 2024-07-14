package org.konkuk.business.verify;

import org.junit.jupiter.api.Test;
import org.konkuk.degreeverifier.business.verify.VerifierFactory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VerifierFactoryTest extends VerifierFactory {
    @Test
    void not_loaded_VerifierFactory() {
        assertFalse(isLoaded());
        assertTrue(isEmpty());
    }
}