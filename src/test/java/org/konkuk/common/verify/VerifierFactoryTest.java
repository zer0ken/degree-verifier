package org.konkuk.common.verify;

import org.junit.jupiter.api.Test;
import org.konkuk.client.logic.ProgressTracker;

import static org.junit.jupiter.api.Assertions.*;

class VerifierFactoryTest {
    VerifierFactory verifierFactory = new VerifierFactory();

    @Test
    void not_loaded_VerifierFactory() {
        assertFalse(verifierFactory.isLoaded());
        assertTrue(verifierFactory.isEmpty());
    }

    @Test
    void loaded_VerifierFactory() {
        verifierFactory.loadAllVerifiers(new ProgressTracker("test"));

        assertTrue(verifierFactory.isLoaded());
        assertEquals(11, verifierFactory.size());
    }
}