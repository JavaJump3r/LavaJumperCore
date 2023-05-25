package io.github.JumperOnJava.autocfg.valuetypes;

import io.github.JumperOnJava.autocfg.Configurable;
import io.github.JumperOnJava.autocfg.FieldValue;
import io.github.JumperOnJava.autocfg.SerializerContainer;

public abstract class RangeMenuValue extends MenuValue {
    public double maxValue;
    public double minValue;
    public double interval;
    public RangeMenuValue(String translationKey, String path, FieldValue value, Configurable metadata, SerializerContainer classDataContainer)
    {
        super(translationKey, path, value, metadata, classDataContainer);

        this.minValue = metadata.minValue();
        this.maxValue = metadata.maxValue();
        this.interval = metadata.interval();
    }
}
