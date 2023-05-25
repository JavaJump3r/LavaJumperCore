package io.github.JumperOnJava.autocfg;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CustomCategory {
    final static String TOGGLE_FEATURES = "OnlyToggleFeatures";
    /**
     * Custom category name
     * if not defined will use name of class in field this field defined
     * @return
     */
    String category() default empty;
    String name() default empty;
    public static final String empty = "";

}
