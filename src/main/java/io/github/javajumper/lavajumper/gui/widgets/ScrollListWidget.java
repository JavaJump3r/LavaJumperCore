package io.github.javajumper.lavajumper.gui.widgets;

import com.google.common.collect.Lists;
import io.github.javajump3r.lavajumper.gui.GuiHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.joml.Vector4f;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ScrollListWidget extends AlwaysSelectedEntryListWidget<ScrollListWidget.ScrollListEntry> {
    public ScrollListWidget(MinecraftClient client, int width, int height, int top,int itemHeight) {
        super(client,width,height,top,height,itemHeight);
        setRenderBackground(false);
        setRenderHeader(false,0);
    }
    @Override
    public int getRowWidth() {
        return this.width;
    }
    public int addEntry(ScrollListEntry entry){
        entry.activationConsumer = this::setSelectedEntry;
        entry.isHoveredFunction = this::isMouseOver;
        return super.addEntry(entry);
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }
    private ScrollListEntry selectedEntry=new ScrollListEntry();
    public void setSelectedEntry(ScrollListEntry listEntry) {
        selectedEntry.setSelected(false);
        listEntry.setSelected(true);
        selectedEntry=listEntry;
    }

    @Override
    public void render(MatrixStack martixStack, int mouseX, int mouseY, float delta) {
        GuiHelper.beginMatrixScissor(martixStack,left,top,left+width,top+height);
        super.render(martixStack, mouseX, mouseY, delta);
        GuiHelper.endScissor();
    }
    public static class ScrollListEntry extends AlwaysSelectedEntryListWidget.Entry<ScrollListEntry> {
        private final List<Drawable> drawables = Lists.newArrayList();
        private final List<Element> children = Lists.newArrayList();
        private boolean isSelected = false;
        private Consumer<ScrollListEntry> activationConsumer;
        private BiFunction<Integer,Integer,Boolean> isHoveredFunction;
        private List<Element> deactivate = Lists.newArrayList();

        @Override
        public Text getNarration() {
            return Text.empty();
        }
        int currentX, currentY;
        private void setSelected(boolean selected) {
            this.isSelected = selected;
            for (var d : deactivate) {
                if (d instanceof PressableWidget pw) {
                    pw.active = !isSelected;
                }
            }
        }
        @Override
        public void render(MatrixStack matrixStack,
                           int index,
                           int y, int x,
                           int entryWidth,
                           int entryHeight,
                           int mouseX, int mouseY,
                           boolean hovered,
                           float delta) {
            for (var d : drawables) {
                matrixStack.push();
                matrixStack.translate(x, y, 0);
                if(!isHoveredFunction.apply(mouseX,mouseY)){
                    mouseX+=100000;
                    mouseY+=100000;
                }
                d.render(matrixStack, mouseX - x, mouseY - y, delta);
                currentX = x;
                currentY = y;
                matrixStack.pop();
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (!isMouseOver(mouseX, mouseY))
                return false;
            for (var c : children) {
                c.mouseClicked(mouseX - currentX, mouseY - currentY, button);
            }
            return false;
        }

        @Override
        public boolean isMouseOver(double mouseX, double mouseY) {
            return super.isMouseOver(mouseX, mouseY) && isHoveredFunction.apply((int) mouseX, (int) mouseY);
        }

        public  <T extends Element & Drawable> T addDrawableChild(T drawableElement, boolean deactivateOnSelect) {
            this.drawables.add(drawableElement);
            this.children.add(drawableElement);
            if (deactivateOnSelect)
                this.deactivate.add(drawableElement);
            return drawableElement;
        }

        public void setMeActive() {
            if (activationConsumer == null)
                return;
            activationConsumer.accept(this);
        }
    }
}