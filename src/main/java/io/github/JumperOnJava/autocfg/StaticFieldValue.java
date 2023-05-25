package io.github.JumperOnJava.autocfg;

import java.lang.reflect.Field;

class StaticFieldValue implements FieldValue {
    private Field value;
    public StaticFieldValue(Field value) {
        this.value = value;
    }

    @Override
    public Object get() {
        try {
            return value.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void set(Object value) {
        try {
            this.value.set(null, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
