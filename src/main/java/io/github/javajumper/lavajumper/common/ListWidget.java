package io.github.javajumper.lavajumper.common;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.util.math.MatrixStack;

public class ListWidget extends EntryListWidgetPatches<ListEntry>
{
    public ListEntry selectedEntry;
    int itemWidth;
    int width,height;
    int gap;
    int top,left;
    Screen parent;
    MinecraftClient client;
    public ListWidget(Screen parent, MinecraftClient client,
                      int width, int height, int top,
                      int bottom, int left, int gap,
                      int itemHeight, int itemWidth) {
        super(client, width, height, top, bottom, itemHeight);
        this.left = left;
        this.right = left + width;
        this.itemWidth = itemWidth;
        this.top = top;
        this.bottom = top + height;
        this.gap = gap;

        this.parent = parent;
        this.client = client;
        this.width = width;
        this.height = height;

        setRenderHorizontalShadows(true);
    }
    public int getRowLeft()
    {
        return this.left + gap / 2;
    }

    public int addEntry(ListEntry entry)
    {
        return super.addEntry(entry);
    }
    public void render(MatrixStack matrix, int mx,int my,float delta)
    {
        renderSides(matrix,left,top,width,height);

        beginScissor(left, top, width+left, height+top);
        super.render(matrix, mx, my, delta);
        RenderSystem.disableScissor();
    }
    public void renderSides(MatrixStack matrices,int x,int y,int width,int height)
    {
        y-=1;
        height+=1;
        fill(matrices, x-7, y-7, x + width+7, y + height+7, 0xFF000000);
        fill(matrices, x-6, y-6, x + width+6, y + height+6, 0xFFC6C6C6);
        fill(matrices, x-6, y-6, x + width+4, y + height+4, 0xFFFFFFFF);
        fill(matrices, x-4, y-4, x + width+6, y + height+6, 0xFF555555);
        fill(matrices, x-4, y-4, x + width+4, y + height+4, 0xFFC6C6C6);
    }
    //cool 0x3C50's code
    public void beginScissor(double x, double y, double endX, double endY)
    {
        double width = endX - x;
        double height = endY - y;
        width = Math.max(0, width);
        height = Math.max(0, height);
        float d = (float) client.getWindow().getScaleFactor();
        int ay = (int) ((client.getWindow().getScaledHeight() - (y + height)) * d);
        RenderSystem.enableScissor((int) (x * d), ay, (int) (width * d), (int) (height * d));
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
        //TODO: add narration
        //narrator stub
    }
    @Override

    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        boolean s=false;
        for(ListEntry child : children())
        {
            child.mouseClicked(mouseX,mouseY,button);
        }
        //s = super.mouseClicked(mouseX, mouseY, button) || s;
        return s;
        //return super.mouseClicked(mouseX, mouseY, button);
    }
    protected int getScrollbarPositionX()
    {
        return this.width + this.left - 3;
    }
}

