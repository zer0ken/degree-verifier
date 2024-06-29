package org.konkuk.degreeverifier.business.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.konkuk.degreeverifier.business.verify.criteria.RecursiveCriteria;

import java.lang.reflect.Type;

public class RecursiveCriteriaSerializer implements JsonSerializer<RecursiveCriteria> {
    @Override
    public JsonElement serialize(RecursiveCriteria recursiveCriteria, Type type, JsonSerializationContext jsonSerializationContext) {
        return null;
    }
}
