package io.github.JumperOnJava.autocfg;

import com.mojang.datafixers.util.Function5;
import io.github.JumperOnJava.autocfg.valuetypes.MenuValue;

import java.util.function.Function;

public class ClassSerializer<T> {
    public final Class<T> targetClass;
    public final Function<String,T> parseFromString;
    public final Function5<String, String, FieldValue, Configurable, SerializerContainer, MenuValue> constructor;
    public ClassSerializer(
            Class<T> targetClass,
            Function<String,T> parseFromStringFunction,
            Function5<String, String, FieldValue, Configurable, SerializerContainer, MenuValue> constructor)
    {
        this.targetClass = targetClass;
        this.parseFromString = parseFromStringFunction;
        this.constructor = constructor;
    }

}
