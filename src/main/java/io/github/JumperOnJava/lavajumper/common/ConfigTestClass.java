package io.github.JumperOnJava.lavajumper.common;

import io.github.JumperOnJava.autocfg.Configurable;
import io.github.JumperOnJava.autocfg.CustomCategory;
import io.github.JumperOnJava.lavajumper.common.actiontext.ActionTextRenderer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

/**
 * This class is test class for JJAutoConfig
 * may be used as example
 */
public class ConfigTestClass extends Feature {
    @Configurable(defaultValue = "false")
    public static boolean enabled;
    @Configurable(defaultValue = "0")
    public static int integerField = 5;
    @Configurable(defaultValue = "0",minValue = 0, maxValue = 10)
    public static int integerSlider = 5;
    @Configurable(defaultValue = "0")
    public static double doubleField = 5;
    @Configurable(defaultValue = "0",minValue = 0, maxValue = 10,interval = 0.01)
    public static double doubleSlider = 5;
    @Configurable(defaultValue = "String field")
    public static String stringField = "Strign test";
    //@Configurable(defaultValue = "STATE_ABC")
    //public static ConfigTestEnum enumField = ConfigTestEnum.STATE_XYZ;
    static {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(enabled)
            ActionTextRenderer.sendChatMessage(String.format("%d;%d;%.2f;%.2f;%s;%s",integerField,integerSlider,doubleField,doubleSlider,stringField,"enum no"));
        });
    }

    enum ConfigTestEnum{
        STATE_ABC,
        STATE_XYZ,
        STATE_XDD,
        STATE_123
    }
}
