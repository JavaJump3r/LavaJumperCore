package io.github.JumperOnJava.lavajumper.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.JumperOnJava.lavajumper.gui.fix.ScissorFix;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Widget for rendering screens in screens.
 * Use init method so set widget dimensions and setScreen method to set/change screen in widget.
 * When screen is not set up or equals null widget will display empty screen with random color and "nullSubScreen" text in center
 */
public class SubScreen implements Drawable, ParentElement, Selectable {
    private Screen screen;
    private int x,y,width,height;
    public SubScreen init(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setScreen(null);
        return this;
    }

    /**
     * If screen paremeter equals null widget will display empty screen with random color and "nullSubScreen" text in center
     * @param screen
     */
    public void setScreen(Screen screen) {
        if(screen==null)
            screen=new NullSubScreen();
        this.screen = screen;
        screen.init(MinecraftClient.getInstance(),width,height);
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        matrixStack.push();
        matrixStack.translate(x,y,0);
        //GuiHelper.beginMatrixScissor();
        ScissorFix.setMatrix(matrixStack);
        screen.render(matrixStack, mouseX-x, mouseY-y, delta);
        //GuiHelper.endScissor();
        matrixStack.pop();
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        screen.mouseMoved(mouseX-x, mouseY-y);
    }

    @Override
    public List<? extends Element> children() {
        return screen.children();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return screen.mouseClicked(mouseX-x, mouseY-y, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return screen.mouseReleased(mouseX-x, mouseY-y, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return screen.mouseDragged(mouseX-x, mouseY-y, button, deltaX, deltaY);
    }

    @Override
    public boolean isDragging() {
        return false;
    }

    @Override
    public void setDragging(boolean dragging) {

    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return screen.mouseScrolled(mouseX-x, mouseY-y, amount);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return screen.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return screen.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return screen.charTyped(chr, modifiers);
    }

    @Nullable
    @Override
    public Element getFocused() {
        return screen.getFocused();
    }

    @Override
    public void setFocused(@Nullable Element focused) {
        screen.setFocused(focused);
    }



    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= this.x &&
               mouseY >= this.y &&
               mouseX < (this.x + this.width) &&
               mouseY < (this.y + this.height);
    }

    @Override
    public SelectionType getType() {
        return SelectionType.NONE;
    }

    @Override
    public boolean isNarratable() {
        return Selectable.super.isNarratable();
    }

    private class NullSubScreen extends Screen {
        public NullSubScreen() {
            super(Text.empty());
        }

        @Override
        public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            super.render(matrices, mouseX, mouseY, delta);
            //renderBackground(matrices);
            RenderSystem.enableBlend();
            fill(matrices,0,0,width,height,(int)(Math.pow(x + width + y + height,5f)%Integer.MAX_VALUE)&0x00FFFFFF|0x3F000000);
            DrawableHelper.drawCenteredTextWithShadow(matrices,MinecraftClient.getInstance().textRenderer, "nullSubScreen",width/2,height/2,(int)(Math.pow(x + width + y + height,5f)%Integer.MAX_VALUE)&0x00FFFFFF|0x3F000000^0x00FFFFFF);
        }
    }
}
