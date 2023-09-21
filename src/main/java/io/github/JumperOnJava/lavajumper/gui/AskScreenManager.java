package io.github.JumperOnJava.lavajumper.gui;

import io.github.JumperOnJava.lavajumper.gui.widgets.OverlayScreen;
import io.github.JumperOnJava.lavajumper.gui.widgets.SubScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class AskScreenManager {
    public static <T> void ask(AskScreen<T> askScreen){
        var client = MinecraftClient.getInstance();
        var currentScreen = client.currentScreen;
        if(currentScreen==null){
            client.setScreen(askScreen);
            return;
        }
        var askSubScreen = new OverlayScreen();
        askSubScreen.init(0,0, currentScreen.width,currentScreen.height);
        askSubScreen.setScreen(askScreen);
        currentScreen.children();
        currentScreen.drawables.add(0,askSubScreen);
        ((java.util.List<Element>)currentScreen.children()).add(0,askSubScreen);
    }

    public static <T extends AskScreen<?>> void close(T screen) {
        var client = MinecraftClient.getInstance();
        var children = client.currentScreen.children();
        var del = new ArrayList<Element>();
        for(var c : children){
            if(c instanceof OverlayScreen overlayScreen){
                //bruh gonna use reflection
                try {
                    Field field = SubScreen.class.getDeclaredField("screen");
                    field.setAccessible(true);
                    if (field.get(overlayScreen) == screen) {
                        del.add(c);
                    }
                } catch (Exception e){throw new RuntimeException(e);}//lmao
            }
        }
        for(var d : del){
            client.currentScreen.remove(d);
        }
    }
}
