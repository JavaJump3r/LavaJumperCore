package io.github.JumperOnJava.lavajumper.common;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.function.Supplier;

@Deprecated
/**
 * Class i used to create buttons in old pre 1.19 way, mostly for fast porting of my old mods to 1.19+
 * Please use original ButtonWidget.Builder instead
 */
public class PublicButtonWidget extends ButtonWidget {
    public PublicButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, new NarrationSupplier() {
            @Override
            public MutableText createNarrationMessage(Supplier<MutableText> textSupplier) {
                return textSupplier.get();
            }
        });
    }
}
