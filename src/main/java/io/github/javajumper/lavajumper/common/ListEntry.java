package io.github.javajumper.lavajumper.common;

import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class ListEntry extends AlwaysSelectedEntryListWidget.Entry<ListEntry>
{
    private final ListWidget parent;
    private final ArrayList<ClickableWidget> widgets = new ArrayList<>();

    public ListEntry(ListWidget parent, ListButton object)
    {
        //x and y in this case don't matter because we will change it in render method
            ButtonWidget nameButton = new PublicButtonWidget(100, 100, parent.itemWidth, 20, Text.literal(object.GetName()), button ->
        {
            object.OnClick();
        });
        widgets.add(nameButton);
        this.parent = parent;
    }
    public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        for(ClickableWidget widget : widgets)
        {
            widget.setX(x);
            widget.setY(y);
            widget.active= parent.selectedEntry != this;
            widget.render(matrices, mouseX, mouseY, tickDelta);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if(!(mouseX >= parent.left  &&  mouseX <= parent.left + parent.width  &&  mouseY >= parent.top  &&  mouseY <= parent.height + parent.top))
            return false;
        boolean s=false;
        for (ClickableWidget widget : widgets)
        {
            s = widget.mouseClicked(mouseX, mouseY, button) || s;
        }
        return s;  //super.mouseClicked(mouseX, mouseY, button);
    }
    public Text getNarration() {
        return Text.literal("placeholder");
    }
}
