package io.github.javajumper.lavajumper.common;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import java.util.function.Consumer;

public class Binder {
    public static void addBind(String displayName, int defaultKey, Consumer<MinecraftClient> callback)
    {
        var bind = new KeyBinding(displayName, InputUtil.Type.KEYSYM, defaultKey, "JavaJumper's things"); //GLFW.GLFW_KEY_DOWN
        KeyBindingHelper.registerKeyBinding(bind);
        ClientTickEvents.END_CLIENT_TICK.register(client ->
        {
            while (bind.wasPressed()) {
                callback.accept(client);
            }
        });
    }
}
