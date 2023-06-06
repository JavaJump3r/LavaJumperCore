package io.github.JumperOnJava.autocfg.valuetypes;

import dev.isxander.yacl3.api.Controller;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.gui.controllers.cycling.EnumController;
import io.github.JumperOnJava.autocfg.Configurable;
import io.github.JumperOnJava.autocfg.FieldValue;
import io.github.JumperOnJava.autocfg.SerializerContainer;

public class EnumMenuValue<T extends Enum> extends MenuValue {

    private Class<T> type;
    @Override
    public Class<T> getTarget() {
        return (Class<T>) getValue().getClass();
    }

    @Override
    public Controller<T> getController(Option<?> option) {
        return new EnumController(option,type);
    }

    public EnumMenuValue(String translationKey, String path, FieldValue value, Configurable metadata, SerializerContainer classDataContainer) {
        super(translationKey,path,value,metadata, classDataContainer);
    }
}
