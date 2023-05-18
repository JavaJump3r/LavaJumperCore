package io.github.javajumper.lavajumper.gui.widgets;

import io.github.javajump3r.lavajumper.gui.datatypes.CircleSlice;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

public interface PizzaSlice {
    default void onRightClick(){}
    default void onLeftClick(){}
    default void onScroll(double scroll){}
    default int getBackgroundColor(){return ColorHelper.Argb.getArgb(255,0,0,0);}
    /**
     * Icon texture identifier. Can return null to not display anything
     * @return
     */
    default Identifier getIconTexture(){return new Identifier("textures/item/diamond.png");}
    default Text getName(){return Text.empty();}
    CircleSlice getSlice();

}
