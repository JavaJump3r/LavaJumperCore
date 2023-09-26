package io.github.JumperOnJava.lavajumper.common;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Queue for any actions that should be happen less than N times per tick
 */
public class TickActionQueue {
    /**
     * General purpose queue for any actions. May be used by other mods in unpredictable ways
     */
    public static TickActionQueue interactQueue;
    static {
        interactQueue = new TickActionQueue();
    }
    public TickActionQueue() {
        ClientTickEvents.END_CLIENT_TICK.register(this::executeAction);
    }
    public void enqueueAction(TickQueueAction action)
    {
        actions.add(action);
    }
    public int maxActionsPerTick = 1;
    private static Queue<TickQueueAction> actions = new LinkedList<>();
    private void executeAction(MinecraftClient client)
    {
        int totalActions=0;
        for(int i = 0; i< maxActionsPerTick; i++)
        {
            while(true)
            {
                if(actions.isEmpty())
                    break;
                var queueElement = actions.poll();
                var result = queueElement.executeAction(client);
                if(result == ActionResult.SUCCESSFUL) totalActions++;
                if(result == ActionResult.FINAL) break;
            }
        }
    }
    public enum ActionResult{
        /**
         * Return successfull if action should increment counter
         */
        SUCCESSFUL,
        /**
         * Return final if this action should be last this tick
         */
        FINAL,
        /**
         * Return failed if this action should not increment action counter
         */
        FAILED,
    }
}
