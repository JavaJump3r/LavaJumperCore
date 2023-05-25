package io.github.JumperOnJava.autocfg.valuetypes;

import dev.isxander.yacl.api.Controller;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.gui.controllers.BooleanController;
import io.github.JumperOnJava.autocfg.Configurable;
import io.github.JumperOnJava.autocfg.FieldValue;
import io.github.JumperOnJava.autocfg.SerializerContainer;

public class BooleanMenuValue extends MenuValue {

    public BooleanMenuValue(String translationKey, String fieldPath, FieldValue value, Configurable metadata, SerializerContainer classDataContainer) {
        super(translationKey, fieldPath, value, metadata, classDataContainer);
    }

    @Override
    public Class getTarget() {
        return boolean.class;
    }
    @Override
    public Controller getController(Option<?> option) {
        return new BooleanController((Option<Boolean>) option);
    }
}
