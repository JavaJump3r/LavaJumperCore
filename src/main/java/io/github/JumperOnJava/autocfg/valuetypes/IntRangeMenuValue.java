package io.github.JumperOnJava.autocfg.valuetypes;

import dev.isxander.yacl3.api.Controller;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController;
import dev.isxander.yacl3.gui.controllers.string.number.IntegerFieldController;
import io.github.JumperOnJava.autocfg.Configurable;
import io.github.JumperOnJava.autocfg.FieldValue;
import io.github.JumperOnJava.autocfg.SerializerContainer;

public class IntRangeMenuValue extends RangeMenuValue {

    public IntRangeMenuValue(String translationKey, String path, FieldValue value, Configurable metaData, SerializerContainer classDataContainer) {
        super(translationKey, path, value, metaData, classDataContainer);
    }

    @Override
    public Class getTarget() {
        return int.class;
    }

    public Controller getController(Option<?> option) {
        if(minValue == maxValue)
        {
            return new IntegerFieldController((Option<Integer>) option,Integer.MIN_VALUE,Integer.MAX_VALUE);
        }
        if((int)interval == 0)
            throw new RuntimeException(String.format("Integer field %s have 0 interval", getFieldPath()));
        return new IntegerSliderController((Option<Integer>) option,(int) minValue,(int) maxValue,(int) interval);
    }
}