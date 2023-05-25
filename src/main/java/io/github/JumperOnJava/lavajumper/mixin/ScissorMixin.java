package io.github.JumperOnJava.lavajumper.mixin;

import io.github.JumperOnJava.lavajumper.gui.GuiHelper;
import io.github.JumperOnJava.lavajumper.gui.fix.ScissorFix;
import net.minecraft.client.gui.DrawableHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(DrawableHelper.class)
public class ScissorMixin {
}