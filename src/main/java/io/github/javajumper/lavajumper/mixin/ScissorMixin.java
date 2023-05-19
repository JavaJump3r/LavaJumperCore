package io.github.javajumper.lavajumper.mixin;

import io.github.javajumper.lavajumper.gui.GuiHelper;
import io.github.javajumper.lavajumper.gui.fix.ScissorFix;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DrawableHelper.class)
public class ScissorMixin {
    @ModifyArg(method = "enableScissor",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/DrawableHelper$ScissorStack;push(Lnet/minecraft/client/gui/ScreenRect;)Lnet/minecraft/client/gui/ScreenRect;"),index = 0)
    private static ScreenRect enableScissor(ScreenRect rect){
        var ul = GuiHelper.transformCoords(ScissorFix.getMatrix(),rect.getLeft(),rect.getTop());
        var dr = GuiHelper.transformCoords(ScissorFix.getMatrix(),rect.getRight(),rect.getBottom());
        return new ScreenRect((int) ul.x, (int) ul.y,(int) (dr.x-ul.x), (int) (dr.y-ul.y));
        //return new ScreenRect(0, 0, 500, 500);
    }
}
