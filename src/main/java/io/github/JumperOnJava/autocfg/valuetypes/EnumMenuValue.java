package io.github.JumperOnJava.autocfg.valuetypes;

import dev.isxander.yacl3.api.Controller;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.gui.controllers.cycling.EnumController;
import io.github.JumperOnJava.autocfg.Configurable;
import io.github.JumperOnJava.autocfg.FieldValue;
import io.github.JumperOnJava.autocfg.SerializerContainer;

public class EnumMenuValue extends MenuValue {

    private Class<?> type;
    @Override
    public Class<?> getTarget() {
        return getValue().getClass();
    }

    @Override
    public Controller<?> getController(Option<?> option) {
        return new EnumController(option,type);
    }

    public EnumMenuValue(String translationKey, String path, FieldValue value, Configurable metadata, SerializerContainer classDataContainer) {
        super(translationKey,path,value,metadata, classDataContainer);
        this.type = value.get().getClass();
    }
}
