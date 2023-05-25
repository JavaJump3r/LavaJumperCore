package io.github.JumperOnJava.autocfg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configurable {
    /**
     * Smallest value of slider
     * If min equals max value field will have field configuration instead of slider
     * if target field is integer this will be cast to integer
     */
    double minValue() default -1;
    /**
     * Smallest value of slider
     * If max equals min value field will have field configuration instead of slider
     * if target field is integer this will be cast to integer
     */
    double maxValue() default -1;

    /**
     * Interval of slider, ignored in field mode
     * Error when equals to zero
     * @return
     */
    double interval() default 1;


    /**
     * default value of variable as string
     * for every non string you should use .toString() method to not get any errors
     * @return
     */
    String defaultValue();
}

