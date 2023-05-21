package io.github.JumperOnJava.lavajumper.common;

import net.minecraft.client.MinecraftClient;

public interface TickQueueAction {
    /**
     * Action to execute
     * @param client
     * @return if true this actions will be counted as successful and will be last executed action this tick
     */
    public TickActionQueue.ActionResult executeAction(MinecraftClient client);
}
