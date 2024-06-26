package org.konkuk.degreeverifier.business.verify.editable;

public interface Editable {
    boolean isEdited();
    void rollback();
}
