package io.github.javajumper.lavajumper.common;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

import java.util.LinkedList;
import java.util.Queue;

public class TickActionQueue {
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
    static int actionsPerTick = 1;
    private static Queue<TickQueueAction> actions = new LinkedList<>();
    private void executeAction(MinecraftClient client)
    {
        int totalActions=0;
        for(int i=0;i<actionsPerTick;i++)
        {
            while(true)
            {
                if(actions.isEmpty())
                    break;
                var queueElement = actions.poll();
                var result = queueElement.executeAction(client);
                totalActions++;
                if(result) break;
            }
        }

    }
}
