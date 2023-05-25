package io.github.JumperOnJava.autocfg;


import io.github.JumperOnJava.autocfg.valuetypes.*;

import java.util.HashMap;
import java.util.Map;

public class SerializerContainer {
    public SerializerContainer(){
        classData = new HashMap<>();
        registerMenuValueOfClass(new ClassSerializer<>(int.class, Integer::parseInt, IntRangeMenuValue::new));
        registerMenuValueOfClass(new ClassSerializer<>(Integer.class, Integer::parseInt, IntRangeMenuValue::new));
        registerMenuValueOfClass(new ClassSerializer<>(double.class, Double::parseDouble, DoubleRangeMenuValue::new));
        registerMenuValueOfClass(new ClassSerializer<>(Double.class, Double::parseDouble, DoubleRangeMenuValue::new));
        registerMenuValueOfClass(new ClassSerializer<>(float.class, b -> (float) Double.parseDouble(b), DoubleRangeMenuValue::new));
        registerMenuValueOfClass(new ClassSerializer<>(Float.class, b -> (float) Double.parseDouble(b), DoubleRangeMenuValue::new));
        registerMenuValueOfClass(new ClassSerializer<>(String.class, b -> b, StringMenuValue::new));
        registerMenuValueOfClass(new ClassSerializer<>(boolean.class, b -> b.equals("true"), BooleanMenuValue::new));
        registerMenuValueOfClass(new ClassSerializer<>(Boolean.class, b -> b.equals("true"), BooleanMenuValue::new));
    }
    public Object parseObject(Class objectClass, String string)
    {
        if(!objectClass.isEnum())
        {
            try {
                return classData.get(objectClass).parseFromString.apply(string);
            }
            catch (Exception e)
            {
                throw new RuntimeException("Class %s serialization is not supported or other error");
            }
        }
        else
        {
            return Enum.valueOf((Class<Enum>)objectClass, string);
        }
    }

    public MenuValue createMenuValueByClass(Class fieldClass, String translationKey, String path, FieldValue value, Configurable metaData) {
        if(!fieldClass.isEnum()) {
            try {
                return (MenuValue)classData.get(fieldClass).constructor.apply(translationKey,path,value,metaData,this);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
        else {
            return new EnumMenuValue<>(translationKey,path,value,metaData,this);
        }
    }
    public Map<Class<?>, ClassSerializer<?>> classData;
    public void registerMenuValueOfClass(ClassSerializer<?> data)
    {
        classData.put(data.targetClass,data);
    }
}
