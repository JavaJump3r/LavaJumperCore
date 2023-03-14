package io.github.javajumper.lavajumper.common;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.EntryListWidget;

public abstract class EntryListWidgetPatches<E extends EntryListWidget.Entry<E>> extends EntryListWidget<E>
{
    public EntryListWidgetPatches(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight)
    {
        super(client, width, height, top, bottom, itemHeight);
    }

    //we do this because original method always returns 220
    public int getRowWidth()
    {
        return width;
    }
    public abstract int getRowLeft();

}
