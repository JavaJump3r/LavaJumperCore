package io.github.JumperOnJava.lavajumper.common;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

import java.util.LinkedList;
import java.util.List;

public class InventoryTools {
    

    private static void addActionToProcess(Slot slot, int button)
    {
        addActionToProcess(new InventoryAction(slot,button,SlotActionType.PICKUP));
    }
    private static void addActionToProcess(InventoryAction action)
    {
        TickActionQueue.interactQueue.enqueueAction(action);
    }
    /**
     * Swaps two slots
     * If these slots have stackable items and not full some items will move from one slot to other
     */
    public static void swapSlots(Slot slotA,Slot slotB)
    {
        addActionToProcess(slotA,0);
        addActionToProcess(slotB,0);
        addActionToProcess(slotA,0);
    }

    public static List<Slot> findAllSlotsWithItemType(List<Slot> slots, Item searchItem)
    {
        var matchingSlots = new LinkedList<Slot>();
        for(var slot : slots)
        {
            if(slot.getStack().getItem()==searchItem)
            {
                matchingSlots.add(slot);
            }
        }
        return matchingSlots;
    }
    public static void shiftMove(Slot slot)
    {
        addActionToProcess(new InventoryAction(slot,1,SlotActionType.QUICK_MOVE));
    }

    private static class InventoryAction implements TickQueueAction
    {
        public Slot slot;
        public Integer button;
        public SlotActionType slotActionType;
        public InventoryAction(Slot slot,int button,SlotActionType action)
        {
            this.slot=slot;
            this.button=button;
            this.slotActionType=action;
        }

        public TickActionQueue.ActionResult executeAction(MinecraftClient client)
        {
            client.interactionManager.clickSlot(MinecraftClient.getInstance().player.currentScreenHandler.syncId, slot.id, button, slotActionType, client.player);
            return TickActionQueue.ActionResult.SUCCESSFUL;
        }
    }
}
