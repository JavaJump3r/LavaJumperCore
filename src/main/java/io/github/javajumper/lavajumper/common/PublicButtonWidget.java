package io.github.javajumper.lavajumper.common;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.function.Supplier;

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
