package org.konkuk.business.verify;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.konkuk.degreeverifier.business.DefaultPaths;
import org.konkuk.degreeverifier.business.verify.VerifierFactory;
import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.criteria.LectureCriteria;
import org.konkuk.degreeverifier.business.verify.criteria.RecursiveCriteria;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class VerifierFactoryTest2 {

    private VerifierFactory verifierFactory;

    @BeforeEach
    void setUp() {
        verifierFactory = VerifierFactory.getInstance();
    }

    @Test
    void loadLatestVerifiers_shouldLoadLatestVerifiers(){
        // Given
        verifierFactory = VerifierFactory.getInstance();

        // When
        verifierFactory.loadLatestVerifiers();

        // Then
        assertFalse(verifierFactory.isEmpty());
        assertEquals(1, verifierFactory.size());
    }

    @Test
    void loadVerifiers_shouldLoadVerifiersFromDirectory()  {

        // When
        verifierFactory.loadVerifiers(DefaultPaths.VERIFIERS_PATH);

        // Then
        assertFalse(verifierFactory.isEmpty());
        assertEquals(30, verifierFactory.size());
    }

    @Test
    void updateAllVerifiers_shouldUpdateVerifiersAndSaveToJsonFiles(){
        // Given
        Collection<DegreeCriteria> mockCriteriaCollection = getMockCriteriaCollection();

        // When
        verifierFactory.updateAllVerifiers(mockCriteriaCollection);

        // Then
        assertFalse(verifierFactory.isEmpty());
        assertEquals(mockCriteriaCollection.size(), verifierFactory.size());

    }

    private Collection<DegreeCriteria> getMockCriteriaCollection() {

        LinkedList<DegreeCriteria> criteriaCollection = new LinkedList<>();

        LectureCriteria lectureCriteria = new LectureCriteria(null, "Degree1을 위한 강의","A",true,2021, 2025, "1", "2");
        RecursiveCriteria recursiveCriteria1 = new RecursiveCriteria("~", false, lectureCriteria, false, 0, null, null);
        DegreeCriteria criteria1 = new DegreeCriteria("Degree1", 1, "",3, recursiveCriteria1);

        criteriaCollection.add(criteria1);
        return criteriaCollection;
    }

}
